package com.sap.ordermanegergreen.controller;

import com.sap.ordermanegergreen.dto.UserDto;
import com.sap.ordermanegergreen.dto.TokenDTO;
import com.sap.ordermanegergreen.exception.NotValidException;
import com.sap.ordermanegergreen.exception.ObjectAlreadyExistsException;
import com.sap.ordermanegergreen.model.User;
import com.sap.ordermanegergreen.service.UserService;
import com.sap.ordermanegergreen.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private JwtToken jwtToken;
    @Autowired
    public UserController(UserService userService, JwtToken jwtToken) {
        this.userService = userService;
        this.jwtToken = jwtToken;
    }

//    @GetMapping
//    @RequestMapping("/getAll")
//    public List<User> getAll() {
//        return userService.getAll();
//    }
    //    Get Users
//    Parameter 	Page Number
//    Action 	Select users with pagination, associated to company
//    Return value	List of users order by role and update date
    @GetMapping
    @RequestMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAll(@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "5") Integer pageSize,@RequestHeader("athorade") String token) {
        TokenDTO tokenDTO=JwtToken.decodeToken(token);
        List<UserDto> l = null;
        try {
            l = userService.getAll(tokenDTO.getCompanyId(),page,pageSize);
        }
        catch (Exception e) {
            System.out.println("💕💕 error "+e);
        }
        return ResponseEntity.ok(l);
    }
    @GetMapping
    @RequestMapping("/getAllByPrefix/{prefixName}")
    public ResponseEntity<Map<String,String>> getAllByPrefix(@PathVariable("prefixName") String prefixName, @RequestHeader("athorade") String token) {
        System.out.println(token);
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        Map<String,String> l = null;
        Map<String, String> errorMap = new HashMap<>();
        try {
            l = userService.getAllByPrefix(prefixName,tokenDTO.getCompanyId());
        } catch (IllegalArgumentException e) {
            errorMap.put("IllegalArgumentException","invalid prefixName");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        } catch (Exception e) {
            errorMap.put("Exception",e.getMessage());
            return ResponseEntity.internalServerError().body(errorMap);
        }
        return ResponseEntity.ok(l);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<Optional<User> > getById(@PathVariable("userId") String userId) {
        Optional<User> t=userService.getById(userId);
       // if(t.())
            //return ResponseEntity.ofNullable(t);
        return ResponseEntity.ok(t);
    }

    @PostMapping
    @RequestMapping("/logIn/{email}/{password}")
    public ResponseEntity<String> logIn(@PathVariable("email") String email,
                                        @PathVariable("password") String password) {
        try {
            User user = userService.getUserByEmailAndPassword(email,password);
            String token = jwtToken.generateToken(user);
            return ResponseEntity.ok(token);
        } catch(ResponseStatusException ex ){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        } catch(Exception ex ){
            return new ResponseEntity<>("Unexpected error, " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    @RequestMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestParam("fullName") String fullName, @RequestParam("companyName") String companyName, @RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            User user = userService.signUp(fullName, companyName, email, password);
            return ResponseEntity.ok(user.getFullName());
        } catch (ObjectAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotValidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error Please try again later", HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping
    public void add(@RequestBody User user) {
        userService.add(user);
    }

    @PutMapping
    public User editById(@RequestBody User user, @PathVariable("userId") String userId) {
        return userService.editById(user, userId);
    }

    @DeleteMapping
    @RequestMapping(("/{userId}"))
    public void deleteById(@PathVariable String userId) {
        userService.deletebyId(userId);
    }
}
