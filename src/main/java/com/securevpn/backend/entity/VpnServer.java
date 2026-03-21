package com.securevpn.backend.entity;

import com.securevpn.backend.enums.ProtocolType;
import com.securevpn.backend.enums.ServerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Represents a VPN server available in the network.
 *
 * Privacy note:
 *   This entity stores only server infrastructure metadata.
 *   No user data, no traffic data, no session content is stored here.
 */
@Entity
@Table(name = "vpn_servers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VpnServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Display name shown in the Android app */
    @Column(name = "server_name", nullable = false, length = 100)
    private String serverName;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    /** ISO 2-letter country code e.g. US, DE, IN */
    @Column(name = "country_code", length = 5)
    private String countryCode;

    /** Unicode flag emoji e.g. 🇺🇸 */
    @Column(name = "flag_emoji", length = 10)
    private String flagEmoji;

    /**
     * Server hostname or IP — used by the VPN node infrastructure.
     * Privacy note: This is infrastructure config, NOT used to track users.
     */
    @Column(name = "host", nullable = false, length = 255)
    private String host;

    @Column(name = "port", nullable = false)
    private Integer port;

    @Enumerated(EnumType.STRING)
    @Column(name = "protocol", nullable = false, length = 20)
    private ProtocolType protocol;

    /** Whether this server is available in the free tier */
    @Column(name = "is_free", nullable = false)
    private Boolean isFree;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ServerStatus status;

    /** Current ping in milliseconds — updated by health check jobs */
    @Column(name = "ping")
    private Integer ping;

    /** Server load as percentage 0–100 */
    @Column(name = "load_percent")
    private Integer loadPercent;

    /** Signal level 1=low, 2=medium, 3=high — derived from ping/load */
    @Column(name = "signal_level")
    private Integer signalLevel;

    /** Whether this server is recommended to new users */
    @Column(name = "is_recommended")
    private Boolean isRecommended;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
