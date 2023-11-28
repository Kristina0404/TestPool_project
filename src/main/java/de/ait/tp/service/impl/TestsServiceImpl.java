package de.ait.tp.service.impl;

import de.ait.tp.dto.tests.NewTestDto;
import de.ait.tp.dto.tests.TestDto;
import de.ait.tp.dto.tests.UpdateTestDto;
import de.ait.tp.exceptions.RestException;
import de.ait.tp.models.Test;
import de.ait.tp.repositories.TestsRepository;
import de.ait.tp.service.TestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.ait.tp.dto.tests.TestDto.from;

@RequiredArgsConstructor
@Service
@Component
public class TestsServiceImpl implements TestsService {

    private final TestsRepository testsRepository;

    @Override
    @Transactional
    public TestDto addTest(NewTestDto newTest) {
        if (testsRepository.existsByNameAndTypeAndLevel(newTest.getName(), newTest.getType(), newTest.getLevel())) {
            throw new RestException(HttpStatus.CONFLICT,
                    "Test with parameter < " + newTest.getName() + "," +
                            newTest.getType() + "," + newTest.getLevel() + "> already exists");
        }
        Test test = Test.builder()
                .name(newTest.getName())
                .type(newTest.getType())
                .level(newTest.getLevel())
                .build();

        testsRepository.save(test);

        return TestDto.from(test);

    }

    @Override
    public List<TestDto> getAllTests() {
        List<Test> tests = testsRepository.findAll();
        return TestDto.from(tests);
    }

    @Override
    public TestDto updateTest(Long testId, UpdateTestDto updateTest) {

        Test test = testsRepository.findTestById(testId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Test with id < " + testId + "> not found >"));
        test.setName(updateTest.getName());
        test.setType(updateTest.getType());
        test.setLevel(updateTest.getLevel());
        testsRepository.save(test);
        return from(test);
    }

    @Override
    public TestDto deleteTest(Long testId) {
        Test test = testsRepository.findTestById(testId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "Test with id <" + testId + "> not found >"));
        testsRepository.delete(test);
        return TestDto.from(test);
    }
}
