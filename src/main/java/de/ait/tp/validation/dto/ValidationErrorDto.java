package de.ait.tp.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ValidationError", description = "Description of the validation error")
public class ValidationErrorDto {

    @Schema(description = "Name of the field in which the error occurred", example = "password")
    private String field;
    @Schema(description = "the name that the user entered and was rejected by the server", example = "13345")
    private String rejectedValue;
    @Schema(description = "message that the user sees",
            example = "password must be at least 8 characters long " +
                    "and contain at least one capital letter and one symbol(@#$%^&+=!])")
    private String message;
}
