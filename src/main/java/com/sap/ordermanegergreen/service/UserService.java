package com.sap.ordermanegergreen.service;

import com.sap.ordermanegergreen.model.*;
import org.springframework.stereotype.Service;
import com.sap.ordermanegergreen.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.sap.ordermanegergreen.exception.NotValidException;
import com.sap.ordermanegergreen.exception.ObjectAlreadyExistsException;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private CompanyService companyService;
    private RolesService rolesService;
    IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository, CompanyService companyService, RolesService rolesService) {
        this.userRepository = userRepository;
        this.companyService = companyService;
        this.rolesService = rolesService;
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
            user.setRoleId(rolesService.getByName(AvailableRoles.ADMIN));
            AuditDate auditDate = new AuditDate();
            auditDate.setCreateDate(new Date());
            auditDate.setUpdateDate(new Date());
            user.setAuditDate(auditDate);
            if (companyService.existsByName(companyName)) {
                throw new ObjectAlreadyExistsException("company");
            }
            Company company = new Company();
            company.setName(companyName);
            companyService.add(company);
            AuditDate auditDate1 = new AuditDate();
            auditDate1.setCreateDate(new Date());
            auditDate1.setUpdateDate(new Date());
            company.setAuditDate(auditDate1);
            user.setCompanyId(company);
            userRepository.insert(user);
            return user;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(String userId) {
        return userRepository.findById(userId).get();
    }

    public void add(User user) {
        userRepository.save(user);
    }

    public User put(String userId, User user) {
        userRepository.deleteById(userId);
        userRepository.save(user);
        return user;
    }

    public void deleteById(String userId) {
        userRepository.deleteById(userId);
    }

}