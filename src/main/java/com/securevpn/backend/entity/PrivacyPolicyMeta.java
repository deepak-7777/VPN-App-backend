package com.securevpn.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Stores metadata about the current active privacy policy version.
 * The Android app can check this to prompt users when policy is updated.
 */
@Entity
@Table(name = "privacy_policy_meta")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivacyPolicyMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Semantic version e.g. "1.0", "1.1" */
    @Column(name = "version", nullable = false, length = 20)
    private String version;

    /** Full URL to the hosted privacy policy HTML/PDF */
    @Column(name = "policy_url", nullable = false, length = 512)
    private String policyUrl;

    /** Full URL to the terms of service */
    @Column(name = "terms_url", length = 512)
    private String termsUrl;

    /** Brief summary shown in app before user accepts */
    @Column(name = "summary", length = 1000)
    private String summary;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
