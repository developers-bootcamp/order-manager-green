package com.sap.ordermanegergreen.Services;


import com.sap.ordermanegergreen.Models.Product;
import com.sap.ordermanegergreen.Repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService implements IProductService
{

    IProductRepository ProductRepository;
    @Autowired
    public ProductService(IProductRepository ProductRepository) {
        this.ProductRepository = ProductRepository;
    }

    @Override
    public void addProduct(ProductDto productDto) {
       if(ProductRepository.findAll().stream().filter(product->product.getName()==productDto.getName()).toArray().length>0)
           throw new ResponseStatusException(HttpStatus.CONFLICT,"product name already exist");
        Product p=new Product();


        ProductRepository.save()

    }
}



