package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.model.*;
import org.springframework.stereotype.Service;
import com.sap.ordermanagergreen.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.sap.ordermanagergreen.exception.NotValidException;
import com.sap.ordermanagergreen.exception.ObjectExistException;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private CompanyService companyService;
    private RoleService rolesService;
    IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository, CompanyService companyService, RoleService rolesService) {
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
                throw new ObjectExistException("email");
            }
            Address address = new Address();
            user.setAddress(address);
            user.getAddress().setEmail(email);
            user.setRoleId(rolesService.getByName(AvailableRoles.ADMIN));
            AuditData auditData = new AuditData();
            auditData.setCreateDate(new Date());
            auditData.setUpdateDate(new Date());
            user.setAuditData(auditData);
            if (companyService.existsByName(companyName)) {
                throw new ObjectExistException("company");
            }
            Company company = new Company();
            company.setName(companyName);
            companyService.add(company);
            AuditData auditData1 = new AuditData();
            auditData1.setCreateDate(new Date());
            auditData1.setUpdateDate(new Date());
            company.setAuditData(auditData1);
            user.setCompanyId(company);
            userRepository.insert(user);
            return user;
        } catch (Exception e) {
            throw new Exception();
        }
    }}