package com.shubzz.myvault.security.services;


import com.shubzz.myvault.modes.RefreshToken;
import com.shubzz.myvault.repository.RefreshTokenRepository;
import com.shubzz.myvault.repository.UserRepository;
import com.shubzz.myvault.security.jwt.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


//TODO - Remove safely


public class RefreshTokenService {

//    @Value("${myvualt.app.jwtRefreshExpirationMs}")
//    private Long refreshTokenDurationMs;
//
//    @Autowired
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public Optional<RefreshToken> findByToken(String token) {
//        return refreshTokenRepository.findByToken(token);
//    }
//
//    public RefreshToken createRefreshToken(String username) {
//        RefreshToken refreshToken = new RefreshToken();
//
//        refreshToken.setUser(userRepository.findById(username).get());
//        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
//        refreshToken.setToken(UUID.randomUUID().toString());
//
//        refreshToken = refreshTokenRepository.save(refreshToken);
//        return refreshToken;
//    }
//
//
//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(token);
//            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
//        }
//
//        return token;
//    }
//
//
//    @Transactional
//    public Optional<RefreshToken> deleteByUserId(String username) {
//        return refreshTokenRepository.findByUser(userRepository.findById(username).get());
//    }

}
