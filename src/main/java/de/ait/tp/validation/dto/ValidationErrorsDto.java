package de.ait.tp.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ValidationErrors", description = "Validation error information")
public class ValidationErrorsDto {
    @Schema(description = "List of validation errors")
    private List<ValidationErrorDto> errors;

}
