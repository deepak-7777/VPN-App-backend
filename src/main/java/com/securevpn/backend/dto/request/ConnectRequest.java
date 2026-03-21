package com.securevpn.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Request body for POST /api/v1/vpn/connect.
 *
 * Privacy note:
 * deviceId must be an app-generated opaque identifier.
 * Do NOT use IMEI, serial, MAC address, or any hardware identifier.
 */
@Data
public class ConnectRequest {

    @NotBlank(message = "deviceId is required")
    private String deviceId;

    @NotNull(message = "serverId is required")
    private Long serverId;

    /**
     * Device OS/type for non-identifying analytics only.
     * Example: android
     */
    private String deviceType;
}