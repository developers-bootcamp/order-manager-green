package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.Company;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;


@Component
public interface ICompanyRepository extends MongoRepository<Company, String> {
    boolean existsByName(String name);
}
