package com.ssafy.apm.quiz.repository;

import com.ssafy.apm.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<List<Quiz>> findAllByStyle(String style);
    Optional<List<Quiz>> findAllByGroupCode(String groupCode);

    @Query(value = "SELECT * FROM quiz ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Quiz> extractRandomQuiz();

    @Query(value = "SELECT * FROM quiz ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Optional<List<Quiz>> extractRandomQuizzes(@Param("limit") Integer limit);

    @Query(value = "SELECT * FROM quiz WHERE style = :style ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Optional<List<Quiz>> extractRandomQuizzesByStyle(@Param("style") String style, @Param("limit") Integer limit);

    @Query(value = "SELECT * FROM quiz WHERE group_code = :groupCode ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Optional<List<Quiz>> extractRandomQuizzesByGroupCode(@Param("groupCode") String groupCode, @Param("limit") Integer limit);

}
