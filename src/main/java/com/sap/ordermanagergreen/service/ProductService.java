package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.dto.ProductDto;
import com.sap.ordermanagergreen.dto.ProductNameDto;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.NoPremissionException;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.repository.*;
import com.sap.ordermanagergreen.util.JwtToken;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        AuditData d = new AuditData(LocalDateTime.now(),LocalDateTime.now());
        Company c = new Company("7", "AAAAAAAAA", "55", d);
        companyRepository.save(c);
        Role roles = new Role("3", AvailableRole.ADMIN, "cust", d);
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
    public List<ProductNameDto> getAllNames(String token,String prefix) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        System.out.println(tokenDTO.getCompanyId());
        List<Product> products = productRepository.findProductsByNameStartingWithAndCompanyIdEqual(prefix,tokenDTO.getCompanyId());
        Type listType = new TypeToken<List<ProductNameDto>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }
    public List<Product> getAll(String token) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        List<Product> products= productRepository.findAllByCompanyId(tokenDTO.getCompanyId());
        Type listType = new TypeToken<List<ProductDto>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }
    public void add(Product product, String token) throws Exception {
        if (productRepository.existsByName(product.getName()))
            throw new ObjectExistException("product name already exist");
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPremissionException("You don't have permission to delete the product");
        product.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        product.setAuditData(new AuditData(LocalDateTime.now()));
        productRepository.save(product);
    }
    public void editById(String id, Product product, String token)throws Exception {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        Product prevProduct = productRepository.findById(id).orElse(null);
        if (productRepository.existsByName(product.getName()) && !prevProduct.getName().equals(product.getName()))
            throw new ObjectExistException("product name already exist");
        if (!prevProduct.getCompany().getId().equals(tokenDTO.getCompanyId()) || roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPremissionException("You don't have permission to delete the product");
        product.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        product.setAuditData(new AuditData(prevProduct.getAuditData().getCreateDate(), LocalDateTime.now()));
        productRepository.save(product);
    }
    public void deleteById(String id, String token)throws Exception {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER) || !tokenDTO.getCompanyId().equals(productRepository.findById(id).orElse(null).getCompany().getId()))
            throw new NoPremissionException("You don't have permission to delete the product");
        productRepository.deleteById(id);
    }
}




