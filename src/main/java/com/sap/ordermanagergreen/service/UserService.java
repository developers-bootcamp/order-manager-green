package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.UserDto;
import com.sap.ordermanagergreen.exception.NoPremissionException;
import com.sap.ordermanagergreen.exception.NotValidException;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.mapper.UserMapper;
import com.sap.ordermanagergreen.repository.ICompanyRepository;
import com.sap.ordermanagergreen.repository.IRoleRepository;
import com.sap.ordermanagergreen.repository.IUserRepository;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.util.JwtToken;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Email;
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

    public List<UserDto> get(String companyId, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<User> y = userRepository.findByCompany_IdOrderByRoleIdAscAuditData_UpdateDateDesc(companyId, pageRequest);
        List<User> users = y.getContent();
        List<UserDto> toReturn = new ArrayList<>();
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

    public User getUserByEmailAndPassword(String userEmail, String userPassword) {
        User user = isEmailExists(userEmail);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found. Please sign up");
        } else {
            if (!user.getPassword().equals(userPassword))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            return user;
        }
    }

    public User signUp(String fullName, String companyName, String email, @NotNull String password) throws NotValidException, Exception {

            User user = new User();
            user.setFullName(fullName);
            //password validations?
            if (!password.contains("")) {
                throw new NotValidException("password");
            }
            user.setPassword(password);
            //email validations?
//            if (!email.contains("@")) {
//                throw new NotValidException("email");
//            }
            if (userRepository.existsByAddress_Email(email)) {
                throw new ObjectExistException("email");
            }
            Address address = new Address();
            user.setAddress(address);
            user.getAddress().setEmail(email);
            user.setRole(roleRepository.getByName(AvailableRole.ADMIN));
            AuditData auditData = new AuditData();
            auditData.setCreateDate(LocalDateTime.now());
            auditData.setUpdateDate(LocalDateTime.now());
            user.setAuditData(auditData);
            if (companyRepository.existsByName(companyName)) {
                throw new ObjectExistException("company");
            }
            Company company = new Company();
            company.setName(companyName);
            companyRepository.save(company);
            AuditData auditData1 = new AuditData();
            auditData1.setCreateDate(LocalDateTime.now());
            auditData1.setUpdateDate(LocalDateTime.now());
            company.setAuditData(auditData1);
            user.setCompany(company);
            userRepository.save(user);
            return user;
        }


    public void add(String token, User user) throws ObjectExistException, NoPremissionException, NotValidException {
        if (userRepository.existsByFullName(user.getFullName())) {
            throw new ObjectExistException("user name ");
        }
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        //cheak password mail telephone...
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(new Role()).getName() == AvailableRole.CUSTOMER)
            throw new NoPremissionException("role");
        if (roleRepository.findById(tokenDTO.getRoleId()).isEmpty())
            throw new NotValidException("role");
        user.setRole(roleRepository.findById(user.getRole().getId()).get());
        //user.getRoleId().getAuditData().setUpdateDate(new Date());
        user.getRole().setAuditData(new AuditData(LocalDateTime.now(), LocalDateTime.now()));
        user.setAuditData(new AuditData(LocalDateTime.now(), LocalDateTime.now()));
        if (!companyRepository.findById(user.getCompany().getId()).orElse(new Company()).getId().equals(tokenDTO.getCompanyId())) {
            throw new NoPremissionException("company");
        }
        user.setCompany(companyRepository.findById(user.getCompany().getId()).get());
        //user.getCompanyId().getAuditData().setUpdateDate(new Date());
        user.getCompany().setAuditData(new AuditData(LocalDateTime.now(), LocalDateTime.now()));
        userRepository.save(user);
    }

    public void update(String token, User user) throws NoPremissionException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(new Role()).getName() ==
                AvailableRole.CUSTOMER || !(companyRepository.findById(tokenDTO.getCompanyId())
                .orElse(new Company()).getId().equals(userRepository.findById(user.getId())
                        .orElse(new User()).getCompany().getId()))) {
            throw new NoPremissionException("You don't have permission to delete the user");
        }
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
        userRepository.save(user);
    }

    public void delete(String token, String userId) throws NoPremissionException {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(new Role()).getName() ==
                AvailableRole.CUSTOMER || !(companyRepository.findById(tokenDTO.getCompanyId()).
                orElse(new Company()).getId().equals(userRepository.findById(userId).
                        orElse(new User()).getCompany().getId()))) {
            throw new NoPremissionException("You don't have permission to delete the user");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
        userRepository.deleteById(userId);
    }

    public User isEmailExists(String email) {
        return userRepository.findByAddress_Email(email);
    }
}