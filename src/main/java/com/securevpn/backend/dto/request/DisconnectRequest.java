package com.securevpn.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request body for POST /api/v1/vpn/disconnect.
 */
@Data
public class DisconnectRequest {

    @NotBlank(message = "deviceId is required")
    private String deviceId;

    private Long sessionId;

    private String sessionToken;
}