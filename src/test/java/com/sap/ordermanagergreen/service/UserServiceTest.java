package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.dto.UserDto;
import com.sap.ordermanagergreen.mapper.UserMapper;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.repository.IRoleRepository;
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

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

        @Mock
        private IRoleRepository roleRepository;

        @Test
        public void getAll_ReturnsListOfUserDto(User demoUser,UserDto demoUserDto) {
            // Mocking the userRepository.findByCompanyIdOrderByRoleIdAscAuditData_UpdateDateDesc() method
            String companyId = "234";
            int page = 0;
            int pageSize = 3;
            PageRequest pageRequest = PageRequest.of(page, pageSize);

        List<User> testUsers=getListOfUser();
        List<UserDto> testUsersDto=getListOfUserDto();
        Mockito.doReturn(new PageImpl<>(testUsers, pageRequest, testUsers.size()))
                    .when(userRepository)
                    .findByCompany_IdOrderByRoleAscAuditData_UpdateDateDesc(eq(companyId), any(PageRequest.class));
            // Mocking the userMapper.UserToUserDTO() method
            // Assuming you have a UserMapper class that maps User objects to UserDto objects
//            for(int i = 0; i < testUsers.size();i++) {
//                Mockito.when(userMapper.UserToUserDTO(testUsers.get(i))).thenReturn(testUsersDto.get(i));;
//            }

            when(userMapper.UserToUserDTO(any(User.class))).thenAnswer(invocation -> {
                User user = invocation.getArgument(0);
                UserDto userDto = new UserDto(user.getId(),user.getFullName(),user.getAddress().getEmail(),user.getAddress().getAddress(),user.getAddress().getTelephone(), user.getRole().getId());
                return userDto;
            });

            // Calling the getAll method of the userService
            List<UserDto> result = userService.get(companyId, page, pageSize);

            // Assertions
            Assertions.assertNotNull(result);
            Assertions.assertEquals(testUsersDto.size(), result.size());
            Assertions.assertEquals(testUsersDto, result);
        }
@Test
public void testGetNamesWithValidPrefixName() {
    // Mock data
    String prefixName = "unit";
    String companyId = "123";
    Role mockRole = Role.builder().id("1").build();
    List<User> mockUsers = getListOfUser();
    List<User> resultUsers=new ArrayList<>();
    resultUsers.add(mockUsers.get(0));

    // Mock roleRepository.getByName
    Mockito.when(roleRepository.getByName(AvailableRole.CUSTOMER)).thenReturn(mockRole);

    // Mock userRepository.findByFullNameStartingWithAndRole_IdAndCompany_Id
//    Mockito.when(userRepository.findByFullNameStartingWithAndRole_IdAndCompany_Id(
//            eq(prefixName), eq(mockRole.getId()), eq(companyId)))
//            .thenReturn(resultUser);
    Mockito.doReturn(resultUsers)
            .when(userRepository)
            .findByFullNameStartingWithAndRole_IdAndCompany_Id(eq(prefixName), eq(mockRole.getId()), eq(companyId));

    // Call the method under test
    Map<String, String> result = userService.getNames(prefixName, companyId);

    // Assertions
    Assertions.assertNotNull(result);
    Assertions.assertEquals(resultUsers.size(), result.size());
    Assertions.assertNotEquals(resultUsers.size(),mockUsers.size());
    for (User user : resultUsers) {
        Assertions.assertTrue(result.containsKey(user.getId()));
        Assertions.assertEquals(user.getFullName(), result.get(user.getId()));
    }
    // Verify mock interactions
    Mockito.verify(roleRepository, times(1)).getByName(AvailableRole.CUSTOMER);
    Mockito.verify(userRepository, times(1)).findByFullNameStartingWithAndRole_IdAndCompany_Id(
            eq(prefixName), eq(mockRole.getId()), eq(companyId));
}

    @Test
    public void testGetNamesWithNullPrefixName() {
        // Call the method under test with null prefixName
        String companyId = "123";
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userService.getNames(null, companyId)
        );

        // Verify the exception message
        Assertions.assertEquals("invalid prefixName", exception.getMessage());
    }


    public List<User> getListOfUser(){
        List<User> users=new ArrayList<>();
        users.add(User.builder().id("5")
                .fullName("unit testing1 is important").password("1111111").address(Address.builder().address("gilo").email("erty@rtt").telephone("0556677889").build()).
                role(Role.builder().id("1").build()).
                company(Company.builder().id("123").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build());
        users.add(User.builder().id("6")
                .fullName("unit testing2 is important").password("2222222").address(Address.builder().address("mila").email("popo@rtt").telephone("0556697559").build()).
                role(Role.builder().id("2").build()).
                company(Company.builder().id("123").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build());
        users.add(User.builder().id("7")
                .fullName("unit testing3 is important").password("3333333").address(Address.builder().address("michina").email("shlomo@rtt").telephone("0556964229").build()).
                role(Role.builder().id("3").build()).
                company(Company.builder().id("1234").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build());
        return users;
    }
    public List<UserDto> getListOfUserDto(){
        List<UserDto> usersDto=new ArrayList<>();
        usersDto.add(UserDto.builder().id("5")
                .fullName("unit testing1 is important").address("gilo").email("erty@rtt").telephone("0556677889").roleId("1").build());
        usersDto.add(UserDto.builder().id("6")
                .fullName("unit testing2 is important").address("mila").email("popo@rtt").telephone("0556697559").roleId("2").build());
        usersDto.add(UserDto.builder().id("7")
                .fullName("unit testing3 is important").address("michina").email("shlomo@rtt").telephone("0556964229").roleId("3").build());
        return usersDto;
    }

}




