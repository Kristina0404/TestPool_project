package de.ait.tp.repositories;

import de.ait.tp.dto.TestDto;
import de.ait.tp.dto.TestResultDto;
import de.ait.tp.models.Test;
import de.ait.tp.models.TestResult;
import de.ait.tp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TestsRepository  extends JpaRepository<Test,Long> {
    Optional<Test> findById(Long id);
    Optional<Test> findTestById(Long testId);
    boolean existsByNameAndTypeAndLevel(String name, Test.Type type, Test.Level level);
}



