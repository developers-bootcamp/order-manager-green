package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.dto.ProductDTO;
import com.sap.ordermanagergreen.dto.ProductNameDTO;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.NoPremissionException;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.repository.*;
import com.sap.ordermanagergreen.util.JwtToken;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
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
        AuditData auditData =AuditData.builder().createDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();
        Company company = Company.builder().name("sap").currency(Currency.SHEKEL).auditData(auditData).build();
        companyRepository.save(company);
        Role role =Role.builder().name(AvailableRole.ADMIN).description("Branch manager in Israel").auditData(auditData).build();
        roleRepository.save(role);
        Address address = Address.builder().telephone("0583204192").address("Eli Horvitz 14").email("sap@gmail.com").build();
        User user =User.builder().fullName("orna").password("1234").address(address).role(role).company(company).auditData(auditData).build();
        userRepository.save(user);
        ProductCategory productCategory =ProductCategory.builder().name("Photo Album").description("None").company(company).auditData(auditData).build();
        productCategoryRepository.save(productCategory);
        return jwtToken.generateToken(user);
    }    public List<Product> get(String token) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        List<Product> products= productRepository.findAllByCompany_Id(tokenDTO.getCompanyId());
        Type listType = new TypeToken<List<ProductDTO>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }
    public List<ProductNameDTO> get(String token, String prefix) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        System.out.println(tokenDTO.getCompanyId());
        List<Product> products = productRepository.findProductsByNameStartingWithAndCompany_IdEqual(prefix,tokenDTO.getCompanyId());
        Type listType = new TypeToken<List<ProductNameDTO>>() {
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
    public void update(String id, Product product, String token)throws Exception {
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
    public void delete(String id, String token)throws Exception {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER) || !tokenDTO.getCompanyId().equals(productRepository.findById(id).orElse(null).getCompany().getId()))
            throw new NoPremissionException("You don't have permission to delete the product");
        productRepository.deleteById(id);
    }
}




