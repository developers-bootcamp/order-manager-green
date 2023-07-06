package com.sap.ordermanegergreen.repositories;


import com.sap.ordermanegergreen.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ICustomerRepository extends MongoRepository<User, String> {
}
