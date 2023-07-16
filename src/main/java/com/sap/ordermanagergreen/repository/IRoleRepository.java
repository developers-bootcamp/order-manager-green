package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.AvailableRoles;
import com.sap.ordermanagergreen.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface IRoleRepository extends MongoRepository<Role, String> {

    Role getByName(AvailableRoles name);

}
