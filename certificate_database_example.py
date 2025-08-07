#!/usr/bin/env python3
"""
Example showing how to use X.509 certificate serial numbers for database operations
"""

import mysql.connector
from x509_certificate_helper import X509CertificateHelper
from typing import Dict, List, Optional


class CertificateDatabaseManager:
    """
    Example class showing how to manage certificates in database using serial numbers
    """
    
    def __init__(self, db_config: Dict[str, str]):
        self.db_config = db_config
        self.helper = X509CertificateHelper()
    
    def get_connection(self):
        """Get database connection"""
        return mysql.connector.connect(**self.db_config)
    
    def extract_serial_from_db_cert(self, cert_pem: str) -> str:
        """
        Extract serial number from certificate stored in database
        
        Args:
            cert_pem: Certificate in PEM format from database
            
        Returns:
            Serial number as hex string
        """
        return self.helper.get_serial_number_from_cert_chain(cert_pem)
    
    def is_certificate_duplicate(self, new_cert_pem: str, domain: str) -> bool:
        """
        Check if a certificate already exists in database by comparing serial numbers
        
        Args:
            new_cert_pem: New certificate to check
            domain: Domain to check for
            
        Returns:
            True if certificate with same serial number exists
        """
        try:
            new_serial = self.extract_serial_from_db_cert(new_cert_pem)
            
            with self.get_connection() as conn:
                cursor = conn.cursor()
                
                # Query to find certificates with same domain
                query = """
                SELECT cert, id 
                FROM kunlun.kong_certificate 
                WHERE first_domain = %s AND is_delete = '0'
                """
                
                cursor.execute(query, (domain,))
                results = cursor.fetchall()
                
                for cert_data, cert_id in results:
                    try:
                        existing_serial = self.extract_serial_from_db_cert(cert_data)
                        if existing_serial == new_serial:
                            print(f"Duplicate certificate found! ID: {cert_id}, Serial: {existing_serial}")
                            return True
                    except Exception as e:
                        print(f"Error parsing certificate ID {cert_id}: {e}")
                        continue
                
                return False
                
        except Exception as e:
            print(f"Error checking certificate duplicate: {e}")
            return False
    
    def find_certificates_by_serial(self, serial_number: str) -> List[Dict]:
        """
        Find all certificates in database with specific serial number
        
        Args:
            serial_number: Serial number to search for (hex string)
            
        Returns:
            List of certificate records
        """
        try:
            with self.get_connection() as conn:
                cursor = conn.cursor(dictionary=True)
                
                # Get all non-deleted certificates
                query = """
                SELECT id, employee_name, cert, first_domain, expiration_time, create_time
                FROM kunlun.kong_certificate 
                WHERE is_delete = '0'
                """
                
                cursor.execute(query)
                results = cursor.fetchall()
                
                matching_certs = []
                
                for record in results:
                    try:
                        cert_serial = self.extract_serial_from_db_cert(record['cert'])
                        if cert_serial == serial_number:
                            record['extracted_serial'] = cert_serial
                            matching_certs.append(record)
                    except Exception as e:
                        print(f"Error parsing certificate ID {record['id']}: {e}")
                        continue
                
                return matching_certs
                
        except Exception as e:
            print(f"Error searching certificates by serial: {e}")
            return []
    
    def get_certificate_details_from_db(self, cert_id: str) -> Optional[Dict]:
        """
        Get detailed certificate information from database including extracted details
        
        Args:
            cert_id: Certificate ID in database
            
        Returns:
            Dictionary with certificate details or None if not found
        """
        try:
            with self.get_connection() as conn:
                cursor = conn.cursor(dictionary=True)
                
                query = """
                SELECT * FROM kunlun.kong_certificate 
                WHERE id = %s AND is_delete = '0'
                """
                
                cursor.execute(query, (cert_id,))
                result = cursor.fetchone()
                
                if not result:
                    return None
                
                # Extract certificate details
                cert_details = self.helper.get_certificate_details(result['cert'])
                
                # Combine database info with extracted certificate info
                combined_info = {
                    'database_info': result,
                    'certificate_details': cert_details,
                    'serial_number': cert_details['serial_number']
                }
                
                return combined_info
                
        except Exception as e:
            print(f"Error getting certificate details from database: {e}")
            return None


