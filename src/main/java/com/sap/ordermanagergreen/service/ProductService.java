package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.dto.ProductDTO;
import com.sap.ordermanagergreen.dto.ProductNameDTO;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.NoPremissionException;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.mapper.ProductMapper;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.repository.*;
import com.sap.ordermanagergreen.util.JwtToken;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> get(String token) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        List<Product> products= productRepository.findAllByCompany_Id(tokenDTO.getCompanyId());
        List<ProductDTO>productDTO=productMapper.INSTANCE.productToDto(products);
        return productDTO;
    }

    public Map<String,String> getNames(String token, String prefix) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        System.out.println(tokenDTO.getCompanyId());
        List<Product> products = productRepository.findProductsByNameStartingWithAndCompany_IdEqual(prefix,tokenDTO.getCompanyId());
        Map<String, String> toReturn = new HashMap<>();
        products.forEach(product -> toReturn.put(product.getId(), product.getName()));
        return toReturn;
    }

    public void add(ProductDTO productDto, String token) throws ObjectExistException,NoPremissionException {
        if (productRepository.existsByName(productDto.getName()))
            throw new ObjectExistException("product name already exist");
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPremissionException("You don't have permission to delete the product");
        Product product=productMapper.INSTANCE.dtoToProduct(productDto);
        product.setCategory(productCategoryRepository.findByName(productDto.getProductCategoryName()));
        product.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        product.setAuditData(new AuditData());
        productRepository.save(product);
    }

    public void update(String id, ProductDTO productDto, String token)throws ObjectExistException,NoPremissionException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        Product prevProduct = productRepository.findById(id).orElse(null);
        if (productRepository.existsByName(productDto.getName()) && !prevProduct.getName().equals(productDto.getName()))
            throw new ObjectExistException("product name already exist");
        if (!prevProduct.getCompany().getId().equals(tokenDTO.getCompanyId()) || roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPremissionException("You don't have permission to delete the product");

        Product product=productMapper.INSTANCE.dtoToProduct(productDto);
        product.setCategory(productCategoryRepository.findByName(productDto.getProductCategoryName()));
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




