package com.shubzz.myvault.payload.response;

public class MessageResponse {
    private int responseCode;
    private String message;


    public MessageResponse(String message, int responseCode) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
