package de.ait.tp.dto.answer;

import de.ait.tp.models.Answer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Array of all answers", example = "[{\"id\": 1," +
        " \"answer\": \"answer1\", \"isCorrect\": \"true\",\"questionId\": 6}]")
public class AllAnswersDto {

    @Schema(description = "Answer_ID")
    private Long id;
    @Schema(description = "Answer")
    @NotNull
    @NotBlank
    @NotEmpty
    private String answer;
    @Schema(description = "Correct answer")
    @NotNull
    @NotBlank
    @NotEmpty
    private boolean isCorrect;
    @Schema(description = "Question_ID")
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

    public static List<AnswerDto> from(Collection<Answer> answers) {
        return answers.stream()
                .map(AnswerDto::from).collect(Collectors.toList());
    }
}


