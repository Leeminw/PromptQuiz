package com.ssafy.apm.multiplechoice.repository;

import com.ssafy.apm.multiplechoice.domain.MultipleChoiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultipleChoiceRepository extends CrudRepository<MultipleChoiceEntity, Long> {
}
