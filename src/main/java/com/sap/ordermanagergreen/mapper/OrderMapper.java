package com.sap.ordermanagergreen.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ordermanagergreen.dto.OrderDTO;
import com.sap.ordermanagergreen.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;





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
