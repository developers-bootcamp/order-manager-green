package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.User;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;

@Component
public interface IUserRepository extends MongoRepository<User, String> {
    boolean existsByAddress_Email(String email);

}
