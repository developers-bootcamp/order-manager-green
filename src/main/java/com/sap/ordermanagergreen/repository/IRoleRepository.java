package com.sap.ordermanegergreen.Repositories;

import com.sap.ordermanegergreen.Models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends MongoRepository<Role, String> {
    boolean findNameById(String id);

}





