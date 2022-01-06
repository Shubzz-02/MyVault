package com.shubzz.myvault.repository;

import com.shubzz.myvault.modes.BannedToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BannedTokenRepository extends MongoRepository<BannedToken, String> {

    List<BannedToken> findByUsername(String username);

    Boolean existsByToken(String token);
}
