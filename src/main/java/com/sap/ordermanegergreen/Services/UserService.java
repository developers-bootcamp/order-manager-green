package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;

import java.util.List;

@Service
public class UserService implements IUserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(String id) {
        return userRepository.findById(id).get();
    }

    public void add(User u) {
        userRepository.save(u);
    }

    public User put(User user, String id) {
        userRepository.deleteById(id);
        userRepository.save(user);
        return user;
    }

    public void deletebyId(String id) {
        userRepository.deleteById(id);
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
                ,new Roles("2222", AvailableRoles.ADMIN,"roleDesc",new AuditData())
                ,new Company("3333","comp", "ILS", new AuditData())
                ,new AuditData(new Date(), new Date()));
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials" );
        }

        return user;
    }

}
