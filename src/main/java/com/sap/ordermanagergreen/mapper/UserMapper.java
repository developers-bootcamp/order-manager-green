package com.sap.ordermanagergreen.mapper;

import com.sap.ordermanagergreen.dto.ProductDTO;
import com.sap.ordermanagergreen.dto.UserDTO;
import com.sap.ordermanagergreen.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import com.sap.ordermanagergreen.model.User;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "address.telephone", target = "telephone")
    @Mapping(source = "address.address", target = "addressName")
    @Mapping(source = "address.email", target = "email")
    @Mapping(source = "role.name", target = "roleName")
    UserDTO UserToUserDTO(User user);

    @Mapping(source = "telephone", target = "address.telephone")
    @Mapping(source = "addressName", target = "address.address")
    @Mapping(source = "email", target = "address.email")
    @Mapping(source = "roleName", target = "role.name")
    User UserDTOToUser(UserDTO user);
    List<User> dtoToUser(List<UserDTO> list);
    List<UserDTO> userToDto(List<User> list);

}
