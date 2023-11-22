package de.ait.tp.controllers;

import de.ait.tp.controllers.api.AnswersApi;
import de.ait.tp.dto.answer.AnswerDto;
import de.ait.tp.dto.answer.NewAnswerDto;
import de.ait.tp.dto.answer.UpdateAnswerDto;
import de.ait.tp.models.Answer;
import de.ait.tp.repositories.AnswersRepository;
import de.ait.tp.service.AnswersService;
import de.ait.tp.service.QuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AnswersController implements AnswersApi {

    private final AnswersService answersService;
    private final QuestionsService questionsService;
    private final AnswersRepository answersRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public AnswerDto addAnswerToQuestion(Long questionId, NewAnswerDto newAnswer) {
        return answersService.addAnswerToQuestion(questionId, newAnswer);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
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
        Optional<Answer> answerOptional = answersRepository.findById(selectedAnswerId);

        if (answerOptional.isPresent()) {
            AnswerDto selectedAnswer = AnswerDto.from(answerOptional.get());
            return ResponseEntity.ok(new AnswerDto(
                    selectedAnswer.getId(),
                    selectedAnswer.getAnswer(),
                    selectedAnswer.isCorrect(),
                    selectedAnswer.getQuestionId()
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}