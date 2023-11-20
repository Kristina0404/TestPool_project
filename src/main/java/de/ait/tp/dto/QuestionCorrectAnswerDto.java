package de.ait.tp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionCorrectAnswerDto {

    @Schema(description = "Question_ID", example = "1")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long id;

    @Schema(description = "Question_ID", example = "1")
    @NotNull
    private Long questionId;

    @Schema(description = "Answer_ID", example = "2")
    @NotNull
    private Long correctAnswerId;

}
