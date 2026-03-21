package com.securevpn.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Privacy policy metadata response.
 * Returned by GET /api/v1/privacy-policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivacyPolicyResponse {
    private String version;
    private String policyUrl;
    private String termsUrl;
    private String summary;
    private String updatedAt;
}
