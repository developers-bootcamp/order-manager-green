package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.UserDTO;
import com.sap.ordermanagergreen.exception.NoPermissionException;
import com.sap.ordermanagergreen.exception.NotValidException;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.mapper.UserMapper;
import com.sap.ordermanagergreen.model.Currency;
import com.sap.ordermanagergreen.repository.ICompanyRepository;
import com.sap.ordermanagergreen.repository.IRoleRepository;
import com.sap.ordermanagergreen.repository.IUserRepository;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    IRoleRepository roleRepository;
    @Autowired
    ICompanyRepository companyRepository;

    public List<UserDTO> get(String companyId) {
        List<User> users = userRepository.findAllByCompany_IdOrderByRoleIdAscAuditData_UpdateDateDesc(companyId);
        List<UserDTO> toReturn = new ArrayList<>();
        users.forEach(e -> toReturn.add(userMapper.UserToUserDTO(e)));
        return toReturn;
    }
    @SneakyThrows
    public Map<String, String> getNames(String prefixName, String companyId) {
        if (prefixName == null) {
            throw new IllegalArgumentException("invalid prefixName");
        }
        Role roleId = roleRepository.getByName(AvailableRole.CUSTOMER);
        List<User> autocomplete = userRepository.findByFullNameStartingWithAndRole_IdAndCompany_Id(prefixName, roleId.getId(), companyId);//CustomersByNameStartingWithAndRoleIdEqualAndCompanyIdEqual(prefixName,roleId.getId(),companyId);
        Map<String, String> toReturn = new HashMap<>();
        autocomplete.forEach(customer -> toReturn.put(customer.getId(), customer.getFullName()));
        return toReturn;
    }

    public Map<String,Object> logIn(String userEmail, String userPassword) {
        User user = isEmailExists(userEmail);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found. Please sign up");
        if(user.getRole().getName()==AvailableRole.CUSTOMER)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Costumer is not able to login");
        else {
            if (!user.getPassword().equals(userPassword))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            Map<String,Object> map=new HashMap<>();
            String token = JwtToken.generateToken(user);
            String availableRole=user.getRole().getName().toString();
            map.put("token",token);
            map.put("role",availableRole);
            map.put("companyId",user.getCompany().getId());
            map.put("currency",user.getCompany().getCurrency());

            return map;
        }
    }

    @Transactional
    @SneakyThrows
    public User signUp(String fullName, String companyName, String email, String password, String currency) throws Exception {
        //password validations?
        if (userRepository.existsByFullName(fullName)) {
            throw new ObjectExistException("user name ");
        }
        if (companyRepository.existsByName(companyName)) {
            throw new ObjectExistException("company");
        }
        validation(password,email);
        Company company = Company.builder().name(companyName).currency(Currency.valueOf(currency)).auditData(new AuditData()).build();
        companyRepository.save(company);
        User user = User.builder().fullName(fullName).company(company).address(Address.builder().email(email).build()).password(password).role(roleRepository.getByName(AvailableRole.ADMIN)).auditData(new AuditData()).build();
        userRepository.save(user);
        return user;
    }


    public void add(String token, UserDTO userDto) throws ObjectExistException, NoPermissionException, NotValidException {

        validation(userDto.getPassword(),userDto.getEmail());
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (userRepository.existsByFullNameAndCompany_Id(userDto.getFullName(),tokenDTO.getCompanyId())) {
            throw new ObjectExistException("user name ");
        }
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER))
            throw new NoPermissionException("role");
        User user=userMapper.INSTANCE.UserDTOToUser(userDto);
        user.setRole(roleRepository.getByName(user.getRole().getName()));
        user.setAuditData(new AuditData(LocalDateTime.now(), LocalDateTime.now()));
        user.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        userRepository.save(user);
    }

    public void update(String token, UserDTO userDto,String id) throws NoPermissionException,NotValidException,ObjectExistException {
        if (userDto.getPassword().contains(" ")) {
            throw new NotValidException("password");
        }
        //email validations?
        if (!userDto.getEmail().contains("@")) {
            throw new NotValidException("email");
        }
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        User prevUser = userRepository.findById(id).orElse(null);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(null).getName().equals(AvailableRole.CUSTOMER)
                || (companyRepository.findById(tokenDTO.getCompanyId()).isEmpty()))
            throw new NoPermissionException("You don't have permission to delete the user");
        User user=userMapper.INSTANCE.UserDTOToUser(userDto);
        if (userRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
        if (userRepository.existsByFullNameAndCompany_Id(userDto.getFullName(),tokenDTO.getCompanyId())&&!prevUser.getFullName().equals(user.getFullName())){
            throw new ObjectExistException("user name ");
        }

        user.setRole(roleRepository.getByName(user.getRole().getName()));
        user.setAuditData(new AuditData(prevUser.getAuditData().getCreateDate(), LocalDateTime.now()));
        user.setCompany(companyRepository.findById(tokenDTO.getCompanyId()).orElse(null));
        userRepository.save(user);
    }

    public void delete(String token, String userId) throws NoPermissionException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(new Role()).getName() ==
                AvailableRole.CUSTOMER || !(companyRepository.findById(tokenDTO.getCompanyId()).
                orElse(new Company()).getId().equals(userRepository.findById(userId).
                        orElse(new User()).getCompany().getId()))) {
            throw new NoPermissionException("You don't have permission to delete the user");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
        userRepository.deleteById(userId);
    }

    public User isEmailExists(String email) {
        return userRepository.findByAddress_Email(email);
    }
    public void validation(String password,String email)throws NotValidException,ObjectExistException{

        if (password.contains(" ")) {
            throw new NotValidException("password");
        }
        //email validations?
        if (!email.contains("@")) {
            throw new NotValidException("email");
        }
        if (userRepository.existsByAddress_Email(email)) {
            throw new ObjectExistException("email");
        }
        if (userRepository.existsByPassword(password)) {
            throw new ObjectExistException("password");
        }

    }

}