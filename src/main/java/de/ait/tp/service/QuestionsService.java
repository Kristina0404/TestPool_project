package de.ait.tp.service;

import de.ait.tp.dto.*;

import java.util.List;

public interface QuestionsService {

    List<QuestionDto> getAllQuestions();

    QuestionDto addQuestionToTest(Long testId, NewQuestionDto newQuestion);

    QuestionDto updateQuestionInTest(Long testId, Long questionId, UpdateQuestionDto updateQuestion);

    QuestionDto deleteQuestionFromTest(Long questionId);

    List<QuestionWithAnswersDto> getAllQuestionIds(Long testId);

}




