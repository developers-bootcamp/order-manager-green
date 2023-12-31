package com.sap.ordermanagergreen.resolver;

import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.model.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class TokenResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == TokenDTO.class;

    }

    @Override
    public TokenDTO resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
      return TokenDTO.builder().userId("64bfe34d3e372e02ff12edb5").companyId("64e5bc404c6d3a5bee0f9ef5").roleId("77f9").build();
    }
}
