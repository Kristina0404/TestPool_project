package de.ait.tp.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Schema(name = "NewUser", description = "Registration details")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    @Email
    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "User email", example = "testpool.ait@gmail.com")
    private String email;
    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    @Schema(description = "User password", example = "Qwerty007!")
    private String password;
    @Schema(description = "User first name", example = "Kristina")
    private String firstName;
    @Schema(description = "User last name", example = "Romanova")
    private String lastName;
}
