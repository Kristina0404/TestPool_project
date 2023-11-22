package de.ait.tp.repositories;

import de.ait.tp.dto.tests.TestResultDto;
import de.ait.tp.models.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByUserId(Long userId);

    TestResult findTopByUserIdAndTestIdOrderByIdDesc(Long userId, Long testId);

    List<TestResultDto> findAllByUserId(Long userId);
}



