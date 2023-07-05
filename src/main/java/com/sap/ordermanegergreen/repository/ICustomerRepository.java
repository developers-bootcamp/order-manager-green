package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.User;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;

@Component
public interface ICustomerRepository extends MongoRepository<User, String> {
}
