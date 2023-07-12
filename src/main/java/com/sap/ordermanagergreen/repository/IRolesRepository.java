package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.AvailableRoles;
import com.sap.ordermanagergreen.model.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface IRolesRepository extends MongoRepository<Roles, String> {

    Roles getByName(AvailableRoles name);

}
