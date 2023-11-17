package de.ait.tp.dto;

import de.ait.tp.models.Answer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {

    @Schema(description = "Answer_ID", example = "1")
    @Positive
    private Long id;
    @Schema(description = "Answer",example= "answer1")
    @NotNull
    @NotBlank
    @NotEmpty
    private String answer;
    @Schema(description = "Correct answer",example= "true")
    @NotNull
    @NotBlank
    @NotEmpty
    private boolean isCorrect;
    @Schema(description = "Question_ID",example= "2")
    @NotNull
    private Long questionId;

    public static AnswerDto from(Answer answer) {
        return AnswerDto.builder()
                .id(answer.getId())
                .answer(answer.getAnswer())
                .isCorrect(answer.isCorrect())
                .questionId(answer.getQuestion().getId())
                .build();
    }

    public static List<AnswerDto> from(Collection<Answer> answers){
        return answers.stream()
                .map(AnswerDto::from).collect(Collectors.toList());
    }
}
