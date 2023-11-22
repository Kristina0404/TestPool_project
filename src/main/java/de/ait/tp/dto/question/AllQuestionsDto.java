package de.ait.tp.dto.question;

import de.ait.tp.models.Question;
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
@Schema(description = "Array of all questions", example = "[{\"id\": 1," +
        " \"question\": \"What is an interface in Java?\", \"testId\": 5}]")
public class AllQuestionsDto {

    @Schema(description = "Question_ID", example = "1")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long id;

    @Schema(description = "Question", example = "")
    @NotNull
    @NotBlank
    @NotEmpty
    private String question;
    @Schema(description = "Test_ID", example = "5")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long testId;


    public static QuestionDto from(Question question) {
        QuestionDto result = QuestionDto.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .build();

        if (question.getTest() != null) {
            result.setTestId(question.getTest().getId());
        }
        return result;
    }

    public static List<QuestionDto> from(Collection<Question> questions) {
        return questions.stream()
                .map(QuestionDto::from).collect(Collectors.toList());
    }

}


