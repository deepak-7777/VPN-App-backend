package com.securevpn.backend.repository;

import com.securevpn.backend.entity.GuestSession;
import com.securevpn.backend.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for guest session data.
 *
 * Privacy note:
 *   Queries here access only session metadata (device ID, server ID, status).
 *   No traffic content, browsing data, or DNS logs are ever stored or queried.
 */
@Repository
public interface GuestSessionRepository extends JpaRepository<GuestSession, Long> {

    /** Find active session for a device (for idempotent connect) */
    Optional<GuestSession> findByDeviceIdAndStatus(String deviceId, SessionStatus status);

    /** Find session by token (for disconnect validation) */
    Optional<GuestSession> findBySessionToken(String sessionToken);

    /** Count active sessions (for basic load monitoring — non-PII) */
    long countByStatus(SessionStatus status);
}
