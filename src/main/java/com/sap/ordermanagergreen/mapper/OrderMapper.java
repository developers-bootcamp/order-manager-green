package com.sap.ordermanagergreen.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ordermanagergreen.dto.OrderDTO;
import com.sap.ordermanagergreen.dto.UserDto;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.model.User;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// ...

import java.time.YearMonth;


@Mapper(componentModel = "spring")
public interface OrderMapper{
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    ObjectMapper mapper = new ObjectMapper();

    @Mapping(source="customer.id",target = "customerId")
    @Mapping(source="totalAmount",target = "paymentAmount")

    OrderDTO toDTO(Order order) ;
    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "paymentAmount", target = "totalAmount")
    Order fromDTO(OrderDTO orderDTO);

}
