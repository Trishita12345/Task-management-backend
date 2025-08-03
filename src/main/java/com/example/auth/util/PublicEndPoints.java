package com.example.auth.util;

import org.springframework.util.AntPathMatcher;

import java.util.List;

public class PublicEndPoints {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final List<String> PUBLIC_PATTERNS = List.of(
            "/auth/**",
            "/h2-console/**",
            "/error/",
            "/refresh-token",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/css/**",
            "/js/**",
            "/images/**"
    );

    public static boolean isPublic(String path) {
        return PUBLIC_PATTERNS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
    public static String[] patterns() {
        return PUBLIC_PATTERNS.toArray(new String[0]);
    }
}
