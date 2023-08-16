package com.sap.ordermanagergreen.mapper;

import com.sap.ordermanagergreen.dto.OrderDTO;
import com.sap.ordermanagergreen.dto.UserDto;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface OrderMapper{
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source="customer.id",target = "customerId")
    @Mapping(source="totalAmount",target = "paymentAmount")
    OrderDTO toDTO(Order order);

}
