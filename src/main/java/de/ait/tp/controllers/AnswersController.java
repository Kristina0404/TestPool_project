package de.ait.tp.controllers;

import de.ait.tp.controllers.api.AnswersApi;
import de.ait.tp.dto.*;
import de.ait.tp.service.AnswersService;
import de.ait.tp.service.QuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AnswersController implements AnswersApi {

    private final AnswersService answersService;
    private final QuestionsService questionsService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public AnswerDto addAnswerToQuestion(Long questionId, NewAnswerDto newAnswer) {
        return answersService.addAnswerToQuestion(questionId, newAnswer);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public List<AnswerDto> getAllAnswers() {
        return answersService.getAllAnswers();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public AnswerDto updateAnswerInQuestion(Long questionId, Long answerId, UpdateAnswerDto updateAnswer) {
        return answersService.updateAnswerInQuestion(questionId, answerId, updateAnswer);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public AnswerDto deleteAnswerFromQuestion(Long questionId, Long answerId) {
        return answersService.deleteAnswerFromQuestion(questionId, answerId);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @Override
    public ResponseEntity<AnswerDto> getCorrectAnswer(Long selectedAnswerId) {
        AnswerDto answer = answersService.getCorrectAnswer(selectedAnswerId);

        if (answer != null ) {
            return ResponseEntity.ok(answer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
