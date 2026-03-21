package com.securevpn.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * WireGuard tunnel configuration returned to Android app.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WireGuardConfigResponse {

    private String serverPublicKey;
    private String clientPrivateKey;
    private String endpointHost;
    private Integer endpointPort;
    private String clientAddress;
    private List<String> dnsServers;
    private List<String> allowedIps;
    private Integer persistentKeepalive;
}