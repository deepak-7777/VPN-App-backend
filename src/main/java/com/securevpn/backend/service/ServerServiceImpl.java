package com.securevpn.backend.service;

import com.securevpn.backend.dto.response.ServerResponse;
import com.securevpn.backend.entity.VpnServer;
import com.securevpn.backend.repository.VpnServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final VpnServerRepository serverRepository;

    @Override
    public List<ServerResponse> getAvailableServers() {
        log.debug("Fetching available free server list");
        List<VpnServer> servers = serverRepository.findAllFreeServersSorted();
        return servers.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /** Maps VpnServer entity → ServerResponse DTO (never expose entity directly) */
    private ServerResponse toResponse(VpnServer server) {
        return ServerResponse.builder()
                .id(String.valueOf(server.getId()))
                .country(server.getCountry())
                .city(server.getCity())
                .countryCode(server.getCountryCode())
                .flag(server.getFlagEmoji())
                .ping(server.getPing())
                .isFree(server.getIsFree())
                .isRecommended(server.getIsRecommended())
                .signalLevel(server.getSignalLevel())
                .status(server.getStatus().name())
                .build();
    }
}
