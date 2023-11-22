package de.ait.tp.dto.tests;

import de.ait.tp.models.Test;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.*;

@Data
@Schema(name = "Test", description = "Adding a new test")

public class NewTestDto {

    @Schema(description = "Test name", example = "Frontend")
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @Schema(description = "Course type", example = "FRONTEND")
    @NotNull
    @NotBlank
    private Test.Type type;
    @Schema(description = "Knowledge level", example = "JUNIOR")
    @NotNull
    private Test.Level level;
}
