package com.securevpn.backend.controller;

import com.securevpn.backend.dto.response.BaseApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Health check endpoint.
 *
 * Used by:
 * - Android app startup validation
 * - Load balancer health verification
 * - Uptime monitoring
 */
@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health", description = "Backend health check")
public class HealthController {

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.name:SecureVPN}")
    private String appName;

    @GetMapping
    @Operation(
            summary = "Health check",
            description = "Returns backend status, app name, and version"
    )
    public ResponseEntity<BaseApiResponse<Map<String, String>>> health() {
        Map<String, String> data = Map.of(
                "status", "UP",
                "version", appVersion,
                "app", appName
        );

        return ResponseEntity.ok(
                BaseApiResponse.success("Backend is running", data)
        );
    }
}