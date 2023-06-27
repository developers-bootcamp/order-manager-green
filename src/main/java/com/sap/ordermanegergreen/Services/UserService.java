package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
