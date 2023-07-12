package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.AvailableRoles;
import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Repositories.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService  {
    ICustomerRepository customerRepository;

    @Autowired
    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

   public List<User> getAll() {
//List<User>users=new ArrayList<User>();
//User u=new User();
//u.setId("111");
//u.setFullName("malka");
//u.setPassword("1234");
//users.add(u);
// User u2=new User();
//u2.setId("895");
// u2.setFullName("ארי");
// u2.setPassword("1234");
// users.add(u2);
// User u3=new User();
// u3.setId("121");
// u3.setFullName("ששש");
// u3.setPassword("789");
//        users.add(u3);
//        return users;
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
