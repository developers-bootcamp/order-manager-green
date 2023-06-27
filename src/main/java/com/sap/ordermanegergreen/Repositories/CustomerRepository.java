package com.sap.ordermanegergreen.Repositorys;

import com.sap.ordermanegergreen.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface CustomerRepository extends MongoRepository<User, String> {
}