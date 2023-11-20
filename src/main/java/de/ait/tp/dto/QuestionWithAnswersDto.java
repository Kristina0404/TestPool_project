package de.ait.tp.dto;

import de.ait.tp.models.Answer;
import de.ait.tp.models.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionWithAnswersDto {

    @Schema(description = "Question with answers id", example = "1")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long id;
    @Schema(description = "Question", example = "What is an interface in Java?")
    @NotNull
    @NotBlank
    @NotEmpty
    private String question;
    @Schema(description = "Answer1", example = "1")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long answerId1;
    @Schema(description = "Answer1", example = "1")
    @NotNull
    @NotBlank
    @NotEmpty
    private String answer1;

    @Schema(description = "Answer2", example = "1")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long answerId2;

    @Schema(description = "Answer2", example = "2")
    @NotNull
    @NotBlank
    @NotEmpty
    private String answer2;

    @Schema(description = "Answer3", example = "1")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long answerId3;

    @Schema(description = "Answer3", example = "3")
    @NotNull
    @NotBlank
    @NotEmpty
    private String answer3;

    @Schema(description = "Answer4", example = "1")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long answerId4;

    @Schema(description = "Answer4", example = "4")
    @NotNull
    @NotBlank
    @NotEmpty
    private String answer4;

    public static QuestionWithAnswersDto from(
            Question question, Answer answer1, Answer answer2, Answer answer3, Answer answer4) {

        QuestionWithAnswersDto result = QuestionWithAnswersDto.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .answerId1(answer1.getId())
                .answer1(answer1.getAnswer())
                .answerId2(answer2.getId())
                .answer2(answer2.getAnswer())
                .answerId3(answer3.getId())
                .answer3(answer3.getAnswer())
                .answerId4(answer4.getId())
                .answer4(answer4.getAnswer())
                .build();

        return result;
    }

    public static List<QuestionWithAnswersDto> from(Collection<Question> questions, Collection<Answer> answers) {
        List<QuestionWithAnswersDto> questionWithAnswersList = new ArrayList<>();

        for (Question question : questions) {
            List<Answer> questionAnswers = answers
                    .stream()
                    .filter(answer -> answer.getQuestion().equals(question.getId()))
                    .toList();
            if (questionAnswers.size() == 4) {
                QuestionWithAnswersDto dto = getQuestionWithAnswersDto(question, questionAnswers);

                questionWithAnswersList.add(dto);
            }
        }
        return questionWithAnswersList;
    }

    private static QuestionWithAnswersDto getQuestionWithAnswersDto(Question question, List<Answer> questionAnswers) {
        QuestionWithAnswersDto dto = new QuestionWithAnswersDto();
        dto.setQuestion(question.getQuestion());
        dto.setAnswerId1(questionAnswers.get(0).getId());
        dto.setAnswer1(questionAnswers.get(0).getAnswer());
        dto.setAnswerId2(questionAnswers.get(1).getId());
        dto.setAnswer2(questionAnswers.get(1).getAnswer());
        dto.setAnswerId3(questionAnswers.get(2).getId());
        dto.setAnswer3(questionAnswers.get(2).getAnswer());
        dto.setAnswerId4(questionAnswers.get(3).getId());
        dto.setAnswer4(questionAnswers.get(3).getAnswer());
        return dto;
    }
}