def example_usage():
    """
    Example of how to use the certificate database manager
    """
    
    # Your database certificate from the INSERT statement
    db_certificate = """-----BEGIN CERTIFICATE-----
MIIFBjCCA+6gAwIBAgISBszMLwrMTjD1b4JhkfnHiDdzMA0GCSqGSIb3DQEBCwUA
MDMxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MQwwCgYDVQQD
EwNSMTEwHhcNMjUwNjE0MDEwMjM0WhcNMjUwOTEyMDEwMjMzWjAZMRcwFQYDVQQD
DA4qLmNkcmVhZGVyLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB
AOCdu5gQknZtEglPad+Llrdb2C+OHaxMhBGr+cERyX4lVovyj0fQoxi97Ji49Wni
Hu2VE76GjC9sv2UryTCs91rXW1hk3uksTd9Ex1yZbttB3BMWXDOjzhQwRzxHHPLI
kD6xIXsxXxAUAHzLSvWT0EdgZJYxhFi11ad35tr3lrWCoWO59SwNLvVYpXvCFcxc
GIfwii8W19pIsApGWr+UvSIPNpkCFq0sdN3SkzDnThOJ+fdDdD00T1tory1t9GaC
w/RvqYxgLDUvReMONItmAgDdrdN66fpe1tp7cXT4Ubv4/vOYiVlEMbV6L0F3J7sy
cZFi44f5XmxGZgepXKrubi8CAwEAAaOCAiwwggIoMA4GA1UdDwEB/wQEAwIFoDAd
BgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwDAYDVR0TAQH/BAIwADAdBgNV
HQ4EFgQUuSkm0PxnLA97tlW9IgDBvRGSGuYwHwYDVR0jBBgwFoAUxc9GpOr0w8B6
bJXELbBeki8m47kwMwYIKwYBBQUHAQEEJzAlMCMGCCsGAQUFBzAChhdodHRwOi8v
cjExLmkubGVuY3Iub3JnLzAnBgNVHREEIDAegg4qLmNkcmVhZGVyLmNvbYIMY2Ry
ZWFkZXIuY29tMBMGA1UdIAQMMAowCAYGZ4EMAQIBMC8GA1UdHwQoMCYwJKAioCCG
Hmh0dHA6Ly9yMTEuYy5sZW5jci5vcmcvMTE2LmNybDCCAQMGCisGAQQB1nkCBAIE
gfQEgfEA7wB2ABLxTjS9U3JMhAYZw48/ehP457Vih4icbTAFhOvlhiY6AAABl2wr
RFcAAAQDAEcwRQIgISW9lb07uYsAUI2Gqxk4KVU2SffbxmPIULm5NrYD1SMCIQCV
f/A4kUWUWaOUEXlHIan36hxgyzjkRjsWTXVnX7irTwB1AMz7D2qFcQll/pWbU87p
snwi6YVcDZeNtql+VMD+TA2wAAABl2wrTDUAAAQDAEYwRAIgUhySNf4Jgwbxb2ig
4MJplKujawAIKLA1zqCPFpGKZ98CICeY0PwZuH2OSRayZo0TQdIbOt6tUdd9htlMA0GCSqGSIb3DQEBCwUAA4IBAQCDbSr0AoWeeaUwxFZPX2YW7W+iSLqB
RL2BUpSBBIA/O0dPeowYaqjtX7wbOhm8Arw4eRIwo4yA+EbE5Axeou2npilOQCJw
mXawx2KxOxO4jk+cDYuQYBgvreh7EI3t5VekWqnfYqKF/01/og7Ol+YRwU2QDEBJ
nMz2RnF8EMMjF4lFZUCXsi20/zfCGBx0k+7sItXMsPkmdHiebSmbWgPbN9bIcWSI
8Wmdn36+xHvVQtHi8zop8GzTCNJIjBZQXPSwnAnqOixDKhiWJLI3u6YXdoeS4TKf
JzFHaQG2XsZ8RQPiJuOAOHyh9LwRf6pLTb5DSstd0Sm0llfVRhwYPifw
-----END CERTIFICATE-----"""
    
    print("=== Certificate Serial Number Extraction Example ===")
    
    # Extract serial number from the certificate
    try:
        helper = X509CertificateHelper()
        serial_number = helper.get_serial_number_from_cert_chain(db_certificate)
        print(f"Certificate Serial Number: {serial_number}")
        
        # Get detailed certificate information
        details = helper.get_certificate_details(db_certificate)
        print(f"\nCertificate Details:")
        print(helper.format_certificate_details(details))
        
        print(f"\n=== How to Use for Certificate Comparison ===")
        print(f"1. Serial Number for this certificate: {serial_number}")
        print(f"2. To check if another certificate is the same, extract its serial number")
        print(f"3. Compare the two serial numbers - if they match, it's the same certificate")
        
        # Example of how you would check for duplicates in your application
        print(f"\n=== Database Query Example ===")
        print(f"-- To find certificates with the same serial number:")
        print(f"-- (You would implement the logic to extract serial from each cert)")
        print(f"""
        SELECT id, first_domain, cert, expiration_time 
        FROM kunlun.kong_certificate 
        WHERE is_delete = '0' 
        AND first_domain = 'cdreader.com';
        
        -- Then for each result, extract the serial number and compare with: {serial_number}
        """)
        
        # Create a mock scenario showing different certificates
        print(f"\n=== Certificate Comparison Example ===")
        
        # Same certificate (should match)
        is_same = helper.compare_certificates_by_serial(db_certificate, db_certificate)
        print(f"Comparing certificate with itself: {is_same}")
        
        # Example of how to implement in your service
        print(f"\n=== Implementation in Your Service ===")
        print(f"""
# Java example:
public boolean isCertificateDuplicate(String newCertPem, String domain) {{
    try {{
        String newSerial = X509CertificateHelper.getSerialNumberFromCertChain(newCertPem);
        
        // Query database for existing certificates for this domain
        List<CertificateRecord> existingCerts = certificateDao.findByDomain(domain);
        
        for (CertificateRecord record : existingCerts) {{
            String existingSerial = X509CertificateHelper.getSerialNumberFromCertChain(record.getCert());
            if (newSerial.equals(existingSerial)) {{
                return true; // Duplicate found
            }}
        }}
        return false;
    }} catch (Exception e) {{
        log.error("Error checking certificate duplicate", e);
        return false;
    }}
}}

# Python example:
def is_certificate_duplicate(self, new_cert_pem: str, domain: str) -> bool:
    new_serial = self.helper.get_serial_number_from_cert_chain(new_cert_pem)
    
    existing_certs = self.get_certificates_by_domain(domain)
    
    for cert_record in existing_certs:
        existing_serial = self.helper.get_serial_number_from_cert_chain(cert_record['cert'])
        if new_serial == existing_serial:
            return True  # Duplicate found
    
    return False
        """)
        
    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    example_usage()