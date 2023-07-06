package com.sap.ordermanegergreen.repositories;
import com.sap.ordermanegergreen.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
public interface IUserRepository extends MongoRepository<User, String> {
    boolean existsByFullName(String fullName);
}


