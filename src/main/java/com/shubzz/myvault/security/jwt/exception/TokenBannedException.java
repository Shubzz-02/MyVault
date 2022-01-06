package com.shubzz.myvault.security.jwt.exception;

public class TokenBannedException extends Exception {
    public TokenBannedException() {
        super("Token Banned");
    }
}
