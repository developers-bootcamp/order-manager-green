package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.DTO.ProductCategoryDTO;
import com.sap.ordermanegergreen.Exeption.ObjectAlreadyExistsExeption;
import com.sap.ordermanegergreen.Exeption.ObjectNotFoundExeption;
import com.sap.ordermanegergreen.Exeption.UnauthorizedExeption;
import com.sap.ordermanegergreen.Models.AvailableRoles;
import com.sap.ordermanegergreen.Models.ProductCategory;
import com.sap.ordermanegergreen.Models.Roles;
import com.sap.ordermanegergreen.Repositories.IProductCategoryRepository;
import com.sap.ordermanegergreen.Repositories.IRolesRepository;
import com.sap.ordermanegergreen.Utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sap.ordermanegergreen.DTO.ProductCategoryMapper;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {
    IProductCategoryRepository ProductCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    @Autowired
    public ProductCategoryService(IProductCategoryRepository ProductCategoryRepository, ProductCategoryMapper productCategoryMapper) {
        this.ProductCategoryRepository = ProductCategoryRepository;
        this.productCategoryMapper = productCategoryMapper;
    }
    public void saveProductCategory(String token, ProductCategory productCategory) {
        if (!isUnauthorized(token))
            throw new UnauthorizedExeption();
        String categoryName = productCategory.getName();
        if (doesCategoryExist(categoryName) == true) {
            throw new ObjectAlreadyExistsExeption("Category name already exists");
        }
       ProductCategoryRepository.save(productCategory);

    }

    public List<ProductCategoryDTO> getAllCategories(String token) {
        String companyId = JwtToken.decodeToken(token).getCompanyId();
        List<ProductCategory> productCategories = ProductCategoryRepository.findAllByCompanyId(companyId);
        List<ProductCategoryDTO> productCategoryDTOs = productCategoryMapper.toDtoList(productCategories);
        return productCategoryDTOs;
    }

    public void deleteProductCategory(String token, String id) {

        if (!isUnauthorized(token))
            throw new UnauthorizedExeption();
        if (ProductCategoryRepository.findById(id).isEmpty()) {
            throw new ObjectNotFoundExeption("Category not found");
        }
        ProductCategoryRepository.deleteById(id);

    }


    public void editProductCategory(String id, ProductCategory productCategory, String token) {
        if (!isUnauthorized(token))
            throw new UnauthorizedExeption();
        Optional<ProductCategory> oldProductCategory = ProductCategoryRepository.findById(id);
        if (oldProductCategory.isEmpty()) {
            throw new ObjectNotFoundExeption("Category does not exist");
        }
        ProductCategoryRepository.save(productCategory);
    }

    public boolean doesCategoryExist(String categoryName) {
        return ProductCategoryRepository.existsByName(categoryName);
    }

    public boolean isUnauthorized(String token) {
        String roleId=JwtToken.decodeToken(token).getRoleId();
        if (roleId.equals(AvailableRoles.ADMIN) || roleId.equals(AvailableRoles.EMPLOYEE))
              return true;
        return false;
        }
    }
