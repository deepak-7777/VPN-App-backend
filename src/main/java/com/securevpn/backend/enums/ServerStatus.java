package com.securevpn.backend.enums;

/**
 * Current operational status of a VPN server.
 */
public enum ServerStatus {
    /** Server is live and accepting connections */
    ONLINE,
    /** Server is unreachable or down */
    OFFLINE,
    /** Server is under planned maintenance */
    MAINTENANCE
}
