package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.User;

import java.util.List;

public interface ICustomerService {
    List<User> getAll();
    User getById(String id);
    void add(User u);
    User put(User user, String id);
    void deletebyId(String id);
}
