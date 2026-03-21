package com.securevpn.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Server item DTO sent to Android app.
 *
 * Matches Android model: ServerItem.kt
 * Fields must stay in sync with the Android data class.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse {

    /** Matches Android: id (String) — sent as string for flexibility */
    private String id;

    /** Matches Android: country */
    private String country;

    /** Matches Android: city */
    private String city;

    /** Matches Android: countryCode e.g. "US", "DE" */
    private String countryCode;

    /** Matches Android: flag — emoji e.g. "🇺🇸" */
    private String flag;

    /** Matches Android: ping — latency in ms */
    private Integer ping;

    /** Matches Android: isFree */
    private Boolean isFree;

    /** Matches Android: isRecommended */
    private Boolean isRecommended;

    /** Matches Android: signalLevel (1=low, 2=medium, 3=high) */
    private Integer signalLevel;

    /** Server operational status string */
    private String status;
}
