package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.AvailableRoles;
import com.sap.ordermanegergreen.model.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface IRolesRepository extends MongoRepository<Roles, String> {

    Roles getByName(AvailableRoles name);

}
