package com.shubzz.myvault.repository;

import com.shubzz.myvault.modes.DataUserPass;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDataRepository extends MongoRepository<DataUserPass, String> {

    List<DataUserPass> findByUserIdOrUserName(String userId, String userName);


}
