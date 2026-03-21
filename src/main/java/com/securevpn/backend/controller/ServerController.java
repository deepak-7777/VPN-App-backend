package com.securevpn.backend.controller;

import com.securevpn.backend.dto.response.BaseApiResponse;
import com.securevpn.backend.dto.response.ServerResponse;
import com.securevpn.backend.service.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * VPN server list endpoint.
 *
 * Returns all available free VPN servers.
 * Sorted by:
 * 1. ONLINE status first
 * 2. Lower ping first
 */
@RestController
@RequestMapping("/api/v1/servers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Servers", description = "VPN server list")
public class ServerController {

    private final ServerService serverService;

    @GetMapping
    @Operation(
            summary = "Get server list",
            description = "Returns all available VPN servers sorted by status and ping"
    )
    public ResponseEntity<BaseApiResponse<List<ServerResponse>>> getServers() {
        log.debug("GET /api/v1/servers called");

        List<ServerResponse> servers = serverService.getAvailableServers();

        return ResponseEntity.ok(
                BaseApiResponse.success("Servers fetched successfully", servers)
        );
    }
}