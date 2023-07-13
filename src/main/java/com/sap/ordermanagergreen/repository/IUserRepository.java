package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepository extends MongoRepository<User, String> {
    boolean existsByFullName(String fullName);
    User findByAddress_Email(String email);



}





