package com.shubzz.myvault.payload.request;

import javax.validation.constraints.NotBlank;


//TODO - Remove safely

public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}