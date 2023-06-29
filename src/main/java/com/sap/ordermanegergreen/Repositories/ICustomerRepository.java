package com.sap.ordermanegergreen.Repositories;


import com.sap.ordermanegergreen.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ICustomerRepository extends MongoRepository<User, String> {
}
