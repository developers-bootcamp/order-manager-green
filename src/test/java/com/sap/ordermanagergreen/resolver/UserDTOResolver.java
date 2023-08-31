package com.sap.ordermanagergreen.resolver;

import com.sap.ordermanagergreen.dto.UserDTO;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class UserDTOResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == UserDTO.class;
    }

    @Override
    public UserDTO resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return UserDTO.builder().id("5")
                .fullName("unit testing1 is important").addressName("gilo").email("erty@rtt").telephone("0556677889").roleId("1").build();

    }
}
