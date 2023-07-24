package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.dto.UserDto;
import com.sap.ordermanagergreen.mapper.UserMapper;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.repository.IUserRepository;
import com.sap.ordermanagergreen.resolver.UserDtoResolver;
import com.sap.ordermanagergreen.resolver.UserResolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith({MockitoExtension.class,UserResolver.class, UserDtoResolver.class})
public class UserServiceTest {

        // Mock the UserRepository and UserMapper dependencies
        @Mock
        private IUserRepository userRepository;

        @InjectMocks
        private UserService userService;

        @Mock
        private UserMapper userMapper;
        @Test
        public void getAll_ReturnsListOfUserDto(List<User> demoUser,List<UserDto> demoUserDto) {
            // Mocking the userRepository.findByCompanyIdOrderByRoleIdAscAuditData_UpdateDateDesc() method
            String companyId = "your_company_id";
            int page = 0;
            int pageSize = 10;
            PageRequest pageRequest = PageRequest.of(page, pageSize);

            // Creating a Page<User> using the mockUsers list
            Page<User> mockUserPage = new PageImpl<>(demoUser, pageRequest, demoUser.size());

            Mockito.when(userRepository.findByCompanyIdOrderByRoleIdAscAuditData_UpdateDateDesc(eq(companyId), any(PageRequest.class)))
                    .thenReturn(mockUserPage);

            // Mocking the userMapper.UserToUserDTO() method
            // Assuming you have a UserMapper class that maps User objects to UserDto objects
            //Mockito.when(userMapper.UserToUserDTO(Mockito.any(User.class))).thenReturn(new UserDto(/* UserDto parameters */));

            // Calling the getAll method of the userService
            List<UserDto> result = userService.getAll(companyId, page, pageSize);

            // Assertions
            Assertions.assertNotNull(result);
            Assertions.assertEquals(demoUserDto.size(), result.size());
            Assertions.assertEquals(demoUserDto, result);
            // Add more assertions here based on your actual UserDto mapping
        }
    }
