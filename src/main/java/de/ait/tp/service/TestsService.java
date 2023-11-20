package de.ait.tp.service;

import de.ait.tp.dto.NewTestDto;
import de.ait.tp.dto.TestDto;
import de.ait.tp.dto.UpdateTestDto;

import java.util.List;

public interface TestsService {

    TestDto addTest(NewTestDto newTest);

    List<TestDto> getAllTests();

    TestDto updateTest(Long testId, UpdateTestDto updateTest);

    TestDto deleteTest(Long testId);

}
