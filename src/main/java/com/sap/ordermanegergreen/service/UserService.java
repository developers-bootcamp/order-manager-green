package com.sap.ordermanegergreen.service;

import com.sap.ordermanegergreen.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sap.ordermanegergreen.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private CompanyService companyService;
    IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity signUp(String fullName, String companyName, String email, String password) {
        try {
            User user = new User();
            user.setPassword(password);
            user.setFullName(fullName);
            // check if email exists in db
//            if (UserService.(email)) {
//                return new ResponseEntity<>("company is already exists", HttpStatus.BAD_REQUEST);//change to throw exception
//            }
            if (!email.contains("@")) {
                return new ResponseEntity<>("non correct email", HttpStatus.resolve(409));
            }
//        Address address = new Address();
//        user.setAddressId(address);
//        user.getAddressId().setEmail(email);
//        Roles roles = new Roles();
//        roles.setName(AvailableRoles.ADMIN);
//        user.setRoleId(roles.getId());
//        AuditData auditData = new AuditData();
//        user.setAuditData(auditData.getCreateDate());
            if (companyService.existsByName(companyName)) {
                return new ResponseEntity<>("company is already exists", HttpStatus.BAD_REQUEST);//change to throw exception
            }
//        Company company = new Company();
//        company.setName(companyName);
//        companyService.add(company);
//        user.setCompanyId(company.getId());
            userRepository.insert(user);
            return ResponseEntity.ok(user.getFullName());
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    public void deletebyId(String userId) {
        userRepository.deleteById(userId);
    }

    public boolean isEmailExists(String email) {
        //TODO: replace mockData with real call - check if email exists in the db
        // userRepository.isUserExists (by email)
        return true;
    }

    public User getUserByEmailAndPassword(String userEmail, String userPassword) {
        if (Boolean.FALSE.equals(isEmailExists(userEmail))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found. Please sign up");
        }
        //TODO: replace mockData with real call - userRepository.getUserByEmailAnsPassword
        User user = new User("1", "Chani", "111"
                , new Address("3527453746", "Rabi Akiva", "aaa@nnn.com")
                , "2132"
                , "234"
                , new AuditDate(new Date(), new Date()));
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return user;
    }

}
