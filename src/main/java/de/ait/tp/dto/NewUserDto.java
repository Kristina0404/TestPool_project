package de.ait.tp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(name = "NewUser",description = "Registration details")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    @Email
    @NotNull
    @Schema(description = "User email", example = "testpool.ait@gmail.com")
    private String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    @Schema(description = "User password", example = "Qwerty007!")
    private String password;
    @NotNull
    @NotEmpty
    @Schema(description = "User first name", example = "Kristina")
    private String firstName;
    @NotNull
    @NotEmpty
    @Schema(description = "User last name", example = "Romanova")
    private String lastName;
}
