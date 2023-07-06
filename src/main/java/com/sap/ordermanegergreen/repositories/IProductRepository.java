package com.sap.ordermanegergreen.repositories;

import com.sap.ordermanegergreen.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface IProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String name);
    List<Product> findAllByCompanyId(String companyId);

}
