package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.*;
import com.sap.ordermanegergreen.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;

@Service
public class UserService implements IUserService{

    UserRepository UserRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.UserRepository = userRepository;
    }
    @Override
    public User getUser(String id) {
        Object p = UserRepository.findById(id);
        return (User)p;
    }

    public boolean isEmailExists(String email){
        //TODO: replace mockData with real call - check if email exists in the db
        // userRepository.isUserExists (by email)
        return true;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        if (Boolean.FALSE.equals(isEmailExists(email))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found. Please sign up" );
        }
        //TODO: replace mockData with real call - userRepository.getUserByEmailAnsPassword
        User user = new User("1","Chani","111"
                ,new Address("3527453746","Rabi Akiva","aaa@nnn.com")
                ,new Roles("4444", AvailableRoles.ADMIN,"roleDesc",new AuditData())
                ,new Company("3333","comp", "ILS", new AuditData())
                ,new AuditData(new Date(), new Date()));
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials" );
        }

        return user;
    }

}
