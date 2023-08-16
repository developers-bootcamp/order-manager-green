package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.ProductCategoryDTO;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.ObjectAlreadyExistsExeption;
import com.sap.ordermanagergreen.exception.ObjectNotFoundExeption;
import com.sap.ordermanagergreen.exception.UnauthorizedExeption;
import com.sap.ordermanagergreen.mapper.ProductCategoryMapper;
import com.sap.ordermanagergreen.model.AuditData;
import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.repository.ICompanyRepository;
import com.sap.ordermanagergreen.util.JwtToken;
import org.springframework.stereotype.Service;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import com.sap.ordermanagergreen.repository.IProductCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {
    @Autowired
    private IProductCategoryRepository ProductCategoryRepository;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ICompanyRepository companyRepository;


    public void add(String token, ProductCategory productCategory) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (!isUnauthorized(token))
            throw new UnauthorizedExeption();
        String categoryName = productCategory.getName();
        if (doesCategoryExist(categoryName) == true) {
            throw new ObjectAlreadyExistsExeption("Category name already exists");
        }
        productCategory.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        productCategory.setAuditData(new AuditData());
        ProductCategoryRepository.save(productCategory);
        ProductCategoryRepository.save(productCategory);

    }

    public List<ProductCategoryDTO> get(String token) {
        String companyId = JwtToken.decodeToken(token).getCompanyId();
        List<ProductCategory> productCategories = ProductCategoryRepository.findAllByCompany_Id(companyId);
        List<ProductCategoryDTO> productCategoryDTOs = productCategoryMapper.toDtoList(productCategories);
        return productCategoryDTOs;
    }

    public void delete(String token, String id) {

        if (!isUnauthorized(token))
            throw new UnauthorizedExeption();
        if (ProductCategoryRepository.findById(id).isEmpty()) {
            throw new ObjectNotFoundExeption("Category not found");
        }
        ProductCategoryRepository.deleteById(id);

    }


    public void update(String id, ProductCategory productCategory, String token) {
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
        if (roleId.equals(AvailableRole.ADMIN) || roleId.equals(AvailableRole.EMPLOYEE))
            return true;
        return false;
    }
}

