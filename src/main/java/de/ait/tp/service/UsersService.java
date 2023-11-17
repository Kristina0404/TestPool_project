package de.ait.tp.service;

import de.ait.tp.dto.NewUserDto;
import de.ait.tp.dto.Pagination;
import de.ait.tp.dto.UpdateUserDto;
import de.ait.tp.dto.UserDto;

import java.util.List;

public interface UsersService {
    UserDto register(NewUserDto newUser);
    UserDto getUserById(Long currentUserId);
    UserDto confirm(String confirmCode);
   // List<UserDto> getAllUsers();
    List<UserDto> getAllUsers();
    UserDto updateUser(Long userId, UpdateUserDto updateUser);
    UserDto deleteUser(Long userId);
}


