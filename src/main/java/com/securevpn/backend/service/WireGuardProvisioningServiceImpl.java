package com.securevpn.backend.service;

import com.securevpn.backend.entity.VpnServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WireGuardProvisioningServiceImpl implements WireGuardProvisioningService {

    @Override
    public ProvisionedWireGuardPeer provisionPeer(VpnServer server, String deviceId, Long sessionId) {

        return ProvisionedWireGuardPeer.builder()
                .clientPrivateKey("CLIENT_PRIVATE_KEY_REPLACE")
                .clientPublicKey("CLIENT_PUBLIC_KEY_REPLACE")
                .serverPublicKey("SERVER_PUBLIC_KEY_REPLACE")
                .clientAddress("10.8.0.2/32")
                .endpointHost(server.getHost())
                .endpointPort(server.getPort())
                .dnsServers(List.of("1.1.1.1","8.8.8.8"))
                .allowedIps(List.of("0.0.0.0/0","::/0"))
                .persistentKeepalive(25)
                .build();
    }

    @Override
    public void revokePeer(VpnServer server, String sessionToken, String deviceId) {

    }
}