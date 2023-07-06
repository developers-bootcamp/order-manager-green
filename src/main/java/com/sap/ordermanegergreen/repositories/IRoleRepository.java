package com.sap.ordermanegergreen.repositories;

import com.sap.ordermanegergreen.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRoleRepository extends MongoRepository<Role, String> {
}
