package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.AvailableRoles;
import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Repositories.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService  {
    ICustomerRepository customerRepository;

    @Autowired
    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<User> getAll() {
        return customerRepository.findAll().stream().filter(u->u.getRoleId().equals(AvailableRoles.CUSTOMER)).toList();
    }

    public User getById(String id) {
        return customerRepository.findById(id).get();
    }

    public void add(User u) {
        customerRepository.save(u);
    }

    public User put(User user, String id) {
        customerRepository.deleteById(id);
        customerRepository.save(user);
        return user;
    }

    public void deletebyId(String id) {
        customerRepository.deleteById(id);
    }

}