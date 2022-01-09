package com.shubzz.myvault.repository;

import com.shubzz.myvault.modes.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;


//TODO - Remove safely

//@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
//
//    Optional<RefreshToken> findByUser(User user);
//
//    Optional<RefreshToken> findByToken(String token);

}
