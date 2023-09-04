package com.sap.ordermanagergreen.resolver;

import com.sap.ordermanagergreen.model.DiscountType;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.model.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ProductResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Product.class;
    }

    @Override
    public Product resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return Product.builder().id("11").name("testingProduct").price(14.2).discount(1).discountType(DiscountType.FIXED_AMOUNT).build();
    }
}
