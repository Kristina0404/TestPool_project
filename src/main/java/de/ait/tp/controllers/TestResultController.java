package de.ait.tp.controllers;

import de.ait.tp.controllers.api.TestResultsApi;

import de.ait.tp.dto.TestResultDto;
import de.ait.tp.dto.TestTotalResultDto;
import de.ait.tp.models.User;
import de.ait.tp.repositories.UsersRepository;
import de.ait.tp.security.details.AuthenticatedUser;
import de.ait.tp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyAuthority('USER')")
public class TestResultController implements TestResultsApi {

    private final TestResultService testResultService;
    private final UsersRepository usersRepository;


    @Override
    public TestTotalResultDto calculateAndSaveTestResult(Long testId, List<Long> userAnswers, Authentication authentication) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        String username = authenticatedUser.getUsername();
        User user =usersRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with email <" + username + "> not found"));
       Long userId = user.getId();
        return testResultService.calculateCorrectAnswersAndSum(userId,testId,userAnswers);

    }

    @Override
    public List<TestResultDto> getTestResultsForUser(Long userId) {
       return testResultService.getTestResultsForUser(userId);

   }
}

