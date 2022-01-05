package com.cognizant.code.test.infrastructure.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ServerResponse<T> {

    private String code;

    private String message;

    private T data;

    public static <T> ServerResponse<T> success() {
        return new ServerResponse<>("0", "success", null);
    }

    public static <T> ServerResponse<T> success(T data) {
        return new ServerResponse<>("0", "success", data);
    }

    public static <T> ServerResponse<T> error(Exception e) {
        return new ServerResponse<>("1", e.getMessage(), null);
    }
}
