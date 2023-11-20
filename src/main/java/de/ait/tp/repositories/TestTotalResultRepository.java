package de.ait.tp.repositories;

import de.ait.tp.models.TestTotalResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestTotalResultRepository extends JpaRepository<TestTotalResult, Long> {
    @Query("SELECT t FROM TestTotalResult t  ORDER BY t.totalCorrectAnswer DESC")
    List<TestTotalResult> findAllByOrderByTotalCorrectAnswerDesc();

    List<TestTotalResult> findByUserIdOrderByTotalCorrectAnswerDesc(Long userId);

}


