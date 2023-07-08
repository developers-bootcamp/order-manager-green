package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.User;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;

@Component
public interface ICustomerRepository extends MongoRepository<User, String> {
}
