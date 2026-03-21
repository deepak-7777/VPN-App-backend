package com.securevpn.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response wrapper used for all endpoints.
 * Matches the Android Retrofit model: BaseApiResponse.
 *
 * JSON structure:
 * {
 *   "success": true,
 *   "message": "...",
 *   "data": { ... }   // null fields omitted
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    // ─── Static factory helpers ───────────────

    public static <T> BaseApiResponse<T> success(String message, T data) {
        return BaseApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseApiResponse<T> success(String message) {
        return BaseApiResponse.<T>builder()
                .success(true)
                .message(message)
                .build();
    }

    public static <T> BaseApiResponse<T> error(String message) {
        return BaseApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
}
