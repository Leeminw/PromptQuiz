package com.ssafy.apm.quiz.repository;

import com.ssafy.apm.quiz.domain.Quiz;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<List<Quiz>> findAllByStyle(String style);

    Optional<List<Quiz>> findAllByGroupCode(String groupCode);

    @Query(value = "SELECT * FROM quiz WHERE url IS NOT NULL ORDER BY RAND() LIMIT 1 ", nativeQuery = true)
    Optional<Quiz> extractRandomQuiz();

    @Query(value = "SELECT * FROM quiz WHERE url IS NOT NULL ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Optional<List<Quiz>> extractRandomQuizzes(@Param("limit") Integer limit);

    @Query(value = "SELECT * FROM quiz WHERE style = :style AND url IS NOT NULL ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Optional<List<Quiz>> extractRandomQuizzesByStyle(@Param("style") String style, @Param("limit") Integer limit);

    @Query(value = "SELECT * FROM quiz WHERE group_code = :groupCode AND url IS NOT NULL ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Optional<List<Quiz>> extractRandomQuizzesByGroupCode(@Param("groupCode") String groupCode, @Param("limit") Integer limit);

    @Query(value = "SELECT * FROM quiz WHERE style = :style AND group_code = :groupCode AND url IS NOT NULL ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Optional<List<Quiz>> extractRandomQuizzesByStyleAndGroupCode(@Param("style") String style, @Param("groupCode") String groupCode, @Param("limit") Integer limit);

}
