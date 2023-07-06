package com.sap.ordermanegergreen.services;

import com.sap.ordermanegergreen.dto.ProductDto;
import com.sap.ordermanegergreen.dto.TokenDTO;
import com.sap.ordermanegergreen.exception.NoPremissionException;
import com.sap.ordermanegergreen.exception.ObjectExistException;
import com.sap.ordermanegergreen.models.*;
import com.sap.ordermanegergreen.repositories.*;
import com.sap.ordermanegergreen.utils.JwtToken;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICompanyRepository companyRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProductCategoryRepository productCategoryRepository;
    public String fill() {
        AuditData d = new AuditData(new Date(), new Date());
        Company c = new Company("7", "AAAAAAAAA", "55", d);
        companyRepository.save(c);
        Role roles = new Role("3", AvailableRoles.ADMIN, "cust", d);
        roleRepository.save(roles);
        Address a = new Address("0580000000", "mezada 7", "aaa");
        User user = new User("8", "A", "a", a, roles, c, d);
        userRepository.save(user);
        ProductCategory productCategory = new ProductCategory("6", "bc", "yu", c, d);
        productCategoryRepository.save(productCategory);
        ProductCategory productCategory1 = new ProductCategory("7", "as", "vg", c, d);
        productCategoryRepository.save(productCategory1);
        return jwtToken.generateToken(user);
    }
    public void add(Product product, String token) {
        if (productRepository.existsByName(product.getName()))
            throw new ObjectExistException("product name already exist");
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRoles.CUSTOMER))
            throw new NoPremissionException("You don't have permission to delete the product");
        product.setCompany(new Company(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null)));
        product.setAuditData(new AuditData(new Date()));
        productRepository.save(product);
    }
    public List<ProductDto> getAllNames(String token) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        List<Product> products = productRepository.findAllByCompanyId(tokenDTO.getCompanyId());
        Type listType = new TypeToken<List<ProductDto>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }
    public List<Product> getAll(String token) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        return productRepository.findAllByCompanyId(tokenDTO.getCompanyId());
    }
    public void editById(String id, Product product, String token) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        Product prevProduct = productRepository.findById(id).orElse(null);
        if (productRepository.existsByName(product.getName()) && !prevProduct.getName().equals(product.getName()))
            throw new ObjectExistException("product name already exist");
        if (!prevProduct.getCompany().getId().equals(tokenDTO.getCompanyId()) || roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRoles.CUSTOMER))
            throw new NoPremissionException("You don't have permission to delete the product");
        product.setCompany(new Company(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null)));
        product.setAuditData(new AuditData(prevProduct.getAuditData().getCreateDate(), new Date()));
        productRepository.save(product);
    }
    public void deleteById(String id, String token) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRoles.CUSTOMER) || !tokenDTO.getCompanyId().equals(productRepository.findById(id).orElse(null).getCompany().getId()))
            throw new NoPremissionException("You don't have permission to delete the product");
        productRepository.deleteById(id);
    }
}




