package com.sap.ordermanegergreen.Controllers;
import com.sap.ordermanegergreen.Services.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@Controller
@RestController("/ProductCategory")
public class ProductCategoryController {
    private IProductCategoryService ProductCategoryService;
    @Autowired
    public ProductCategoryController(IProductCategoryService ProductCategoryService){
        this.ProductCategoryService=ProductCategoryService;
    }
    //@GetMapping("/Create")
    //@DeleteMapping("/delete/{id}")
    //@PutMapping("/update")
    @PostMapping("/Create")
    public void createProductCategory(String id){
        ProductCategoryService.saveProductCategory(id);
    }


}
