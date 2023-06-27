package com.sap.ordermanegergreen.Repositories;

import com.sap.ordermanegergreen.Models.Product;
import com.sap.ordermanegergreen.Models.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProductRepository extends MongoRepository<Product, String> {
}
