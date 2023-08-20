package com.sap.ordermanagergreen.resolver;

import com.sap.ordermanagergreen.dto.UserDto;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.ArrayList;
import java.util.List;

public class UserDtoResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == UserDto.class;
    }

    @Override
    public UserDto resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
//        List<UserDto> users=new ArrayList<>();
//        users.add(UserDto.builder().id("5")
//                .fullName("unit testing1 is important").addressName("gilo").email("erty@rtt").telephone("0556677889").roleId("1").build());
//        users.add(UserDto.builder().id("6")
//                .fullName("unit testing2 is important").addressName("mila").email("popo@rtt").telephone("0556697559").roleId("2").build());
//        users.add(UserDto.builder().id("7")
//                .fullName("unit testing3 is important").addressName("michina").email("shlomo@rtt").telephone("0556964229").roleId("3").build());
//        return users;
        return UserDto.builder().id("5")
                .fullName("unit testing1 is important").address("gilo").email("erty@rtt").telephone("0556677889").roleId("1").build();

    }
}
