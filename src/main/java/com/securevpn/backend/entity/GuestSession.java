package com.securevpn.backend.entity;

import com.securevpn.backend.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Represents a guest VPN session (connect → disconnect lifecycle).
 *
 * Privacy policy compliance:
 *   - Only minimal operational metadata is stored.
 *   - deviceId is an opaque app-generated identifier — NOT a hardware ID or PII.
 *   - NO browsing history, DNS logs, traffic content, or IP abuse data is stored.
 *   - Sessions are stored only to support connect/disconnect flow.
 *   - TODO: Add a data retention policy job to auto-delete sessions older than N days.
 */
@Entity
@Table(name = "guest_sessions", indexes = {
        @Index(name = "idx_device_id", columnList = "device_id"),
        @Index(name = "idx_status", columnList = "status")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Opaque device identifier provided by the Android app.
     * Privacy note: This should be a randomly generated UUID from the app,
     * not a hardware ID or IMEI. App must not use ANDROID_ID or hardware IDs.
     */
    @Column(name = "device_id", nullable = false, length = 100)
    private String deviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id", nullable = false)
    private VpnServer server;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SessionStatus status;

    /**
     * Opaque session token sent to the Android app.
     * Used only to identify this session on disconnect.
     * Does NOT grant any privileges beyond disconnect.
     */
    @Column(name = "session_token", length = 255)
    private String sessionToken;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
