package com.sap.ordermanegergreen.Services;

import ch.qos.logback.core.subst.Token;
import com.mongodb.client.FindIterable;
import com.sap.ordermanegergreen.DTO.ProductCategoryDTO;
import com.sap.ordermanegergreen.DTO.TokenDTO;
import com.sap.ordermanegergreen.Exeption.InternalServerException;
import com.sap.ordermanegergreen.Exeption.ObjectAlreadyExistsExeption;
import com.sap.ordermanegergreen.Exeption.ObjectNotFoundExeption;
import com.sap.ordermanegergreen.Exeption.UnauthorizedExeption;
import com.sap.ordermanegergreen.Models.AvailableRoles;
import com.sap.ordermanegergreen.Models.ProductCategory;
import com.sap.ordermanegergreen.Models.Roles;
import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Repositories.IProductCategoryRepository;
import com.sap.ordermanegergreen.Repositories.IRolesRepository;
import com.sap.ordermanegergreen.Utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sap.ordermanegergreen.DTO.ProductCategoryMapper;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {
    IProductCategoryRepository ProductCategoryRepository;
    JwtToken jwtToken;
    private final ProductCategoryMapper productCategoryMapper;
    private final IRolesRepository rolesRepository;

    @Autowired
    public ProductCategoryService(IProductCategoryRepository ProductCategoryRepository, ProductCategoryMapper productCategoryMapper, JwtToken jwtToken, IRolesRepository rolesRepository) {
        this.ProductCategoryRepository = ProductCategoryRepository;
        this.productCategoryMapper = productCategoryMapper;
        this.jwtToken = jwtToken;
        this.rolesRepository = rolesRepository;
    }
    public ResponseEntity<String> saveProductCategory(String token, ProductCategory productCategory) {
//            try {
//                if(isUnauthorized(token))
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }catch (ObjectNotFoundExeption objectNotFoundExeption){
//                throw objectNotFoundExeption;
//            }
        if (!isUnauthorized(token))
            throw new UnauthorizedExeption();
        String categoryName = productCategory.getName();
        if (doesCategoryExist(categoryName) == true) {
            throw new ObjectAlreadyExistsExeption("Category name already exists");
        }
        ProductCategoryRepository.save(productCategory);
        return ResponseEntity.ok("success: true");

    }

    public ResponseEntity<List<ProductCategoryDTO>> getAllCategories(String token) {
        String companyId = jwtToken.decodeToken(token).getCompanyId();
        List<ProductCategory> productCategories = ProductCategoryRepository.findAllByCompanyId(companyId);
        List<ProductCategoryDTO> productCategoryDTOs = productCategoryMapper.toDtoList(productCategories);
        return ResponseEntity.ok(productCategoryDTOs);
    }

    public ResponseEntity<String> deleteProductCategory(String token, String id) {

        if (!isUnauthorized(token))
            throw new UnauthorizedExeption();
        if (ProductCategoryRepository.findById(id).isEmpty()) {
            throw new ObjectNotFoundExeption("Category not found");
        }
        ProductCategoryRepository.deleteById(id);
        return new ResponseEntity<>("{\"success\": true}", HttpStatus.OK);

    }


    public ResponseEntity<String> editProductCategory(String id, ProductCategory productCategory, String token) {
        if (!isUnauthorized(token))
            throw new UnauthorizedExeption();
        Optional<ProductCategory> oldProductCategory = ProductCategoryRepository.findById(id);
        if (oldProductCategory.isEmpty()) {
            throw new ObjectNotFoundExeption("Category does not exist");
        }
        ProductCategoryRepository.save(productCategory);
        return ResponseEntity.ok("success: true");
    }

    public boolean doesCategoryExist(String categoryName) {
        return ProductCategoryRepository.existsByName(categoryName);
    }

    public boolean isUnauthorized(String token) {
        String roleId = jwtToken.decodeToken(token).getRoleId();
        Optional<Roles> roleOptional = rolesRepository.findById(roleId);
        if (!roleOptional.isPresent())
            throw new ObjectNotFoundExeption("Role not found");
        AvailableRoles roleName = roleOptional.get().getName();
        if (roleName != AvailableRoles.ADMIN && roleName != AvailableRoles.ADMIN.EMPLOYEE)
            return false;
        return true;
        }
    }
