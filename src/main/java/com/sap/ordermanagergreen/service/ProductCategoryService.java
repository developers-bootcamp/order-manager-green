package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.ProductCategoryDTO;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import com.sap.ordermanagergreen.exception.UnauthorizedException;
import com.sap.ordermanagergreen.mapper.ProductCategoryMapper;
import com.sap.ordermanagergreen.model.AuditData;
import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.repository.ICompanyRepository;
import com.sap.ordermanagergreen.repository.IRoleRepository;
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
    @Autowired
    private IRoleRepository roleRepository;


    public void add(String token, ProductCategory productCategory) throws ObjectExistException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (!isUnauthorized(token))
            throw new UnauthorizedException();
        String categoryName = productCategory.getName();
        if (doesCategoryExist(categoryName) == true) {
            throw new ObjectExistException("Category name already exists");
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

    public void delete(String token, String id) throws ObjectNotExistException {

        if (!isUnauthorized(token))
            throw new UnauthorizedException();
        if (ProductCategoryRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistException("Category not found");
        }
        ProductCategoryRepository.deleteById(id);

    }


    public void update(String id, ProductCategory productCategory, String token) throws ObjectNotExistException {
        if (!isUnauthorized(token))
            throw new UnauthorizedException();
        Optional<ProductCategory> oldProductCategory = ProductCategoryRepository.findById(id);
        if (oldProductCategory.isEmpty()) {
            throw new ObjectNotExistException("Category does not exist");
        }
        ProductCategoryRepository.save(productCategory);
    }

    public boolean doesCategoryExist(String categoryName) {
        return ProductCategoryRepository.existsByName(categoryName);
    }

    public boolean isUnauthorized(String token) {
        AvailableRole availableRole= roleRepository.findById(JwtToken.decodeToken(token).getRoleId()).orElse(null).getName();
        if (availableRole.equals(AvailableRole.ADMIN) || availableRole.equals(AvailableRole.EMPLOYEE))
            return true;
        return false;
    }
}

