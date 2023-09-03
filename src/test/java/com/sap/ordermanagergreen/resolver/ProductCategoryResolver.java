package com.sap.ordermanagergreen.resolver.resolver;

import com.sap.ordermanagergreen.model.AuditData;
import com.sap.ordermanagergreen.model.Company;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.time.LocalDateTime;

public class ProductCategoryResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()== ProductCategory.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return ProductCategory.builder().id("1")
                .name("testName")
                .description("testDesc")
                .company(Company.builder().id("1").build())
                .auditData(AuditData.builder().createDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build())
                .build();
    }
}
