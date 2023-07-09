package com.sap.ordermanegergreen.Repositories;

import com.sap.ordermanegergreen.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
    public interface IUserRepository extends MongoRepository<User, String> {
        boolean existsByFullName(String fullName);
        User findByAddress_Email(String email);

}





