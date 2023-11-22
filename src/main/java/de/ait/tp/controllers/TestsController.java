package de.ait.tp.controllers;

import de.ait.tp.controllers.api.TestsApi;
import de.ait.tp.dto.tests.NewTestDto;
import de.ait.tp.dto.tests.TestDto;
import de.ait.tp.dto.tests.UpdateTestDto;
import de.ait.tp.service.TestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class TestsController implements TestsApi {

    private final TestsService testsService;

    @Override
    public TestDto addTest(NewTestDto newTest) {

        return testsService.addTest(newTest);
    }

    @Override
    public List<TestDto> getAllTests() {
        return testsService.getAllTests();
    }

    @Override
    public TestDto updateTest(Long testId, UpdateTestDto updateTest) {
        return testsService.updateTest(testId, updateTest);
    }

    @Override
    public TestDto deleteTest(Long testId) {
        return testsService.deleteTest(testId);
    }

}

