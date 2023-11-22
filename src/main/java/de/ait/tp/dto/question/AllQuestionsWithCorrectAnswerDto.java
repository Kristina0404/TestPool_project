package de.ait.tp.dto.question;

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
@Schema(description =  "Array of all questions with correct answers" , example ="[{\n" +
        "  \"questionId\": 1,\n" +
        "  \"correctAnswerId\": 2,\n" +
        "  \"questionText\": \"What is an interface in Java?\",\n" +
        "  \"correctAnswerText\": \"An interface in Java is a collection of abstract methods.\"\n" +
        "}]")
public class AllQuestionsWithCorrectAnswerDto {
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


}
