package de.ait.tp.dto.user;

import de.ait.tp.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "User",description = "User data")
public class UserDto {

    @Schema(description = "User ID",example = "1")
    private Long id;
    @Schema(description = "User firstname", example = "Kristina")

    private String firstName;
    @Schema(description = "User lastname", example = "Romanova")
    private String lastName;
    @Schema(description = "User email", example = "kristina@mail.ru")
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;
    @Schema(description = "Role of the user",example = "USER")
    @NotNull
    @NotBlank
    @NotEmpty
    private String role;
    public static UserDto from(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }
    public static List<UserDto> from(Collection<User> users){
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }
}

