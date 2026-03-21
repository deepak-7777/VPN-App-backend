package com.securevpn.backend.enums;

/**
 * Status of a guest VPN session.
 * Privacy note: Session records store only metadata — no traffic content.
 */
public enum SessionStatus {
    CONNECTING,
    CONNECTED,
    DISCONNECTED,
    FAILED
}
