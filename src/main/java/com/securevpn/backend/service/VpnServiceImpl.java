package com.securevpn.backend.service;

import com.securevpn.backend.dto.request.ConnectRequest;
import com.securevpn.backend.dto.request.DisconnectRequest;
import com.securevpn.backend.dto.response.BaseApiResponse;
import com.securevpn.backend.dto.response.ConnectData;
import com.securevpn.backend.dto.response.ServerResponse;
import com.securevpn.backend.dto.response.WireGuardConfigResponse;
import com.securevpn.backend.entity.GuestSession;
import com.securevpn.backend.entity.VpnServer;
import com.securevpn.backend.enums.ServerStatus;
import com.securevpn.backend.enums.SessionStatus;
import com.securevpn.backend.exception.BadRequestException;
import com.securevpn.backend.exception.ResourceNotFoundException;
import com.securevpn.backend.exception.ServerUnavailableException;
import com.securevpn.backend.repository.GuestSessionRepository;
import com.securevpn.backend.repository.VpnServerRepository;
import com.securevpn.backend.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VpnServiceImpl implements VpnService {

    private final VpnServerRepository serverRepository;
    private final GuestSessionRepository sessionRepository;
    private final WireGuardProvisioningService wireGuardProvisioningService;

    @Override
    @Transactional
    public ConnectData connect(ConnectRequest request) {
        log.debug("Connect request for serverId={}, deviceId={}",
                request.getServerId(), request.getDeviceId());

        VpnServer server = serverRepository.findById(request.getServerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Server not found with id: " + request.getServerId()));

        if (server.getStatus() != ServerStatus.ONLINE) {
            throw new ServerUnavailableException(
                    "Server is currently " + server.getStatus().name().toLowerCase()
                            + ". Please choose another server.");
        }

        closeExistingSession(request.getDeviceId());

        String sessionToken = TokenUtil.generateSessionToken();

        GuestSession session = GuestSession.builder()
                .deviceId(request.getDeviceId())
                .server(server)
                .status(SessionStatus.CONNECTED)
                .sessionToken(sessionToken)
                .startedAt(LocalDateTime.now())
                .build();

        session = sessionRepository.save(session);

        log.debug("Session created: id={}, server={}", session.getId(), server.getCity());

        ProvisionedWireGuardPeer provisioned =
                wireGuardProvisioningService.provisionPeer(server, request.getDeviceId(), session.getId());

        ServerResponse serverResponse = mapServerToResponse(server);

        WireGuardConfigResponse wireGuardConfig = WireGuardConfigResponse.builder()
                .serverPublicKey(provisioned.getServerPublicKey())
                .clientPrivateKey(provisioned.getClientPrivateKey())
                .endpointHost(provisioned.getEndpointHost())
                .endpointPort(provisioned.getEndpointPort())
                .clientAddress(provisioned.getClientAddress())
                .dnsServers(provisioned.getDnsServers())
                .allowedIps(provisioned.getAllowedIps())
                .persistentKeepalive(provisioned.getPersistentKeepalive())
                .build();

        return ConnectData.builder()
                .sessionToken(sessionToken)
                .assignedIp(provisioned.getClientAddress())
                .server(serverResponse)
                .wireGuard(wireGuardConfig)
                .build();
    }

    @Override
    @Transactional
    public BaseApiResponse<Void> disconnect(DisconnectRequest request) {
        log.debug("Disconnect request for deviceId={}", request.getDeviceId());

        Optional<GuestSession> sessionOpt = Optional.empty();

        if (request.getSessionToken() != null && !request.getSessionToken().isBlank()) {
            sessionOpt = sessionRepository.findBySessionToken(request.getSessionToken());
        }

        if (sessionOpt.isEmpty() && request.getSessionId() != null) {
            sessionOpt = sessionRepository.findById(request.getSessionId());
        }

        if (sessionOpt.isEmpty()) {
            sessionOpt = sessionRepository.findByDeviceIdAndStatus(
                    request.getDeviceId(), SessionStatus.CONNECTED
            );
        }

        if (sessionOpt.isPresent()) {
            GuestSession session = sessionOpt.get();

            if (!session.getDeviceId().equals(request.getDeviceId())) {
                throw new BadRequestException("Session does not belong to this device");
            }

            wireGuardProvisioningService.revokePeer(
                    session.getServer(),
                    session.getSessionToken(),
                    session.getDeviceId()
            );

            session.setStatus(SessionStatus.DISCONNECTED);
            session.setEndedAt(LocalDateTime.now());
            sessionRepository.save(session);

            log.debug("Session {} disconnected", session.getId());
        }

        return BaseApiResponse.success("Disconnected successfully");
    }

    private void closeExistingSession(String deviceId) {
        sessionRepository.findByDeviceIdAndStatus(deviceId, SessionStatus.CONNECTED)
                .ifPresent(existing -> {
                    existing.setStatus(SessionStatus.DISCONNECTED);
                    existing.setEndedAt(LocalDateTime.now());
                    sessionRepository.save(existing);
                    log.debug("Closed existing session {} for device", existing.getId());
                });
    }

    private ServerResponse mapServerToResponse(VpnServer server) {
        return ServerResponse.builder()
                .id(String.valueOf(server.getId()))
                .country(server.getCountry())
                .city(server.getCity())
                .countryCode(server.getCountryCode())
                .flag(server.getFlagEmoji())
                .ping(server.getPing())
                .isFree(Boolean.TRUE.equals(server.getIsFree()))
                .isRecommended(Boolean.TRUE.equals(server.getIsRecommended()))
                .signalLevel(resolveSignalLevel(server))
                .status(server.getStatus().name())
                .build();
    }

    private int resolveSignalLevel(VpnServer server) {
        if (server.getSignalLevel() != null) {
            return server.getSignalLevel();
        }

        Integer ping = server.getPing();
        if (ping == null) return 1;
        if (ping < 60) return 3;
        if (ping < 100) return 2;
        return 1;
    }
}