package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.ProductCategory;
import com.sap.ordermanegergreen.Repositories.IProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService implements IProductCategoryService{
    IProductCategoryRepository ProductCategoryRepository;
    @Autowired
    public ProductCategoryService(IProductCategoryRepository ProductCategoryRepository) {
        this.ProductCategoryRepository = ProductCategoryRepository;
    }
    @Override
    public void saveProductCategory(String id) {
        ProductCategory p = new ProductCategory(id);
        ProductCategoryRepository.save(p);
    }

}
