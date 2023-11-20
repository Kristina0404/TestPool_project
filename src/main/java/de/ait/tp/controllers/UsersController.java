package de.ait.tp.controllers;

import de.ait.tp.controllers.api.UsersApi;
import de.ait.tp.dto.*;
import de.ait.tp.security.details.AuthenticatedUser;
import de.ait.tp.service.TestResultService;
import de.ait.tp.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController

public class UsersController implements UsersApi {
    private final UsersService usersService;
    private final TestResultService testResultService;

    @Override
    public UserDto register(NewUserDto newUser) {
        return usersService.register(newUser);
    }

    @Override
    public UserDto getConfirmation(String confirmCode) {
        return usersService.confirm(confirmCode);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Override
    public UserDto getProfile(AuthenticatedUser user) {
        Long currentUserId = user.getId();
        return usersService.getUserById(currentUserId);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('USER')")
    public UserDto updateUser(Long userId, UpdateUserDto updateUser) {
        return usersService.updateUser(userId, updateUser);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @Override
    public UserDto deleteUser(Long userId) {
        return usersService.deleteUser(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return usersService.getAllUsers();
    }

}
