package de.ait.tp.service.impl;

import de.ait.tp.dto.*;
import de.ait.tp.exceptions.ConflictException;
import de.ait.tp.exceptions.RestException;
import de.ait.tp.models.Answer;
import de.ait.tp.models.Question;
import de.ait.tp.dto.QuestionWithAnswersDto;
import de.ait.tp.models.Test;
import de.ait.tp.repositories.AnswersRepository;
import de.ait.tp.repositories.QuestionsRepository;
import de.ait.tp.repositories.TestsRepository;
import de.ait.tp.service.QuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

import static de.ait.tp.dto.QuestionDto.from;


@RequiredArgsConstructor
@Service
@Component
public class QuestionsServiceImpl implements QuestionsService {

    @Value("${myapp.maxQuestions}")
    private int maxQuestions;

    private final QuestionsRepository questionsRepository;
    private final TestsRepository testsRepository;
    private final AnswersRepository answersRepository;

    @Override
    public QuestionDto addQuestionToTest(Long testId, NewQuestionDto newQuestion) {
        Test test = getTestOrThrow(testId);
        if (questionsRepository.existsByQuestionAndTestId(newQuestion.getQuestion(), testId)) {
            throw new ConflictException("Question < " + newQuestion + "> already exists");
        }
        Question question = Question.builder()
                .question(newQuestion.getQuestion())
                .test(test)
                .build();

        questionsRepository.save(question);
        return QuestionDto.from(question);

    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = questionsRepository.findAll();
        return QuestionDto.from(questions);
    }

    @Override
    public List<QuestionWithAnswersDto> getAllQuestionIds(Long testId) {
        List<Long> ids = questionsRepository.getAllQuestionIdsByTestId(testId);
        List<QuestionWithAnswersDto> resultList = new ArrayList<>();

        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> chosenQuestionIds = new ArrayList<>();
        Random random = new Random();

        int totalQuestions = Math.min(ids.size(), maxQuestions);
        while (resultList.size() < totalQuestions) {
            int randomIndex = random.nextInt(ids.size());
            Long randomId = ids.get(randomIndex);

            if (!chosenQuestionIds.contains(randomId)) {
                chosenQuestionIds.add(randomId);
                Optional<Question> optionalQuestion = questionsRepository.findById(randomId);

                if (optionalQuestion.isPresent()) {
                    Question question = optionalQuestion.get();
                    List<Answer> answers = answersRepository.findAnswersByQuestionId(randomId);

                    if (!answers.isEmpty()) {
                        QuestionWithAnswersDto dto = QuestionWithAnswersDto
                                .from(question, answers.get(0), answers.get(1), answers.get(2), answers.get(3));
                        resultList.add(dto);
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public QuestionDto updateQuestionInTest(Long testId, Long questionId, UpdateQuestionDto updateQuestion) {

        Test test = getTestOrThrow(testId);
        Question question = questionsRepository.findByTestAndId(test, questionId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Question with id < " + questionId +
                                "> not found in test with id < " + testId + ">"));
        question.setQuestion(updateQuestion.getQuestion());
        questionsRepository.save(question);
        return from(question);
    }

    @Override
    public QuestionDto deleteQuestionFromTest(Long questionId) {
        Question question = questionsRepository.findQuestionById(questionId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Question with id <" + questionId + "> not found >"));
        questionsRepository.delete(question);
        return from(question);
    }

    private Test getTestOrThrow(Long testId) {
        return testsRepository.findById(testId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Test with id <" + testId + "> not found"));
    }

   /* public List<QuestionCorrectAnswerDto> getQuestionsWithCorrectAnswers() {
        List<Question> questions = questionsRepository.findAll();
        List<Answer> correctAnswers = answersRepository.findByIsCorrectTrue();

        List<QuestionCorrectAnswerDto> result = new ArrayList<>();
        for (Question question : questions) {
            for (Answer correctAnswer : correctAnswers) {
                if (correctAnswer.getQuestion().getId().equals(question.getId())) {
                    QuestionCorrectAnswerDto dto = new QuestionCorrectAnswerDto(
                            question.getId(),
                            question.getQuestion().getId(),
                            correctAnswer.getId()
                    );
                    result.add(dto);
                }
            }
        }

        return result;
    }*/
}





