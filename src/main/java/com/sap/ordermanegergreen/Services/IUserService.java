package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.User;

import java.util.Optional;

public interface IUserService {
    User getUser(String id);

    boolean isEmailExists(String email);
    User getUserByEmailAndPassword(String email, String password);
}
