package de.ait.tp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Schema(name = "UpdateUser", description = "Name,types or level update ")

public class UpdateUserDto {
    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User firstname", example = "Kristi")
    @NotNull
    @NotBlank
    @NotEmpty
    private String firstName;

    @Schema(description = "User lastname", example = "Romanova")
    @NotNull
    @NotBlank
    @NotEmpty
    private String lastName;

}
