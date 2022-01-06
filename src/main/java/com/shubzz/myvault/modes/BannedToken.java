package com.shubzz.myvault.modes;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Date;

@Document(collection = "banned_token")
public class BannedToken {

    String username;
    String token;

    @Field
    @Indexed(name = "createdAtIndex", expireAfter = "1d")
    Date createdAt;


    public BannedToken() {
        this.createdAt = Date.from(Instant.now());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
