package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.Company;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;


@Component
public interface ICompanyRepository extends MongoRepository<Company, String> {
    boolean existsByName(String name);
}
