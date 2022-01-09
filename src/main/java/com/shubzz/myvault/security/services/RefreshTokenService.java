package com.shubzz.myvault.security.services;


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
