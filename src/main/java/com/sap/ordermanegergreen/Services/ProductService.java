package com.sap.ordermanegergreen.Services;
import com.sap.ordermanegergreen.DTO.ProductDto;
import com.sap.ordermanegergreen.DTO.TokenDTO;
import com.sap.ordermanegergreen.Exception.NoPremissionException;
import com.sap.ordermanegergreen.Exception.ObjectExistException;
import com.sap.ordermanegergreen.Models.*;
import com.sap.ordermanegergreen.Repositories.ICompanyRepository;
import com.sap.ordermanegergreen.Repositories.IProductCategoryRepository;
import com.sap.ordermanegergreen.Repositories.IProductRepository;
import com.sap.ordermanegergreen.Repositories.IRoleRepository;
import com.sap.ordermanegergreen.Utils.JwtToken;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.sap.ordermanegergreen.Models.Role;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
@Service
public class ProductService
{

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

    public void addProduct(Product product,String token) {
       if(productRepository.existsByName(product.getName()))
        throw new ObjectExistException("product name already exist");
        TokenDTO tokenDTO=jwtToken.decodeToken(token);
       if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName()==AvailableRoles.CUSTOMER)
           throw new NoPremissionException("You don't have permission to delete the product");
        product.setCompany(new Company(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null)));
        product.setAuditData(new AuditData(new Date()));
        productRepository.save(product);
    }

    public List<ProductDto> getAllNames(String token) {
        TokenDTO tokenDTO=jwtToken.decodeToken(token);
        List<Product>products= productRepository.findAllByCompanyId(tokenDTO.getCompanyId());
        Type listType = new TypeToken<List<ProductDto>>() {}.getType();
        return modelMapper.map(products, listType);
    }
//
    public List<Product> getAll(String token) {
        TokenDTO tokenDTO=jwtToken.decodeToken(token);
        return productRepository.findAllByCompanyId(tokenDTO.getCompanyId());
    }
//
    public void editById(String id, Product product,String token) {
        TokenDTO tokenDTO=jwtToken.decodeToken(token);
        Product prevProduct= productRepository.findById(id).orElse(null);
if(prevProduct.getCompany().getId()!=tokenDTO.getCompanyId()||roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName()==AvailableRoles.CUSTOMER)
    throw new NoPremissionException("You don't have permission to delete the product");
        product.setCompany(new Company(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null)));
        product.setAuditData(new AuditData(product.getAuditData().getCreateDate(),new Date()));
        productRepository.save(product);
    }

    public void deleteById(String id,String token) {
        TokenDTO tokenDTO=jwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName()==AvailableRoles.CUSTOMER||tokenDTO.getCompanyId()!=productRepository.findById(id).orElse(new Product()).getCompany().getId())
            throw new NoPremissionException("You don't have permission to delete the product");
            productRepository.deleteById(id);
    }
    }




