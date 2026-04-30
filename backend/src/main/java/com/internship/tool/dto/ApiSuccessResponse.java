package com.internship.tool.dto;

import java.time.Instant;

public class ApiSuccessResponse<T> {

    private final Instant timestamp;
    private final int status;
    private final String message;
    private final String path;
    private final T data;

    public ApiSuccessResponse(Instant timestamp, int status, String message, String path, T data) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
        this.data = data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public T getData() {
        return data;
    }
}
