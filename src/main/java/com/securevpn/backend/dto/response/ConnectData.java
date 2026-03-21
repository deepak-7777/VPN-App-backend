package com.securevpn.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload returned inside BaseApiResponse.data for connect API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectData {

    private String sessionToken;
    private String assignedIp;
    private ServerResponse server;
    private WireGuardConfigResponse wireGuard;
}