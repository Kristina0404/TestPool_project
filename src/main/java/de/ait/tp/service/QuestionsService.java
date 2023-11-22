package de.ait.tp.service;

import de.ait.tp.dto.question.*;
import de.ait.tp.models.Answer;

import java.util.List;

public interface QuestionsService {

    List<QuestionDto> getAllQuestions();

    QuestionDto addQuestionToTest(Long testId, NewQuestionDto newQuestion);


    QuestionDto deleteQuestionFromTest(Long questionId);

    QuestionDto updateQuestionInTest(Long testId, Long questionId, UpdateQuestionDto updateQuestion);

    List<QuestionWithAnswersDto> getAllQuestionIds(Long testId);

    QuestionWithCorrectAnswerDto mapToDto(Answer answer);

    List<QuestionWithCorrectAnswerDto> getQuestionsWithCorrectAnswers();

    QuestionWithCorrectAnswerDto getCorrectAnswerByQuestionId(Long questionId);
}




