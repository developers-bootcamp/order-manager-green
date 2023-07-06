package com.sap.ordermanegergreen.repositories;
import com.sap.ordermanegergreen.models.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


    @Component
    public interface ICompanyRepository extends MongoRepository<Company, String> {
    }


