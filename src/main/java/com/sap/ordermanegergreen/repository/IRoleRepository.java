package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.AvailableRoles;
import com.sap.ordermanegergreen.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends MongoRepository<Role, String> {
    @Query(value = "{'name': ?0}", fields = "{'_id': 1}")
     Role findByName(AvailableRoles name);
}
