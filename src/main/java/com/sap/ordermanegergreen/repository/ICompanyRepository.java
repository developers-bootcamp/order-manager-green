package com.sap.ordermanegergreen.repository;
import com.sap.ordermanegergreen.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


    @Component
    public interface ICompanyRepository extends MongoRepository<Company, String> {
    }


