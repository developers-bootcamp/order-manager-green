package com.sap.ordermanegergreen.Repositorys;

import com.sap.ordermanegergreen.Models.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface CompanyRepository extends MongoRepository<Company, String> {
}