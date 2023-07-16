package com.sap.ordermanegergreen.service;

import com.sap.ordermanegergreen.dto.UserDto;
import com.sap.ordermanegergreen.exception.NotValidException;
import com.sap.ordermanegergreen.exception.ObjectAlreadyExistsException;
import com.sap.ordermanegergreen.mapper.UserMapper;
import com.sap.ordermanegergreen.model.*;
import com.sap.ordermanegergreen.repository.ICompanyRepository;
import com.sap.ordermanegergreen.repository.IRoleRepository;
import com.sap.ordermanegergreen.repository.IUserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

import com.sap.ordermanegergreen.model.AvailableRoles;

@Service
public class UserService  {

    IUserRepository userRepository;
    UserMapper userMapper;

    IRoleRepository roleRepository;
    ICompanyRepository companyRepository;

    @Autowired
    public UserService(IUserRepository userRepository, UserMapper userMapper,
                       IRoleRepository roleRepository,ICompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.userMapper=userMapper;
        this.roleRepository=roleRepository;
        this.companyRepository=companyRepository;
    }
    public List<UserDto> getAll(String companyId, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<User> y= userRepository.findByCompanyIdOrderByRoleIdAscAuditDate_UpdateDateDesc(companyId, pageRequest);
        List<User> hh=y.getContent();
        List<UserDto> toReturn =new ArrayList<>();
        hh.forEach(e->toReturn.add(userMapper.UserToUserDTO(e)));
        return toReturn;
    }

    @SneakyThrows
    public Map<String,String> getAllByPrefix(String prefixName, String companyId) {
        if(prefixName==null){
            throw new IllegalArgumentException("invalid prefixName") ;
        }
        Role roleId=roleRepository.findByName(AvailableRoles.CUSTOMER);
        List<User> autocomplete=userRepository.findByFullNameStartingWithAndRoleId_IdAndCompanyId_Id(prefixName,roleId.getId(),companyId);//CustomersByNameStartingWithAndRoleIdEqualAndCompanyIdEqual(prefixName,roleId.getId(),companyId);
        Map<String,String> toReturn=new HashMap<>();
        autocomplete.forEach(customer->toReturn.put(customer.getId(),customer.getFullName()));
        return toReturn;
    }

    public User signUp(String fullName, String companyName, String email, String password) throws Exception {
        try {
            User user = new User();
            user.setFullName(fullName);
            //password validations?
            if (!password.contains("")) {
                throw new NotValidException("password");
            }
            user.setPassword(password);
            //email validations?
            if (!email.contains("@")) {
                throw new NotValidException("email");
            }
            if (userRepository.existsByAddress_Email(email)) {
                throw new ObjectAlreadyExistsException("email");
            }
            Address address = new Address();
            user.setAddress(address);
            user.getAddress().setEmail(email);
            user.setRoleId(roleRepository.findByName(AvailableRoles.ADMIN));
            AuditDate auditDate = new AuditDate();
            auditDate.setCreateDate(LocalDate.now());
            auditDate.setUpdateDate(LocalDate.now());
            user.setAuditDate(auditDate);
            if (companyRepository.existsByName(companyName)) {
                throw new ObjectAlreadyExistsException("company");
            }
            Company company = new Company();
            company.setName(companyName);
            companyRepository.save(company);
            AuditDate auditDate1 = new AuditDate();
            auditDate1.setCreateDate(LocalDate.now());
            auditDate1.setUpdateDate(LocalDate.now());
            company.setAuditDate(auditDate1);
            user.setCompanyId(company);
            userRepository.insert(user);
            return user;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public Optional<User> getById(String userId) {
        Optional<User> y=userRepository.findById(userId);
        return y;
    }

    public void add(User user) {
        userRepository.save(user);
    }

    public User editById(User user, String userId) {
        userRepository.deleteById(userId);
        userRepository.save(user);
        return user;
    }

    public void deletebyId(String userId) {
        userRepository.deleteById(userId);
    }

    public boolean isEmailExists(String email){
        //TODO: replace mockData with real call - check if email exists in the db
        // userRepository.isUserExists (by email)
        return true;
    }

    public User getUserByEmailAndPassword(String userEmail, String userPassword) {
        if (Boolean.FALSE.equals(isEmailExists(userEmail))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found. Please sign up" );
        }
        //User user = User.builder().fullName("jsd").build();
        //TODO: replace mockData with real call - userRepository.getUserByEmailAnsPassword
        User user = new User("1","Chani","111"
                ,new Address("3527453746","Rabi Akiva","aaa@nnn.com")
                ,new Role("666rt9",AvailableRoles.CUSTOMER,"customer",new AuditDate(LocalDate.now(), LocalDate.now()))
                ,new Company("234","Osem","fgh",new AuditDate(LocalDate.now(), LocalDate.now()))
                ,new AuditDate(LocalDate.now(), LocalDate.now()));
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials" );
        }

        return user;
    }

}
