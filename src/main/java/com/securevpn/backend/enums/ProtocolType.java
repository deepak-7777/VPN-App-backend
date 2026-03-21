package com.securevpn.backend.enums;

/**
 * VPN tunnel protocol type used by a server.
 * Real protocol implementation lives in the VPN node infrastructure, not here.
 */
public enum ProtocolType {
    WIREGUARD,
    OPENVPN,
    OTHER
}
