package de.ait.tp.controllers;

import de.ait.tp.controllers.api.TestResultsApi;

import de.ait.tp.dto.TestResultDto;
import de.ait.tp.dto.TestTotalResultDto;
import de.ait.tp.models.TestResult;
import de.ait.tp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyAuthority('USER')")
public class TestResultController implements TestResultsApi {

    private final TestResultService testResultService;

    @Override
    public TestTotalResultDto calculateAndSaveTestResult(Long userId, Long testId, List<Long> userAnswers) {
         return testResultService.calculateCorrectAnswersAndSum(userId, testId, userAnswers);
    }
    @Override
    public List<TestResultDto> getTestResultsForUser(Long userId) {
       return testResultService.getTestResultsForUser(userId);

   }
}

