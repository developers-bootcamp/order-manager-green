package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    IUserRepository UserRepository;
    @Autowired
    public UserService(IUserRepository userRepository) {
        this.UserRepository = userRepository;
    }
    @Override
    public User getUser(String id) {
        Object p = UserRepository.findById(id);
        return (User)p;
    }

}
