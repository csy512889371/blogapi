import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class X509CertificateHelper {
    
    /**
     * Extract serial number from PEM format certificate string
     * @param pemCertificate PEM format certificate (with BEGIN/END CERTIFICATE headers)
     * @return Serial number as hex string
     */
    public static String getSerialNumberFromPem(String pemCertificate) throws Exception {
        // Remove PEM headers and whitespace
        String base64Cert = pemCertificate
                .replace("-----BEGIN CERTIFICATE-----", "")
                .replace("-----END CERTIFICATE-----", "")
                .replaceAll("\\s", "");
        
        // Decode base64 to bytes
        byte[] certBytes = Base64.getDecoder().decode(base64Cert);
        
        // Create X509Certificate object
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(
                new ByteArrayInputStream(certBytes));
        
        // Get serial number
        BigInteger serialNumber = certificate.getSerialNumber();
        
        // Return as hex string (uppercase)
        return serialNumber.toString(16).toUpperCase();
    }
    
    /**
     * Extract serial number from certificate chain (first certificate)
     * @param certChain PEM format certificate chain
     * @return Serial number as hex string
     */
    public static String getSerialNumberFromCertChain(String certChain) throws Exception {
        // Find first certificate in chain
        int beginIndex = certChain.indexOf("-----BEGIN CERTIFICATE-----");
        int endIndex = certChain.indexOf("-----END CERTIFICATE-----");
        
        if (beginIndex == -1 || endIndex == -1) {
            throw new IllegalArgumentException("Invalid PEM certificate format");
        }
        
        String firstCert = certChain.substring(beginIndex, endIndex + "-----END CERTIFICATE-----".length());
        return getSerialNumberFromPem(firstCert);
    }
    
    /**
     * Compare two certificates by serial number
     * @param cert1 First certificate in PEM format
     * @param cert2 Second certificate in PEM format
     * @return true if certificates have same serial number
     */
    public static boolean compareCertificatesBySerial(String cert1, String cert2) {
        try {
            String serial1 = getSerialNumberFromCertChain(cert1);
            String serial2 = getSerialNumberFromCertChain(cert2);
            return serial1.equals(serial2);
        } catch (Exception e) {
            System.err.println("Error comparing certificates: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get certificate details including serial number
     * @param pemCertificate PEM format certificate
     * @return Certificate details as string
     */
    public static String getCertificateDetails(String pemCertificate) throws Exception {
        String base64Cert = pemCertificate
                .replace("-----BEGIN CERTIFICATE-----", "")
                .replace("-----END CERTIFICATE-----", "")
                .replaceAll("\\s", "");
        
        byte[] certBytes = Base64.getDecoder().decode(base64Cert);
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(
                new ByteArrayInputStream(certBytes));
        
        StringBuilder details = new StringBuilder();
        details.append("Subject: ").append(certificate.getSubjectDN()).append("\n");
        details.append("Issuer: ").append(certificate.getIssuerDN()).append("\n");
        details.append("Serial Number: ").append(certificate.getSerialNumber().toString(16).toUpperCase()).append("\n");
        details.append("Not Before: ").append(certificate.getNotBefore()).append("\n");
        details.append("Not After: ").append(certificate.getNotAfter()).append("\n");
        
        return details.toString();
    }
    
    // Example usage
    public static void main(String[] args) {
        try {
            // Your certificate from the database
            String dbCertificate = "-----BEGIN CERTIFICATE-----\n" +
                    "MIIFBjCCA+6gAwIBAgISBszMLwrMTjD1b4JhkfnHiDdzMA0GCSqGSIb3DQEBCwUA\n" +
                    "MDMxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MQwwCgYDVQQD\n" +
                    "EwNSMTEwHhcNMjUwNjE0MDEwMjM0WhcNMjUwOTEyMDEwMjMzWjAZMRcwFQYDVQQD\n" +
                    "DA4qLmNkcmVhZGVyLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n" +
                    "AOCdu5gQknZtEglPad+Llrdb2C+OHaxMhBGr+cERyX4lVovyj0fQoxi97Ji49Wni\n" +
                    "Hu2VE76GjC9sv2UryTCs91rXW1hk3uksTd9Ex1yZbttB3BMWXDOjzhQwRzxHHPLI\n" +
                    "kD6xIXsxXxAUAHzLSvWT0EdgZJYxhFi11ad35tr3lrWCoWO59SwNLvVYpXvCFcxc\n" +
                    "GIfwii8W19pIsApGWr+UvSIPNpkCFq0sdN3SkzDnThOJ+fdDdD00T1tory1t9GaC\n" +
                    "w/RvqYxgLDUvReMONItmAgDdrdN66fpe1tp7cXT4Ubv4/vOYiVlEMbV6L0F3J7sy\n" +
                    "cZFi44f5XmxGZgepXKrubi8CAwEAAaOCAiwwggIoMA4GA1UdDwEB/wQEAwIFoDAd\n" +
                    "BgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwDAYDVR0TAQH/BAIwADAdBgNV\n" +
                    "HQ4EFgQUuSkm0PxnLA97tlW9IgDBvRGSGuYwHwYDVR0jBBgwFoAUxc9GpOr0w8B6\n" +
                    "bJXELbBeki8m47kwMwYIKwYBBQUHAQEEJzAlMCMGCCsGAQUFBzAChhdodHRwOi8v\n" +
                    "cjExLmkubGVuY3Iub3JnLzAnBgNVHREEIDAegg4qLmNkcmVhZGVyLmNvbYIMY2Ry\n" +
                    "ZWFkZXIuY29tMBMGA1UdIAQMMAowCAYGZ4EMAQIBMC8GA1UdHwQoMCYwJKAioCCG\n" +
                    "Hmh0dHA6Ly9yMTEuYy5sZW5jci5vcmcvMTE2LmNybDCCAQMGCisGAQQB1nkCBAIE\n" +
                    "gfQEgfEA7wB2ABLxTjS9U3JMhAYZw48/ehP457Vih4icbTAFhOvlhiY6AAABl2wr\n" +
                    "RFcAAAQDAEcwRQIgISW9lb07uYsAUI2Gqxk4KVU2SffbxmPIULm5NrYD1SMCIQCV\n" +
                    "f/A4kUWUWaOUEXlHIan36hxgyzjkRjsWTXVnX7irTwB1AMz7D2qFcQll/pWbU87p\n" +
                    "snwi6YVcDZeNtql+VMD+TA2wAAABl2wrTDUAAAQDAEYwRAIgUhySNf4Jgwbxb2ig\n" +
                    "4MJplKujawAIKLA1zqCPFpGKZ98CICeY0PwZuH2OSRayZo0TQdIbOt6/zLPX/rOz\n" +
                    "tUdd9htlMA0GCSqGSIb3DQEBCwUAA4IBAQCDbSr0AoWeeaUwxFZPX2YW7W+iSLqB\n" +
                    "RL2BUpSBBIA/O0dPeowYaqjtX7wbOhm8Arw4eRIwo4yA+EbE5Axeou2npilOQCJw\n" +
                    "mXawx2KxOxO4jk+cDYuQYBgvreh7EI3t5VekWqnfYqKF/01/og7Ol+YRwU2QDEBJ\n" +
                    "nMz2RnF8EMMjF4lFZUCXsi20/zfCGBx0k+7sItXMsPkmdHiebSmbWgPbN9bIcWSI\n" +
                    "8Wmdn36+xHvVQtHi8zop8GzTCNJIjBZQXPSwnAnqOixDKhiWJLI3u6YXdoeS4TKf\n" +
                    "JzFHaQG2XsZ8RQPiJuOAOHyh9LwRf6pLTb5DSstd0Sm0llfVRhwYPifw\n" +
                    "-----END CERTIFICATE-----";
            
            // Extract serial number
            String serialNumber = getSerialNumberFromCertChain(dbCertificate);
            System.out.println("Certificate Serial Number: " + serialNumber);
            
            // Get full certificate details
            System.out.println("\nCertificate Details:");
            System.out.println(getCertificateDetails(dbCertificate));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}