package com.securevpn.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SecureVPN Backend — Spring Boot Application Entry Point.
 *
 * Privacy note:
 *   This backend manages only VPN server metadata and minimal guest session flow.
 *   No user traffic, DNS queries, browsing history, or sensitive user data is
 *   collected, stored, or processed anywhere in this application.
 *
 * Future extension points:
 *   - TODO: Add Spring Security + JWT when login/auth is implemented
 *   - TODO: Add rate limiting (Bucket4j or similar) per device/IP
 *   - TODO: Add Redis caching for server list when scale demands it
 *   - TODO: Add premium subscription module when monetization is added
 */
@SpringBootApplication
public class VpnBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VpnBackendApplication.class, args);
    }
}
