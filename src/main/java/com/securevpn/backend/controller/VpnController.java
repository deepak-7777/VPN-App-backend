package com.securevpn.backend.controller;

import com.securevpn.backend.dto.request.ConnectRequest;
import com.securevpn.backend.dto.request.DisconnectRequest;
import com.securevpn.backend.dto.response.BaseApiResponse;
import com.securevpn.backend.dto.response.ConnectData;
import com.securevpn.backend.service.VpnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vpn")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "VPN", description = "VPN connect and disconnect operations")
public class VpnController {

    private final VpnService vpnService;

    @PostMapping("/connect")
    @Operation(
            summary = "Connect to VPN",
            description = "Creates a guest VPN session and returns WireGuard connection details"
    )
    public ResponseEntity<BaseApiResponse<ConnectData>> connect(
            @Valid @RequestBody ConnectRequest request) {

        log.debug("POST /api/v1/vpn/connect serverId={}, deviceId={}",
                request.getServerId(), request.getDeviceId());

        ConnectData data = vpnService.connect(request);

        return ResponseEntity.ok(
                BaseApiResponse.success("Connected successfully", data)
        );
    }

    @PostMapping("/disconnect")
    @Operation(
            summary = "Disconnect from VPN",
            description = "Closes an active guest VPN session"
    )
    public ResponseEntity<BaseApiResponse<Void>> disconnect(
            @Valid @RequestBody DisconnectRequest request) {

        log.debug("POST /api/v1/vpn/disconnect deviceId={}", request.getDeviceId());

        BaseApiResponse<Void> response = vpnService.disconnect(request);
        return ResponseEntity.ok(response);
    }
}