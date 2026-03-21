package com.securevpn.backend.service;

import com.securevpn.backend.dto.response.ServerResponse;

import java.util.List;

/**
 * Service interface for VPN server operations.
 */
public interface ServerService {

    /** Returns all free servers sorted by status priority and ping */
    List<ServerResponse> getAvailableServers();
}
