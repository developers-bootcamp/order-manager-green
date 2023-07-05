package com.sap.ordermanegergreen.service;

import com.sap.ordermanegergreen.model.*;
import com.sap.ordermanegergreen.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import java.util.List;

@Service
public class UserService  {

    IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
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
        //TODO: replace mockData with real call - userRepository.getUserByEmailAnsPassword
        User user = new User("1","Chani","111"
                ,new Address("3527453746","Rabi Akiva","aaa@nnn.com")
                ,"2132"
                ,"234"
                ,new AuditDate(new Date(), new Date()));
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials" );
        }

        return user;
    }

}