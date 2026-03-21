package com.securevpn.backend.service;

import com.securevpn.backend.dto.response.PrivacyPolicyResponse;
import com.securevpn.backend.entity.PrivacyPolicyMeta;
import com.securevpn.backend.repository.PrivacyPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * Returns privacy policy metadata.
 * Falls back to application.properties values if database is empty.
 */
@Service
@RequiredArgsConstructor
public class PrivacyServiceImpl implements PrivacyService {

    private final PrivacyPolicyRepository policyRepository;

    // Fallback values from application.properties
    @Value("${app.privacy.policy.url}")
    private String fallbackPolicyUrl;

    @Value("${app.privacy.policy.version}")
    private String fallbackVersion;

    @Value("${app.privacy.terms.url}")
    private String fallbackTermsUrl;

    @Override
    public PrivacyPolicyResponse getPrivacyPolicy() {
        return policyRepository.findTopByOrderByUpdatedAtDesc()
                .map(this::toResponse)
                .orElse(buildFallbackResponse());
    }

    private PrivacyPolicyResponse toResponse(PrivacyPolicyMeta meta) {
        return PrivacyPolicyResponse.builder()
                .version(meta.getVersion())
                .policyUrl(meta.getPolicyUrl())
                .termsUrl(meta.getTermsUrl())
                .summary(meta.getSummary())
                .updatedAt(meta.getUpdatedAt() != null
                        ? meta.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE)
                        : null)
                .build();
    }

    private PrivacyPolicyResponse buildFallbackResponse() {
        return PrivacyPolicyResponse.builder()
                .version(fallbackVersion)
                .policyUrl(fallbackPolicyUrl)
                .termsUrl(fallbackTermsUrl)
                .summary("SecureVPN does not collect, store, or share your browsing data or traffic content.")
                .updatedAt("2024-01-01")
                .build();
    }
}
