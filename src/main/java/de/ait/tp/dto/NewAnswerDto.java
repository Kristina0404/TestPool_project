package de.ait.tp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(name = "NewAnswer")
public class NewAnswerDto {
    @Schema(description = "Answer", example = "answer1")
    @NotNull
    @NotBlank
    @NotEmpty
    private String answer;
    @Schema(description = "Correct answer", example = "true")
    @NotNull
    @NotBlank
    @NotEmpty
    private boolean isCorrect;
}
