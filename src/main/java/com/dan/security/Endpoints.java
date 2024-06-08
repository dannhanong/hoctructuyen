package com.dan.security;

public class Endpoints {
    public static final String front_end_host = "http://localhost:3000";
    public static final String[] PUBLIC_GET_ENDPOINTSS = {
            "/courses/**",
            "/comments/**",
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/login",
            "/signup",
            "/comments/**",
    };

    public static final String[] PUBLIC_PUT_ENDPOINTSS = {
            "/profile/**",
            "/comments/**",
    };

    public static final String[] PUBLIC_TEACHER_GET_ENDPOINTSS = {
            "/report/**",
    };

    public static final String[] PUBLIC_DELETE_ENDPOINTSS = {
    };
}
