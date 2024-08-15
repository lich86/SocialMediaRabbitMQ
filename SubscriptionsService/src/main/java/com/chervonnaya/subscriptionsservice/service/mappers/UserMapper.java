package com.chervonnaya.subscriptionsservice.service.mappers;


import com.chervonnaya.dto.UserDTO;
import com.chervonnaya.subscriptionsservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
    User map(UserDTO dto);
    UserDTO map(User user);
}
