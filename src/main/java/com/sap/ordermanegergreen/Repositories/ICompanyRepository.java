package com.sap.ordermanegergreen.Repositories;
import com.sap.ordermanegergreen.Models.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


    @Component
    public interface ICompanyRepository extends MongoRepository<Company, String> {
    }


