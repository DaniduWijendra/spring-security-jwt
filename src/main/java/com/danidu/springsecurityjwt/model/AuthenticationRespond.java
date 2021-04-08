package com.danidu.springsecurityjwt.model;

public class AuthenticationRespond {
    private final String jwt;

    public String getJwt() {
        return jwt;
    }

    public AuthenticationRespond(String jwt) {
        this.jwt = jwt;
    }
}
