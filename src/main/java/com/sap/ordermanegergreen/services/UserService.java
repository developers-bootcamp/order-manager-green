package com.sap.ordermanegergreen.services;
import com.sap.ordermanegergreen.dto.TokenDTO;
import com.sap.ordermanegergreen.models.*;
import com.sap.ordermanegergreen.repositories.ICompanyRepository;
import com.sap.ordermanegergreen.repositories.IRoleRepository;
import com.sap.ordermanegergreen.repositories.IUserRepository;
import com.sap.ordermanegergreen.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Date;
import java.util.List;
@Service
public class UserService {
    IUserRepository userRepository;
    IRoleRepository roleRepository;
    ICompanyRepository companyRepository;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    public UserService(IUserRepository userRepository,IRoleRepository roleRepository,ICompanyRepository companyRepository)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }
    public User getById(String userId) {
        return userRepository.findById(userId).get();
    }
    public void add(String token,User user) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
        if(roleRepository.findById(tokenDTO.getRoleId()).orElse(new Role()).getName()!=AvailableRoles.CUSTOMER)
        {
            if (userRepository.existsByFullName(user.getFullName()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "user name already exist");
            userRepository.save(user);
        }
    }
    public User editById(User user, String userId) {
        userRepository.deleteById(userId);
        userRepository.save(user);
        return user;
    }
    public void deleteById( String token, String userId) {
        TokenDTO tokenDTO = jwtToken.decodeToken(token);
//        if(roleRepository.findById(tokenDTO.getRoleId()).orElse(new Roles()).getName()!=AvailableRoles.CUSTOMER||companyRepository.findById(tokenDTO.getCompanyId()).orElse(new Company()).getId().equals(userRepository.findById(userId).orElse(new User()).getCompanyId().getId()))
//        {
        if (userRepository.findById(userId)==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        userRepository.deleteById(userId);
//        }
    }
    public boolean isEmailExists(String email){
        //TODO: replace mockData with real call - check if email exists in the db
        // userRepository.isUserExists (by email)
        return true;
    }
    public User getUserByEmailAndPassword(String userEmail, String userPassword) {
        if (Boolean.FALSE.equals(isEmailExists(userEmail))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found. Please sign up" );
        }
        //TODO: replace mockData with real call - userRepository.getUserByEmailAnsPassword
        User user = new User("1", "Chani","111"
                ,new Address("3527453746","Rabi Akiva","aaa@nnn.com")
                ,new Role("4444", AvailableRoles.ADMIN,"roleDesc",new AuditData())
                ,new Company("1","comp", "ILS", new AuditData())
                ,new AuditData(new Date(), new Date()));
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials" );
        }
        return user;
    }
}