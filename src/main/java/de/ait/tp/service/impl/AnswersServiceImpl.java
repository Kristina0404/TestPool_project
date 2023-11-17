package de.ait.tp.service.impl;

import de.ait.tp.dto.*;
import de.ait.tp.exceptions.RestException;
import de.ait.tp.models.Question;
import de.ait.tp.models.Answer;
import de.ait.tp.models.Test;
import de.ait.tp.repositories.AnswersRepository;
import de.ait.tp.repositories.QuestionsRepository;
import de.ait.tp.service.AnswersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static de.ait.tp.dto.QuestionDto.from;
import static de.ait.tp.dto.AnswerDto.from;
import static de.ait.tp.models.Answer.*;

@RequiredArgsConstructor
@Service
@Component
public class AnswersServiceImpl implements AnswersService {
    private final AnswersRepository answersRepository;
    private final QuestionsRepository questionsRepository;
    @Override
    public AnswerDto addAnswerToQuestion(Long questionId, NewAnswerDto newAnswer) {
        Question question = getQuestionOrThrow(questionId);

        if (answersRepository.existsByAnswerAndQuestionId(newAnswer.getAnswer(), questionId)) {
            throw new RestException(HttpStatus.CONFLICT,
                    "Answer < " + newAnswer + " to question with id " +
                            questionId + "> already exists");
        }
        Answer answer = Answer.builder()
                .answer(newAnswer.getAnswer())
                .question(question)
                .isCorrect(newAnswer.isCorrect())
                .build();

        answersRepository.save(answer);

        return AnswerDto.from(answer);

    }
    @Override
    public List<AnswerDto> getAllAnswers() {
        List<Answer> answers = answersRepository.findAll();
        return AnswerDto.from(answers);
    }
    @Override
    public AnswerDto updateAnswerInQuestion(Long questionId, Long answerId, UpdateAnswerDto updateAnswer) {
        Question question = getQuestionOrThrow(questionId);
        Answer answer = answersRepository.findByQuestionAndId(question, answerId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Answer with id < " + answerId +
                                "> not found in question with id < " + questionId + ">"));
        answer.setAnswer(updateAnswer.getAnswer());
        answersRepository.save(answer);
        return from(answer);
    }

    @Override
    public AnswerDto deleteAnswerFromQuestion(Long questionId, Long answerId) {
        Question question = getQuestionOrThrow(questionId);

        Answer answer = answersRepository.findByQuestionAndId(question, answerId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Answer with id <" + answerId +
                                "> not found in question with id <" + questionId + ">"));

        answersRepository.delete(answer);
        return from(answer);
    }

    public boolean getCorrectAnswer(Long selectedAnswerId) {
        List<Answer> correctAnswers = answersRepository.findAllById(selectedAnswerId);
        if (correctAnswers.size() == 1) {
            Answer selectedAnswer = correctAnswers.get(0);
            return selectedAnswer.isCorrect();
        } else if (correctAnswers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Answer not found with id: " +
                    selectedAnswerId);
        }
        return false;
    }
    private Question getQuestionOrThrow(Long questionId) {
        return questionsRepository.findById(questionId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Question with id <" + questionId + "> not found"));
    }

}
