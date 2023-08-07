package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.dto.UserDto;
import com.sap.ordermanagergreen.mapper.UserMapper;
import com.sap.ordermanagergreen.model.Address;
import com.sap.ordermanagergreen.model.AuditData;
import com.sap.ordermanagergreen.model.Role;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.repository.IUserRepository;
import com.sap.ordermanagergreen.resolver.UserDtoResolver;
import com.sap.ordermanagergreen.resolver.UserResolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class,UserResolver.class, UserDtoResolver.class})
@SpringBootTest
public class UserServiceTest {

        // Mock the UserRepository and UserMapper dependencies
        @Mock
        private IUserRepository userRepository;

        @InjectMocks
        private UserService userService;

        @Mock
        private UserMapper userMapper;

    @BeforeEach
        public void setUp() {
            MockitoAnnotations.initMocks(this);
        }
        @Test
        public void getAll_ReturnsListOfUserDto(User demoUser,UserDto demoUserDto) {
            // Mocking the userRepository.findByCompanyIdOrderByRoleIdAscAuditData_UpdateDateDesc() method
            String companyId = "234";
            int page = 0;
            int pageSize = 3;
            PageRequest pageRequest = PageRequest.of(page, pageSize);

        List<User> testUsers=getListOfUser();
        List<UserDto> testUsersDto=getListOfUserDto();
        doReturn(new PageImpl<>(testUsers, pageRequest, testUsers.size()))
                    .when(userRepository)
                    .findByCompany_IdOrderByRoleAscAuditData_UpdateDateDesc(eq(companyId), any(PageRequest.class));
            // Mocking the userMapper.UserToUserDTO() method
            // Assuming you have a UserMapper class that maps User objects to UserDto objects
            for(int i = 0; i < testUsers.size();i++) {
                when(userMapper.UserToUserDTO(testUsers.get(i))).thenReturn(testUsersDto.get(i));;
            }
            //testUsers.forEach((e,index)=>when(userMapper.UserToUserDTO(Mockito.any(User.class))).thenReturn(testUsersDto[index]));;

            // Calling the getAll method of the userService
            List<UserDto> result = userService.get(companyId, page, pageSize);

            // Assertions
            Assertions.assertNotNull(result);
            Assertions.assertEquals(testUsersDto.size(), result.size());
            Assertions.assertEquals(testUsersDto, result);
            // Add more assertions here based on your actual UserDto mapping
        }
//        import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import java.util.ArrayList;
//import java.util.List;
//import static org.mockito.Mockito.*;
//
//    public class UserServiceTest {
//
//        @Mock
//        private UserRepository userRepository;
//
//        @Mock
//        private UserMapper userMapper;
//
//        @InjectMocks
//        private UserService userService;
//
//        @BeforeEach
//        public void setUp() {
//            MockitoAnnotations.initMocks(this);
//        }
//
//        @Test
//        public void testGetUsersByCompanyId() {
//            String companyId = "123";
//            int page = 0;
//            int pageSize = 10;
//
//            // Create mock users
//            List<User> mockUserList = new ArrayList<>();
//            mockUserList.add(new User("user1", "Role1", /* other properties */));
//            mockUserList.add(new User("user2", "Role2", /* other properties */));
//            // Add more mock users as needed
//
//            PageRequest pageRequest = PageRequest.of(page, pageSize);
//            Page<User> mockUserPage = new PageImpl<>(mockUserList, pageRequest, mockUserList.size());
//
//            when(userRepository.findByCompany_IdOrderByRoleAscAuditData_UpdateDateDesc(eq(companyId), any(PageRequest.class)))
//                    .thenReturn(mockUserPage);
//
//            // Mock user-to-DTO mapping
//            when(userMapper.UserToUserDTO(any(User.class))).thenAnswer(invocation -> {
//                User user = invocation.getArgument(0);
//                UserDto userDto = new UserDto(user.getName(), user.getRole(), /* other mapped properties */);
//                return userDto;
//            });
//
//            List<UserDto> result = userService.get(companyId, page, pageSize);
//
//            // Assertions for the 'result' list
//            assertEquals(mockUserList.size(), result.size());
//            // Add more assertions for UserDto properties, etc.
//        }
//    }

    public List<User> getListOfUser(){
        List<User> users=new ArrayList<>();
        users.add(User.builder().id("5")
                .fullName("unit testing1 is important").password("1111111").address(Address.builder().addressName("gilo").email("erty@rtt").telephone("0556677889").build()).
                role(Role.builder().id("1").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build());
        users.add(User.builder().id("6")
                .fullName("unit testing2 is important").password("2222222").address(Address.builder().addressName("mila").email("popo@rtt").telephone("0556697559").build()).
                role(Role.builder().id("2").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build());
        users.add(User.builder().id("7")
                .fullName("unit testing3 is important").password("3333333").address(Address.builder().addressName("michina").email("shlomo@rtt").telephone("0556964229").build()).
                role(Role.builder().id("3").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build());
        return users;
    }
    public List<UserDto> getListOfUserDto(){
        List<UserDto> usersDto=new ArrayList<>();
        usersDto.add(UserDto.builder().id("5")
                .fullName("unit testing1 is important").addressName("gilo").email("erty@rtt").telephone("0556677889").roleId("1").build());
        usersDto.add(UserDto.builder().id("6")
                .fullName("unit testing2 is important").addressName("mila").email("popo@rtt").telephone("0556697559").roleId("2").build());
        usersDto.add(UserDto.builder().id("7")
                .fullName("unit testing3 is important").addressName("michina").email("shlomo@rtt").telephone("0556964229").roleId("3").build());
        return usersDto;
    }

    }




