package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.DTO.TokenDTO;
import com.sap.ordermanegergreen.Exception.NoPremissionException;
import com.sap.ordermanegergreen.Exception.ObjectExistException;
import com.sap.ordermanegergreen.Models.*;
import com.sap.ordermanegergreen.Repositories.ICompanyRepository;
import com.sap.ordermanegergreen.Repositories.IRoleRepository;
import com.sap.ordermanegergreen.Repositories.IUserRepository;
import com.sap.ordermanegergreen.Utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class UserService {

    IUserRepository userRepository;
    IRoleRepository roleRepository;
    ICompanyRepository companyRepository;

    @Autowired
    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, ICompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
    }


    public void add(String token, User user) {
        if (userRepository.existsByFullName(user.getFullName())) {
            throw new ObjectExistException("user name already exist");
        }
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(new Role()).getName() != AvailableRoles.CUSTOMER) {
            user.setAuditData(new AuditData(new Date(), new Date()));
            userRepository.save(user);
        }
    }


    public void deleteById(String token, String userId) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(new Role()).getName() == AvailableRoles.CUSTOMER ||!( companyRepository.findById(tokenDTO.getCompanyId()).orElse(new Company()).getId().equals(userRepository.findById(userId).orElse(new User()).getCompanyId().getId()))) {
            throw new NoPremissionException("You don't have permission to delete the user");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
            }
            userRepository.deleteById(userId);
        }

    public void editById(String token, User user) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        if (roleRepository.findById(tokenDTO.getRoleId()).orElse(new Role()).getName() == AvailableRoles.CUSTOMER || !(companyRepository.findById(tokenDTO.getCompanyId()).orElse(new Company()).getId().equals(userRepository.findById(user.getId()).orElse(new User()).getCompanyId().getId())) ) {
            throw new NoPremissionException("You don't have permission to delete the user");
        }
        if (userRepository.findById(user.getId()).isEmpty()) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
            userRepository.save(user);
        }

    public User isEmailExists(String email) {
        return userRepository.findByAddress_Email(email);
    }

    public User getUserByEmailAndPassword(String userEmail, String userPassword) {
        User user = isEmailExists(userEmail);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found. Please sign up");
        } else {
            if (!user.getPassword().equals(userPassword))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            return user;

        }



    }
}
