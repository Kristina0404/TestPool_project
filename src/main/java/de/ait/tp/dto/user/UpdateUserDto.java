package de.ait.tp.dto.user;

import de.ait.tp.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Schema(name = "UpdateUser", description = "Name,types or level update ")

public class UpdateUserDto {
    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User firstname", example = "Kristi")

    private String firstName;

    @Schema(description = "User lastname", example = "Romanova")

    private String lastName;

    public static UpdateUserDto from(User user){
        return UpdateUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
    public static List<UserDto> from(Collection<User> users){
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }

}
