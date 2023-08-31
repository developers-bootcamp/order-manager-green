package com.sap.ordermanagergreen.mapper;

import com.sap.ordermanagergreen.dto.UserDTO;
import com.sap.ordermanagergreen.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source="address.telephone",target = "telephone")
    @Mapping(source="address.addressName",target = "addressName")
    @Mapping(source="address.email",target = "email")
    @Mapping(source = "role.id",target = "roleId")
    UserDTO UserToUserDTO(User user);
}
