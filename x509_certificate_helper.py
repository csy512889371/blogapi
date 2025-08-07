#!/usr/bin/env python3
"""
X.509 Certificate Helper for extracting serial numbers and comparing certificates
"""

import base64
import re
from cryptography import x509
from cryptography.hazmat.backends import default_backend
from typing import Optional


class X509CertificateHelper:
    
    @staticmethod
    def get_serial_number_from_pem(pem_certificate: str) -> str:
        """
        Extract serial number from PEM format certificate string
        
        Args:
            pem_certificate: PEM format certificate (with BEGIN/END CERTIFICATE headers)
            
        Returns:
            Serial number as hex string (uppercase)
        """
        try:
            # Parse the certificate
            cert = x509.load_pem_x509_certificate(pem_certificate.encode(), default_backend())
            
            # Get serial number as hex string
            serial_number = cert.serial_number
            return format(serial_number, 'X')
            
        except Exception as e:
            raise ValueError(f"Failed to parse certificate: {e}")
    
    @staticmethod
    def get_serial_number_from_cert_chain(cert_chain: str) -> str:
        """
        Extract serial number from certificate chain (first certificate)
        
        Args:
            cert_chain: PEM format certificate chain
            
        Returns:
            Serial number as hex string (uppercase)
        """
        # Find first certificate in chain
        begin_pattern = r'-----BEGIN CERTIFICATE-----'
        end_pattern = r'-----END CERTIFICATE-----'
        
        begin_match = re.search(begin_pattern, cert_chain)
        end_match = re.search(end_pattern, cert_chain)
        
        if not begin_match or not end_match:
            raise ValueError("Invalid PEM certificate format")
        
        # Extract first certificate
        first_cert = cert_chain[begin_match.start():end_match.end()]
        return X509CertificateHelper.get_serial_number_from_pem(first_cert)
    
    @staticmethod
    def compare_certificates_by_serial(cert1: str, cert2: str) -> bool:
        """
        Compare two certificates by serial number
        
        Args:
            cert1: First certificate in PEM format
            cert2: Second certificate in PEM format
            
        Returns:
            True if certificates have same serial number
        """
        try:
            serial1 = X509CertificateHelper.get_serial_number_from_cert_chain(cert1)
            serial2 = X509CertificateHelper.get_serial_number_from_cert_chain(cert2)
            return serial1 == serial2
        except Exception as e:
            print(f"Error comparing certificates: {e}")
            return False
    
    @staticmethod
    def get_certificate_details(pem_certificate: str) -> dict:
        """
        Get certificate details including serial number
        
        Args:
            pem_certificate: PEM format certificate
            
        Returns:
            Dictionary with certificate details
        """
        try:
            cert = x509.load_pem_x509_certificate(pem_certificate.encode(), default_backend())
            
            details = {
                'subject': cert.subject.rfc4514_string(),
                'issuer': cert.issuer.rfc4514_string(),
                'serial_number': format(cert.serial_number, 'X'),
                'not_before': cert.not_valid_before.isoformat(),
                'not_after': cert.not_valid_after.isoformat(),
                'signature_algorithm': cert.signature_algorithm_oid._name,
                'version': cert.version.name
            }
            
            # Extract Subject Alternative Names if present
            try:
                san_ext = cert.extensions.get_extension_for_oid(x509.oid.ExtensionOID.SUBJECT_ALTERNATIVE_NAME)
                san_names = [name.value for name in san_ext.value]
                details['subject_alternative_names'] = san_names
            except x509.ExtensionNotFound:
                details['subject_alternative_names'] = []
            
            return details
            
        except Exception as e:
            raise ValueError(f"Failed to parse certificate: {e}")
    
    @staticmethod
    def format_certificate_details(details: dict) -> str:
        """
        Format certificate details as readable string
        
        Args:
            details: Certificate details dictionary
            
        Returns:
            Formatted string
        """
        formatted = []
        formatted.append(f"Subject: {details['subject']}")
        formatted.append(f"Issuer: {details['issuer']}")
        formatted.append(f"Serial Number: {details['serial_number']}")
        formatted.append(f"Not Before: {details['not_before']}")
        formatted.append(f"Not After: {details['not_after']}")
        formatted.append(f"Signature Algorithm: {details['signature_algorithm']}")
        formatted.append(f"Version: {details['version']}")
        
        if details['subject_alternative_names']:
            formatted.append(f"Subject Alternative Names: {', '.join(details['subject_alternative_names'])}")
        
        return '\n'.join(formatted)


def main():
    """Example usage with the certificate from your database"""
    
    # Your certificate from the database
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
4MJplKujawAIKLA1zqCPFpGKZ98CICeY0PwZuH2OSRayZo0TQdIbOt6/zLPX/rOz
tUdd9htlMA0GCSqGSIb3DQEBCwUAA4IBAQCDbSr0AoWeeaUwxFZPX2YW7W+iSLqB
RL2BUpSBBIA/O0dPeowYaqjtX7wbOhm8Arw4eRIwo4yA+EbE5Axeou2npilOQCJw
mXawx2KxOxO4jk+cDYuQYBgvreh7EI3t5VekWqnfYqKF/01/og7Ol+YRwU2QDEBJ
nMz2RnF8EMMjF4lFZUCXsi20/zfCGBx0k+7sItXMsPkmdHiebSmbWgPbN9bIcWSI
8Wmdn36+xHvVQtHi8zop8GzTCNJIjBZQXPSwnAnqOixDKhiWJLI3u6YXdoeS4TKf
JzFHaQG2XsZ8RQPiJuOAOHyh9LwRf6pLTb5DSstd0Sm0llfVRhwYPifw
-----END CERTIFICATE-----"""
    
    try:
        # Extract serial number
        serial_number = X509CertificateHelper.get_serial_number_from_cert_chain(db_certificate)
        print(f"Certificate Serial Number: {serial_number}")
        
        # Get full certificate details
        details = X509CertificateHelper.get_certificate_details(db_certificate)
        print(f"\nCertificate Details:")
        print(X509CertificateHelper.format_certificate_details(details))
        
        # Example of comparing certificates
        print(f"\nSerial number for comparison: {serial_number}")
        
    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()