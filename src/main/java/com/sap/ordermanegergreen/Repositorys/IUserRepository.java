package com.sap.ordermanegergreen.Repositorys;

import com.sap.ordermanegergreen.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, String> {

    // Additional custom query methods can be added here


}