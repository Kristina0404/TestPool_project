package de.ait.tp.dto.user;

import de.ait.tp.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Array of all users", example = "[{\"id\": 1," +
        " \"firstName\": \"Kristina\", \"lastName\": \"Romanova\", \"email\": \"kristina@mail.ru\"," +
        " \"role\": \"USER\"}]")
public class AllUsersDto {

    @Schema(description = "User ID")
    private Long id;
    @Schema(description = "User firstname")
    private String firstName;
    @Schema(description = "User lastname")
    private String lastName;
    @Schema(description = "User email")
    private String email;
    @Schema(description = "Role of the user")
    private String role;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }

    public static List<UserDto> from(Collection<User> users) {
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }
}


