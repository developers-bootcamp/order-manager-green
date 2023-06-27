package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.ProductCategory;
import com.sap.ordermanegergreen.Repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService implements IProductCategoryService{
    ProductCategoryRepository ProductCategoryRepository;
    @Autowired
    public ProductCategoryService(ProductCategoryRepository ProductCategoryRepository) {
        this.ProductCategoryRepository = ProductCategoryRepository;
    }
    @Override
    public void saveProductCategory(String id) {
        ProductCategory p = new ProductCategory(id);
        ProductCategoryRepository.save(p);
    }

}
