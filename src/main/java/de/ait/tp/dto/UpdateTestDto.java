package de.ait.tp.dto;

import de.ait.tp.models.Test;
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
@Schema(name = "UpdateTest", description = "Name,types or level update ")
public class UpdateTestDto {
    @Schema(description = "Test_ID", example = "1")
    private Long id;
    @Schema(description = "Test name", example = "new Test")
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @Schema(description = "Course type", example = "description of course")
    @NotNull
    private Test.Type type;
    @Schema(description = "Knowledge level", example = "description of knowledge")
    @NotNull
    private Test.Level level;
}
