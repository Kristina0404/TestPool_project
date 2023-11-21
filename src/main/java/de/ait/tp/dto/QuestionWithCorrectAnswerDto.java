package de.ait.tp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionWithCorrectAnswerDto {

    @Schema(description = "Question_ID", example = "1")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long questionId;

    @Schema(description = "Answer_ID", example = "2")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long correctAnswerId;

    @Schema(description = "Question Text", example = "What is an interface in Java?")
    private String questionText;

    @Schema(description = "Correct Answer Text", example = "An interface in Java is a collection of abstract methods.")
    private String correctAnswerText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionWithCorrectAnswerDto that = (QuestionWithCorrectAnswerDto) o;
        return Objects.equals(questionId, that.questionId) && Objects.equals(correctAnswerId, that.correctAnswerId) && Objects.equals(questionText, that.questionText) && Objects.equals(correctAnswerText, that.correctAnswerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, correctAnswerId, questionText, correctAnswerText);
    }
}
