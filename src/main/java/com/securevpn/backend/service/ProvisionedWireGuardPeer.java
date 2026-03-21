package com.securevpn.backend.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvisionedWireGuardPeer {

    private String clientPrivateKey;
    private String clientPublicKey;
    private String serverPublicKey;

    private String clientAddress;
    private String endpointHost;
    private Integer endpointPort;

    private List<String> dnsServers;
    private List<String> allowedIps;
    private Integer persistentKeepalive;
}