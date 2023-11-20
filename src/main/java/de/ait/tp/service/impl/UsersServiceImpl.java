package de.ait.tp.service.impl;

import de.ait.tp.dto.*;
import de.ait.tp.exceptions.RestException;
import de.ait.tp.mail.MailTemplatesUtil;
import de.ait.tp.mail.TestPoolMailSender;
import de.ait.tp.models.ConfirmationCode;
import de.ait.tp.models.User;
import de.ait.tp.repositories.ConfirmationCodesRepository;
import de.ait.tp.repositories.UsersRepository;
import de.ait.tp.service.UsersService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static de.ait.tp.dto.UserDto.from;


@RequiredArgsConstructor
@Service

public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final ConfirmationCodesRepository confirmationCodesRepository;
    private final PasswordEncoder passwordEncoder;
    private final TestPoolMailSender mailSender;
    private final MailTemplatesUtil mailTemplatesUtil;


    @Value("${base.url}")
    private String baseUrl;

    @Transactional
    @Override
    public UserDto register(NewUserDto newUser) {

        checkIfExistsByEmail(newUser);
        User user = saveNewUser(newUser);

        String codeValue = UUID.randomUUID().toString();

        saveConfirmCode(codeValue, user);

        String link = createLinkForConfirmation(codeValue);
        String html = mailTemplatesUtil.createConfirmationMail(user, link);

        mailSender.send(user.getEmail(), "Registration", html);
        return UserDto.from(user);
    }

    private String createLinkForConfirmation(String codeValue) {
        return baseUrl + "/confirm.html?id=" + codeValue;

    }

    private void saveConfirmCode(String codeValue, User user) {
        ConfirmationCode code = ConfirmationCode.builder()
                .code(codeValue)
                .user(user)
                .expiredDateTime(LocalDateTime.now().plusMinutes(10))
                .build();
        confirmationCodesRepository.save(code);
    }

    private User saveNewUser(NewUserDto newUser) {
        User user = User.builder()
                .email(newUser.getEmail())
                .hashPassword(passwordEncoder.encode(newUser.getPassword()))
                .role(User.Role.USER)
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .state(User.State.NOT_CONFIRM)
                .build();
        usersRepository.save(user);
        return user;
    }

    private void checkIfExistsByEmail(NewUserDto newUser) {
        if (usersRepository.existsByEmail(newUser.getEmail())) {
            throw new RestException(HttpStatus.CONFLICT,
                    "User with email <" + newUser.getEmail() + " already exists");
        }
    }

    @Override
    public UserDto getUserById(Long currentUserId) {
        return from(usersRepository.findById(currentUserId).orElseThrow());
    }

    @Override
    @Transactional
    public UserDto confirm(String confirmCode) {
        ConfirmationCode code = confirmationCodesRepository
                .findByCodeAndExpiredDateTimeAfter(confirmCode, LocalDateTime.now())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Code not found or is expired"));

        User user = usersRepository
                .findFirstByCodesContains(code)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User by that code not found"));

        user.setState(User.State.CONFIRMED);

        usersRepository.save(user);

        return UserDto.from(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        return from(usersRepository.findAll());
    }

    @Override
    public UserDto updateUser(Long userId, UpdateUserDto updateUser) {

        User user = usersRepository.findUserById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "User with id < " + userId + "> not found >"));
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        usersRepository.save(user);
        return UserDto.from(user);
    }

    @Override
    public UserDto deleteUser(Long userId) {
        User user = usersRepository.findUserById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Test with id <" + userId + "> not found >"));
        usersRepository.delete(user);
        return UserDto.from(user);
    }
}


