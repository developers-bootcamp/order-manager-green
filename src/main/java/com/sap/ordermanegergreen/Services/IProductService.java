package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.Product;

import java.util.List;

public interface IProductService {
    public void addProduct(Product product);
    public List<Product>getAllProductsName();
    public Product getProductById(int productId);
    public void editProduct(int productId,Product product);
    public void eProduct(int productId,Product product);

}
