package com.sap.ordermanagergreen.resolver;

import com.sap.ordermanagergreen.model.Address;
import com.sap.ordermanagergreen.model.AuditData;
import com.sap.ordermanagergreen.model.Role;
import com.sap.ordermanagergreen.model.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == User.class;
    }

    @Override
    public User resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
       return User.builder().id("5")
                .fullName("unit testing1 is important").password("1111111").address(Address.builder().address("gilo").email("erty@rtt").telephone("0556677889").build()).
                role(Role.builder().id("1").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build();
    }
}
