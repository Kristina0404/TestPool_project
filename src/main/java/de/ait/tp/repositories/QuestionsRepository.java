package de.ait.tp.repositories;

import de.ait.tp.dto.QuestionDto;
import de.ait.tp.models.Question;
import de.ait.tp.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long> {

    Optional<Question> findById(Long existsQuestionId);

    Optional<Question> findByTestAndId(Test test, Long questionId);

    Optional<Question> findQuestionById(Long questionId);

    @Query("SELECT q.id FROM Question q WHERE q.test.id = ?1")
    List<Long> getAllQuestionIdsByTestId(Long testId);

    boolean existsByQuestionAndTestId(String question, Long testId);

}

