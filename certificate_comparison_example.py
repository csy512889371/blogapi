#!/usr/bin/env python3
"""
Standalone example showing how to use X.509 certificate serial numbers for comparison
"""

from x509_certificate_helper import X509CertificateHelper


def main():
    """
    Complete example of extracting and comparing certificate serial numbers
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
4MJplKujawAIKLA1zqCPFpGKZ98CICeY0PwZuH2OSRayZo0TQdIbOt6/zLPX/rOz
tUdd9htlMA0GCSqGSIb3DQEBCwUAA4IBAQCDbSr0AoWeeaUwxFZPX2YW7W+iSLqB
RL2BUpSBBIA/O0dPeowYaqjtX7wbOhm8Arw4eRIwo4yA+EbE5Axeou2npilOQCJw
mXawx2KxOxO4jk+cDYuQYBgvreh7EI3t5VekWqnfYqKF/01/og7Ol+YRwU2QDEBJ
nMz2RnF8EMMjF4lFZUCXsi20/zfCGBx0k+7sItXMsPkmdHiebSmbWgPbN9bIcWSI
8Wmdn36+xHvVQtHi8zop8GzTCNJIjBZQXPSwnAnqOixDKhiWJLI3u6YXdoeS4TKf
JzFHaQG2XsZ8RQPiJuOAOHyh9LwRf6pLTb5DSstd0Sm0llfVRhwYPifw
-----END CERTIFICATE-----"""
    
    # Example of a different certificate (Let's Encrypt root certificate)
    different_certificate = """-----BEGIN CERTIFICATE-----
MIIFazCCA1OgAwIBAgIRAIIQz7DSQONZRGPgu2OCiwAwDQYJKoZIhvcNAQELBQAw
TzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh
cmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwHhcNMTUwNjA0MTEwNDM4
WhcNMzUwNjA0MTEwNDM4WjBPMQswCQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJu
ZXQgU2VjdXJpdHkgUmVzZWFyY2ggR3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBY
MTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAK3oJHP0FDfzm54rVygc
h77ct984kIxuPOZXoHj3dcKi/vVqbvYATyjb3miGbESTtrFj/RQSa78f0uoxmyF+
0TM8ukj13Xnfs7j/EvEhmkvBioZxaUpmZmyPfjxwv60pIgbz5MDmgK7iS4+3mX6U
A5/TR5d8mUgjU+g4rk8Kb4Mu0UlXjIB0ttov0DiNewNwIRt18jA8+o+u3dpjq+sW
T8KOEUt+zwvo/7V3LvSye0rgTBIlDHCNAymg4VMk7BPZ7hm/ELNKjD+Jo2FR3qyH
B5T0Y3HsLuJvW5iB4YlcNHlsdu87kGJ55tukmi8mxdAQ4Q7e2RCOFvu396j3x+UC
B5iPNgiV5+I3lg02dZ77DnKxHZu8A/lJBdiB3QW0KtZB6awBdpUKD9jf1b0SHzUv
KBds0pjBqAlkd25HN7rOrFleaJ1/ctaJxQZBKT5ZPt0m9STJEadao0xAH0ahmbWn
OlFuhjuefXKnEgV4We0+UXgVCwOPjdAvBbI+e0ocS3MFEvzG6uBQE3xDk3SzynTn
jh8BCNAw1FtxNrQHusEwMFxIt4I7mKZ9YIqioymCzLq9gwQbooMDQaHWBfEbwrbw
qHyGO0aoSCqI3Haadr8faqU9GY/rOPNk3sgrDQoo//fb4hVC1CLQJ13hef4Y53CI
rU7m2Ys6xt0nUW7/vGT1M0NPAgMBAAGjQjBAMA4GA1UdDwEB/wQEAwIBBjAPBgNV
HRMBAf8EBTADAQH/MB0GA1UdDgQWBBR5tFnme7bl5AFzgAiIyBpY9umbbjANBgkq
hkiG9w0BAQsFAAOCAgEAVR9YqbyyqFDQDLHYGmkgJykIrGF1XIpu+ILlaS/V9lZL
ubhzEFnTIZd+50xx+7LSYK05qAvqFyFWhfFQDlnrzuBZ6brJFe+GnY+EgPbk6ZGQ
3BebYhtF8GaV0nxvwuo77x/Py9auJ/GpsMiu/X1+mvoiBOv/2X/qkSsisRcOj/KK
NFtY2PwByVS5uCbMiogziUwthDyC3+6WVwW6LLv3xLfHTjuCvjHIInNzktHCgKQ5
ORAzI4JMPJ+GslWYHb4phowim57iaztXOoJwTdwJx4nLCgdNbOhdjsnvzqvHu7Ur
TkXWStAmzOVyyghqpZXjFaH3pO3JLF+l+/+sKAIuvtd7u+Nxe5AW0wdeRlN8NwdC
jNPElpzVmbUq4JUagEiuTDkHzsxHpFKVK7q4+63SM1N95R1NbdWhscdCb+ZAJzVc
oyi3B43njTOQ5yOf+1CceWxG1bQVs5ZufpsMljq4Ui0/1lvh+wjChP4kqKOJ2qxq
4RgqsahDYVvTH9w7jXbyLeiNdd8XM2w9U/t7y0Ff/9yi0GE44Za4rF2LN9d11TPA
mRGunUHBcnWEvgJBQl9nJEiU0Zsnvgc/ubhPgXRR4Xq37Z0j4r7g1SgEEzwxA57d
emyPxgcYxn/eR44/KJ4EBs+lVDR3veyJm+kXQ99b21/+jh5Xos1AnX5iItreGCc=
-----END CERTIFICATE-----"""
    
    print("🔐 === X.509 Certificate Serial Number Analysis ===")
    print()
    
    try:
        helper = X509CertificateHelper()
        
        # Extract serial numbers from both certificates
        print("📋 STEP 1: Extract Serial Numbers")
        print("-" * 50)
        
        serial1 = helper.get_serial_number_from_cert_chain(db_certificate)
        serial2 = helper.get_serial_number_from_cert_chain(different_certificate)
        
        print(f"Certificate 1 (cdreader.com) Serial: {serial1}")
        print(f"Certificate 2 (ISRG Root X1) Serial: {serial2}")
        print()
        
        # Compare certificates
        print("🔍 STEP 2: Certificate Comparison")
        print("-" * 50)
        
        # Compare same certificate with itself
        is_same_cert = helper.compare_certificates_by_serial(db_certificate, db_certificate)
        print(f"Comparing certificate with itself: {is_same_cert}")
        
        # Compare different certificates
        is_different_cert = helper.compare_certificates_by_serial(db_certificate, different_certificate)
        print(f"Comparing different certificates: {is_different_cert}")
        print()
        
        # Get detailed information
        print("📊 STEP 3: Certificate Details")
        print("-" * 50)
        
        details1 = helper.get_certificate_details(db_certificate)
        print("Certificate 1 Details:")
        print(helper.format_certificate_details(details1))
        print()
        
        details2 = helper.get_certificate_details(different_certificate)
        print("Certificate 2 Details:")
        print(helper.format_certificate_details(details2))
        print()
        
        # Practical implementation examples
        print("💡 STEP 4: Practical Implementation for Your Database")
        print("-" * 60)
        
        print(f"""
🎯 For your kong_certificate table, here's how to implement certificate comparison:

1. SERIAL NUMBER TO COMPARE: {serial1}

2. JAVA IMPLEMENTATION:
```java
public boolean isCertificateDuplicate(String newCertPem, String domain) {{
    try {{
        String newSerial = X509CertificateHelper.getSerialNumberFromCertChain(newCertPem);
        
        // Query database for existing certificates
        String sql = "SELECT cert FROM kunlun.kong_certificate WHERE first_domain = ? AND is_delete = '0'";
        List<String> existingCerts = jdbcTemplate.queryForList(sql, String.class, domain);
        
        for (String existingCert : existingCerts) {{
            String existingSerial = X509CertificateHelper.getSerialNumberFromCertChain(existingCert);
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
```

3. SQL APPROACH:
Since X.509 serial numbers can't be easily extracted with SQL, you have two options:

Option A - Store serial number in a separate column:
```sql
ALTER TABLE kunlun.kong_certificate ADD COLUMN cert_serial_number VARCHAR(255);

-- Create index for fast lookups
CREATE INDEX idx_cert_serial ON kunlun.kong_certificate(cert_serial_number, is_delete);

-- Update existing records (you'd need to implement this)
UPDATE kunlun.kong_certificate 
SET cert_serial_number = '<extracted_serial>' 
WHERE id = '1823596552418103296';
```

Option B - Extract and compare in application code:
```sql
-- Query to get certificates for comparison
SELECT id, cert, first_domain 
FROM kunlun.kong_certificate 
WHERE first_domain = 'cdreader.com' AND is_delete = '0';

-- Then extract serial numbers in your application code
```

4. PYTHON IMPLEMENTATION:
```python
def check_certificate_duplicate(new_cert_pem, domain):
    new_serial = helper.get_serial_number_from_cert_chain(new_cert_pem)
    
    # Query database
    cursor.execute(
        "SELECT cert FROM kunlun.kong_certificate WHERE first_domain = %s AND is_delete = '0'",
        (domain,)
    )
    
    for (existing_cert,) in cursor:
        existing_serial = helper.get_serial_number_from_cert_chain(existing_cert)
        if new_serial == existing_serial:
            return True  # Duplicate found
    
    return False
```

🔑 KEY POINTS:
- Serial number {serial1} uniquely identifies your cdreader.com certificate
- Serial numbers are hex strings (case-insensitive comparison recommended)
- Same certificate = same serial number (reliable identifier)
- Different certificates = different serial numbers
- Store serial numbers in database for faster queries (recommended)
        """)
        
        print("\n✅ SUMMARY:")
        print("- Your certificate serial number: " + serial1)
        print("- Use this serial number to identify duplicate certificates")
        print("- Implement comparison logic in your certificate management service")
        print("- Consider adding cert_serial_number column to your database for performance")
        
    except Exception as e:
        print(f"❌ Error: {e}")


if __name__ == "__main__":
    main()