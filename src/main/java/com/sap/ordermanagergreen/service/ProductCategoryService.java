package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.ProductCategoryDTO;
import com.sap.ordermanagergreen.mapper.ProductCategoryMapper;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import com.sap.ordermanagergreen.exception.UnauthorizedException;
import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.model.Role;
import com.sap.ordermanagergreen.repository.ICompanyRepository;
import com.sap.ordermanagergreen.repository.IRoleRepository;
import com.sap.ordermanagergreen.util.JwtToken;
import com.sap.ordermanagergreen.model.ProductCategory;
import com.sap.ordermanagergreen.repository.IProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    @Autowired
    private IProductCategoryRepository ProductCategoryRepository;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    public void add(String token, ProductCategory productCategory) throws ObjectExistException {
        if (!isUnauthorized(token))
            throw new UnauthorizedException();
        String categoryName = productCategory.getName();
        if (doesCategoryExist(categoryName) == true) {
            throw new ObjectExistException("Category name");
        }
        productCategory.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        productCategory.setAuditData(new AuditData());
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
            throw new ObjectNotExistException("Category");
        }
        ProductCategoryRepository.deleteById(id);
    }

    public void update(String id, ProductCategory productCategory, String token) throws ObjectNotExistException {
        if (!isUnauthorized(token))
            throw new UnauthorizedException();
        Optional<ProductCategory> oldProductCategory = ProductCategoryRepository.findById(id);
        if (oldProductCategory.isEmpty()) {
            throw new ObjectNotExistException("Category");
        }
        ProductCategoryRepository.save(productCategory);
    }

    public boolean doesCategoryExist(String categoryName) {
        return ProductCategoryRepository.existsByName(categoryName);
    }

    public boolean isUnauthorized(String token) {
        String roleId = JwtToken.decodeToken(token).getRoleId();
        if (roleId.equals(AvailableRole.ADMIN) || roleId.equals(AvailableRole.EMPLOYEE))
            return true;
        return false;
    }

}