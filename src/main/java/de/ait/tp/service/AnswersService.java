package de.ait.tp.service;

import de.ait.tp.dto.answer.AnswerDto;
import de.ait.tp.dto.answer.NewAnswerDto;
import de.ait.tp.dto.answer.UpdateAnswerDto;

import java.util.List;

public interface AnswersService {
    AnswerDto addAnswerToQuestion(Long questionId, NewAnswerDto newAnswer);

    List<AnswerDto> getAllAnswers();

    AnswerDto updateAnswerInQuestion(Long questionId, Long answerId, UpdateAnswerDto updateAnswer);

    AnswerDto deleteAnswerFromQuestion(Long questionId, Long answerId);

    List<Long> getCorrectAnswerIds(Long testId);

}
