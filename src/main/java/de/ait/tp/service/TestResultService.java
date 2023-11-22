package de.ait.tp.service;

import de.ait.tp.dto.tests.TestResultDto;
import de.ait.tp.dto.tests.TestTotalResultDto;

import java.util.List;

public interface TestResultService {
    int calculateCorrectAnswers(Long userId, Long testId, List<Long> userAnswers);

    TestTotalResultDto calculateTotalCorrectAnswers(Long userId);

    TestTotalResultDto calculateCorrectAnswersAndSum(Long userId, Long testId, List<Long> userAnswers);

    List<TestResultDto> getTestResultsForUser(Long userId);

}