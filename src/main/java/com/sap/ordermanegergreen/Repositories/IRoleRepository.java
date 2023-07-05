package com.sap.ordermanegergreen.Repositories;

import com.sap.ordermanegergreen.Models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRoleRepository extends MongoRepository<Role, String> {
}
