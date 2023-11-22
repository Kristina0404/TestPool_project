package de.ait.tp.service;

import de.ait.tp.dto.user.NewUserDto;
import de.ait.tp.dto.user.UpdateUserDto;
import de.ait.tp.dto.user.UserDto;

import java.util.List;

public interface UsersService {
    UserDto register(NewUserDto newUser);

    UserDto getUserById(Long currentUserId);

    UserDto confirm(String confirmCode);

    List<UserDto> getAllUsers();

    UpdateUserDto updateUser(Long userId, UpdateUserDto updateUser);

    UserDto deleteUser(Long userId);
}


