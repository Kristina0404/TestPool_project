package de.ait.tp.dto.answer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UpdateAnswer", description = "Answer update ")
public class UpdateAnswerDto {

    @Schema(description = "Answer_ID", example = "10")
    @Positive
    private Long id;
    @Schema(description = "Answer", example = "answer1")
    @NotNull
    @NotBlank
    @NotEmpty
    private String answer;
    @Schema(description = "Correct answer", example = "true")
    @NotNull
    private boolean isCorrect;
    @Schema(description = "Question_ID", example = "2")
    @NotNull
    private Long questionId;
}
