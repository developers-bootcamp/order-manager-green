package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
    @Component
    public interface IUserRepository extends MongoRepository<User, String> {
    }

