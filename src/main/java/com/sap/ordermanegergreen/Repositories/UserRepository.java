package com.sap.ordermanegergreen.Repository;

import com.sap.ordermanegergreen.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends MongoRepository<User, String> {
//    User findByName(String id);
    //User getUserFromDB(String id);
    // Additional custom query methods can be added here
}