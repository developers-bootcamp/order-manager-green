package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends MongoRepository<Company, String> {
    boolean existsByName(String name);
}