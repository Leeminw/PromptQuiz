package com.ssafy.apm.multiplechoice.repository;

import com.ssafy.apm.multiplechoice.domain.MultipleChoiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MultipleChoiceRepository extends CrudRepository<MultipleChoiceEntity, Long> {
    Optional<List<MultipleChoiceEntity>> findAllByGameQuizId(Long gameQuizId);
}
