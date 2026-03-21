package com.securevpn.backend.service;

import com.securevpn.backend.entity.VpnServer;

public interface WireGuardProvisioningService {

    ProvisionedWireGuardPeer provisionPeer(
            VpnServer server,
            String deviceId,
            Long sessionId
    );

    void revokePeer(
            VpnServer server,
            String sessionToken,
            String deviceId
    );
}