package org.gallup.access.utils;

import java.util.Base64;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtUtils {

    public static Map<String, Object> getClaims(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Not a valid JWT format.");
            }

            // Decode payload
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

            // Convert JSON string into Map
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> claims = mapper.readValue(payloadJson, Map.class);

            // Example: print common claims
            System.out.println("Subject (sub): " + claims.get("sub"));
            System.out.println("Name: " + claims.get("name"));
            System.out.println("Role: " + claims.get("role"));
            System.out.println("Issuer (iss): " + claims.get("iss"));
            System.out.println("Audience (aud): " + claims.get("aud"));
            System.out.println("Issued At (iat): " + claims.get("iat"));
            System.out.println("Expiry (exp): " + claims.get("exp"));

            return claims;

        } catch (Exception e) {
            throw new RuntimeException("Error parsing JWT claims: " + e.getMessage(), e);
        }
    }
}
