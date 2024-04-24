package com.ssafy.apm.quiz.repository;

import com.ssafy.apm.quiz.domain.QuizEntity;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository  extends CrudRepository<QuizEntity, Long> {
}
