package com.securevpn.backend.controller;

import com.securevpn.backend.dto.response.BaseApiResponse;
import com.securevpn.backend.dto.response.PrivacyPolicyResponse;
import com.securevpn.backend.service.PrivacyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Privacy policy metadata endpoint.
 *
 * Returns:
 * - current privacy policy version
 * - privacy policy URL
 * - terms URL
 * - summary text
 * - last updated date
 */
@RestController
@RequestMapping("/api/v1/privacy-policy")
@RequiredArgsConstructor
@Tag(name = "Privacy", description = "Privacy policy metadata")
public class PrivacyController {

    private final PrivacyService privacyService;

    @GetMapping
    @Operation(
            summary = "Get privacy policy",
            description = "Returns current privacy policy metadata"
    )
    public ResponseEntity<BaseApiResponse<PrivacyPolicyResponse>> getPrivacyPolicy() {
        PrivacyPolicyResponse policy = privacyService.getPrivacyPolicy();

        return ResponseEntity.ok(
                BaseApiResponse.success("Privacy policy fetched", policy)
        );
    }
}