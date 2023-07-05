package com.sap.ordermanegergreen.Repositories;

import com.sap.ordermanegergreen.Models.Product;
import com.sap.ordermanegergreen.Models.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface IRolesRepository extends MongoRepository<Roles, String> {
}
