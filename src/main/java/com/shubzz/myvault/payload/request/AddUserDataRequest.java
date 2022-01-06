package com.shubzz.myvault.payload.request;

import javax.validation.constraints.NotBlank;

public class AddUserDataRequest {

    @NotBlank
    String folder;

    @NotBlank
    String emailOrUsername;
    String encryptedPassword;

    String description;

    public AddUserDataRequest() {
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
