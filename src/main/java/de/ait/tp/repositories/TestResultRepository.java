package de.ait.tp.repositories;

import de.ait.tp.dto.TestResultDto;
import de.ait.tp.models.Test;
import de.ait.tp.models.TestResult;
import de.ait.tp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByUserId(Long userId);

    TestResult findTopByUserIdAndTestIdOrderByIdDesc(Long userId, Long testId);

    List<TestResultDto> findAllByUserId(Long userId);

}



