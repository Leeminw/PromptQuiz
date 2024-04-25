package com.ssafy.apm.quiz.repository;

import com.ssafy.apm.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query(value = "SELECT * FROM quiz order by RAND() LIMIT :limitMax",nativeQuery =true)
    List<Quiz> findAllQuizRandom(@Param(value="limitMax") Integer limitMax);
}
