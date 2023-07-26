package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends MongoRepository<Role, String> {

    Role getByName(AvailableRole name);

}
