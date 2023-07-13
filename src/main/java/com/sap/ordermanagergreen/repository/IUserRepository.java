package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.User;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;

@Component
public interface IUserRepository extends MongoRepository<User, String> {
    
    boolean existsByAddress_Email(String email);

    User findByAddress_Email(String email);

    boolean existsByFullName(String fullName);

}
