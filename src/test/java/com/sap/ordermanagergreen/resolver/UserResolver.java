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
    public List<User> resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        List<User> users=new ArrayList<>();
        users.add(User.builder().id("5")
                .fullName("unit testing1 is important").password("1111111").address(Address.builder().addressName("gilo").email("erty@rtt").telephone("0556677889").build()).
                roleId(Role.builder().id("1").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build());
        users.add(User.builder().id("6")
                .fullName("unit testing2 is important").password("2222222").address(Address.builder().addressName("mila").email("popo@rtt").telephone("0556697559").build()).
                roleId(Role.builder().id("2").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build());
        users.add(User.builder().id("7")
                .fullName("unit testing3 is important").password("3333333").address(Address.builder().addressName("michina").email("shlomo@rtt").telephone("0556964229").build()).
                roleId(Role.builder().id("3").build())
                .auditData(AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build())
                .build());
        return users;
    }
}
