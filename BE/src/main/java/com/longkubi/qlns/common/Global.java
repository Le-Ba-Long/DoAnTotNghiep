package com.longkubi.qlns.common;

import org.springframework.http.HttpHeaders;

import java.util.Objects;

public class Global {
    public static String getTokenFromHeaders(HttpHeaders headers) {
        return Objects.requireNonNull(headers.get("Authorization")).get(0).substring("Bearer ".length());
    }

}
