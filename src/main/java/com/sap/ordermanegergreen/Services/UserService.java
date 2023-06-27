package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
