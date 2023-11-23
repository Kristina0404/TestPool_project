package de.ait.tp.controllers;

import de.ait.tp.controllers.api.QuestionsApi;
import de.ait.tp.dto.question.*;
import de.ait.tp.service.AnswersService;
import de.ait.tp.service.QuestionsService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class QuestionsController implements QuestionsApi {
    private final QuestionsService questionsService;
    private final AnswersService answersService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public QuestionDto addQuestionToTest(Long testId, NewQuestionDto newQuestion) {
        return questionsService.addQuestionToTest(testId, newQuestion);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public List<QuestionDto> getAllQuestions() {
        return questionsService.getAllQuestions();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public QuestionDto updateQuestionInTest(Long testId, Long questionId, UpdateQuestionDto updateQuestion) {
        return questionsService.updateQuestionInTest(testId, questionId, updateQuestion);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public QuestionDto deleteQuestionFromTest(Long questionId) {
        return questionsService.deleteQuestionFromTest(questionId);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @Override
    public List<QuestionWithAnswersDto> getAllQuestionIds(Long testId) {
        return questionsService.getAllQuestionIds(testId);

    }

    @Override
    public List<QuestionWithCorrectAnswerDto> getQuestionsWithCorrectAnswers() {
        return questionsService.getQuestionsWithCorrectAnswers();
    }

    @Override
    public ResponseEntity<QuestionWithCorrectAnswerDto> getCorrectAnswerByQuestionId(Long questionId) {
        QuestionWithCorrectAnswerDto resultDto = questionsService.getCorrectAnswerByQuestionId(questionId);

        if (resultDto != null) {
            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


