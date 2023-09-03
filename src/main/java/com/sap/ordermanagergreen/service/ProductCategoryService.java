package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.ProductCategoryDTO;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.NoPermissionException;
import com.sap.ordermanagergreen.mapper.ProductCategoryMapper;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import com.sap.ordermanagergreen.exception.UnauthorizedException;
import com.sap.ordermanagergreen.model.AuditData;
import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.model.Role;
import com.sap.ordermanagergreen.repository.ICompanyRepository;
import com.sap.ordermanagergreen.repository.IRoleRepository;
import com.sap.ordermanagergreen.util.JwtToken;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import com.sap.ordermanagergreen.model.ProductCategory;
import com.sap.ordermanagergreen.repository.IProductCategoryRepository;
import org.springframework.stereotype.Service;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import com.sap.ordermanagergreen.repository.IProductCategoryRepository;

import java.time.LocalDateTime;
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
    private  IRoleRepository roleRepository;


    public void add(String token, ProductCategory productCategory) throws ObjectExistException,NoPermissionException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPermissionException("You don't have permission to delete the product");
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

    public void delete(String token, String id) throws ObjectNotExistException,NoPermissionException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPermissionException("You don't have permission to delete the product");
        if (ProductCategoryRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistException("Category not found");
        }
        ProductCategoryRepository.deleteById(id);
    }

    public void update(String id, ProductCategory productCategory, String token) throws ObjectNotExistException,UnauthorizedException,NoPermissionException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        ProductCategory oldProductCategory = ProductCategoryRepository.findById(id).orElse(null);

        if (!oldProductCategory.getCompany().getId().equals(tokenDTO.getCompanyId()) || roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPermissionException("You don't have permission to delete the product");
        if (oldProductCategory==null) {
            throw new ObjectNotExistException("Category");
        }
        if (!oldProductCategory.getCompany().getId().equals(tokenDTO.getCompanyId()) || roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPermissionException("You don't have permission to delete the product");
        productCategory.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        productCategory.setAuditData(new AuditData(oldProductCategory.getAuditData().getCreateDate(), LocalDateTime.now()));
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