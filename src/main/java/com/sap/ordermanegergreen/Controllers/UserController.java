package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Controller
@RestController("/user")
public class UserController {
    @Autowired
   private IUserService userService;

    @GetMapping("/")
    //@ResponseBody
    public String index( String id) {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/get")
    //@ResponseBody
    public User indexty(String id) {
        return userService.getUser();
    }

}
