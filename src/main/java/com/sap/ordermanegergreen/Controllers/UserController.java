package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@Controller
@RestController("/User")
public class UserController {
   private IUserService UserService;
   @Autowired
   public UserController(IUserService UserService){
       this.UserService=UserService;
   }

    @GetMapping("/check")
    //@ResponseBody
    public String index( String id) {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/get")
    //@ResponseBody
    public User GettingUser(String id) {
        return UserService.getUser(id);
    }

}
