package com.securevpn.backend.service;

import com.securevpn.backend.dto.response.PrivacyPolicyResponse;

/**
 * Service interface for privacy policy metadata.
 */
public interface PrivacyService {

    /**
     * Returns current privacy policy metadata.
     * Android app uses this to check for policy version updates.
     */
    PrivacyPolicyResponse getPrivacyPolicy();
}
