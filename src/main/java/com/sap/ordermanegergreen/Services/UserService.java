package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Repositorys.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService implements  IUserService{

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Example usage
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser() {
        return null;
    }
}
