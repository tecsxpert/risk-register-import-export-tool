package com.internship.tool.dto;

import java.time.Instant;
import org.springframework.http.HttpStatus;

public final class ApiResponseFactory {

    private ApiResponseFactory() {}

    public static <T> ApiSuccessResponse<T> success(
        HttpStatus status,
        String message,
        String path,
        T data
    ) {
        return new ApiSuccessResponse<>(Instant.now(), status.value(), message, path, data);
    }
}
