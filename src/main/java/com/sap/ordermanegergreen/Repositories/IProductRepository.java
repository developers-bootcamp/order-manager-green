package com.sap.ordermanegergreen.Repositories;

import com.sap.ordermanegergreen.Models.Company;
import com.sap.ordermanegergreen.Models.Product;
import com.sap.ordermanegergreen.Models.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface IProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String name);
    List<Product> findAllByCompanyId(String companyId);

}
