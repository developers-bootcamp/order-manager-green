//package com.sap.ordermanegergreen.Services;
//
//
//import com.sap.ordermanegergreen.Models.Product;
//import com.sap.ordermanegergreen.Repositories.IProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@Service
//public class ProductService
//{
//
//    IProductRepository ProductRepository;
//    @Autowired
//    public ProductService(IProductRepository ProductRepository) {
//        this.ProductRepository = ProductRepository;
//    }
//
//    public void addProduct(Product product) {
//       if(ProductRepository.existsByName(product.getName()))
//        throw new ResponseStatusException(HttpStatus.CONFLICT,"product name already exist");
//        ProductRepository.insert(product);
//    }
//
//    public List<Product> getAllNames() {
//        List<Product>products=ProductRepository.
//        return null;
//    }
//
//    public List<Product> getAll() {
//        return null;
//    }
//
//    public void editProduct(String id, Product product) {
//        ProductRepository.findById(id);
//        ProductRepository.save(product);
//    }
//
//    public void deleteById(String id) {
//        ProductRepository.deleteById(id);
//    }
//}
//
//
//
