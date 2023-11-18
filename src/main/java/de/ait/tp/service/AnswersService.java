package de.ait.tp.service;

import de.ait.tp.dto.*;
import java.util.List;

public interface AnswersService {
    AnswerDto addAnswerToQuestion(Long questionId, NewAnswerDto newAnswer);
    List<AnswerDto> getAllAnswers();
    AnswerDto updateAnswerInQuestion(Long questionId, Long answerId, UpdateAnswerDto updateAnswer);
    AnswerDto deleteAnswerFromQuestion(Long questionId, Long answerId);
    AnswerDto getCorrectAnswer(Long selectedAnswerId);
}
