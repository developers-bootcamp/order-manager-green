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

    public List<Product> get(String token) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        List<Product> products= productRepository.findAllByCompany_Id(tokenDTO.getCompanyId());
        Type listType = new TypeToken<List<ProductDTO>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }

    public List<ProductNameDTO> getNames(String token, String prefix) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        System.out.println(tokenDTO.getCompanyId());
        List<Product> products = productRepository.findProductsByNameStartingWithAndCompany_IdEqual(prefix,tokenDTO.getCompanyId());
        Type listType = new TypeToken<List<ProductNameDTO>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }

    public void add(Product product, String token) throws ObjectExistException,NoPremissionException {
        if (productRepository.existsByName(product.getName()))
            throw new ObjectExistException("product name already exist");
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPremissionException("You don't have permission to delete the product");
        product.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        product.setAuditData(new AuditData());
        productRepository.save(product);
    }

    public void update(String id, Product product, String token)throws ObjectExistException,NoPremissionException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        Product prevProduct = productRepository.findById(id).orElse(null);
        if (productRepository.existsByName(product.getName()) && !prevProduct.getName().equals(product.getName()))
            throw new ObjectExistException("product name already exist");
        if (!prevProduct.getCompany().getId().equals(tokenDTO.getCompanyId()) || roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPremissionException("You don't have permission to delete the product");
        product.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        product.setAuditData(new AuditData(prevProduct.getAuditData().getCreateDate(), LocalDateTime.now()));
        productRepository.save(product);
    }

    public void delete(String id, String token)throws NoPremissionException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER) || !tokenDTO.getCompanyId().equals(productRepository.findById(id).orElse(null).getCompany().getId()))
            throw new NoPremissionException("You don't have permission to delete the product");
        productRepository.deleteById(id);
    }
}




