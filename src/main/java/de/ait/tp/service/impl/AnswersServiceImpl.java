package de.ait.tp.service.impl;

import de.ait.tp.dto.answer.AnswerDto;
import de.ait.tp.dto.answer.NewAnswerDto;
import de.ait.tp.dto.answer.UpdateAnswerDto;
import de.ait.tp.exceptions.RestException;
import de.ait.tp.models.Question;
import de.ait.tp.models.Answer;
import de.ait.tp.models.Test;
import de.ait.tp.repositories.AnswersRepository;
import de.ait.tp.repositories.QuestionsRepository;
import de.ait.tp.repositories.TestsRepository;
import de.ait.tp.service.AnswersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static de.ait.tp.dto.answer.AnswerDto.from;

@RequiredArgsConstructor
@Service
@Component
public class AnswersServiceImpl implements AnswersService {
    private final AnswersRepository answersRepository;
    private final QuestionsRepository questionsRepository;
    private final TestsRepository testsRepository;

    @Override
    @Transactional
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

    @Override
    public List<Long> getCorrectAnswerIds(Long testId) {
        Test test = testsRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Тест не найден"));
        List<Long> correctAnswerIds = new ArrayList<>();

        for (Question question : test.getQuestions()) {
            for (Answer answer : question.getAnswers()) {
                if (answer.isCorrect()) {
                    correctAnswerIds.add(answer.getId());
                }
            }
        }
        return correctAnswerIds;
    }

    private Question getQuestionOrThrow(Long questionId) {
        return questionsRepository.findById(questionId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Question with id <" + questionId + "> not found"));
    }
}