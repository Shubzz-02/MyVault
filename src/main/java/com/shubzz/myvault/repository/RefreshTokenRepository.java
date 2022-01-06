package com.shubzz.myvault.repository;

import com.shubzz.myvault.modes.RefreshToken;
import com.shubzz.myvault.modes.Role;
import com.shubzz.myvault.modes.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//TODO - Remove safely

//@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
//
//    Optional<RefreshToken> findByUser(User user);
//
//    Optional<RefreshToken> findByToken(String token);

}
