package com.shubzz.myvault.security.jwt.advice;


import com.shubzz.myvault.payload.response.MessageResponse;
import com.shubzz.myvault.security.jwt.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


//TODO - Remove safely

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageResponse handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new MessageResponse(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value());
    }
}