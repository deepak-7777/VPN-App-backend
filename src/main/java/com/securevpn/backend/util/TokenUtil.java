package com.securevpn.backend.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility for generating opaque session tokens.
 *
 * Privacy note:
 *   Tokens generated here are random, non-reversible identifiers.
 *   They contain no user PII, device info, or traffic metadata.
 *
 * TODO: In production, replace with signed JWT tokens when auth is added.
 *       JWT payload should contain only: sessionId, serverId, expiry — NO user traffic.
 */
public class TokenUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private TokenUtil() {
        // Utility class — no instantiation
    }

    /**
     * Generates a cryptographically secure random session token.
     * @return URL-safe Base64 encoded token string (32 bytes = 43 chars)
     */
    public static String generateSessionToken() {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * Generates a demo config token for MVP phase.
     * TODO: Replace with real WireGuard config or signed tunnel config in production.
     */
    public static String generateDemoConfigToken(Long sessionId, String serverId) {
        return "svpn-" + sessionId + "-" + serverId + "-" + generateSessionToken().substring(0, 8);
    }
}
