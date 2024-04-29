package com.ssafy.apm.prompt.repository;

import com.ssafy.apm.prompt.domain.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    Optional<List<Prompt>> findAllByStyle(String style);
    Optional<List<Prompt>> findAllByGroupCode(String groupCode);

    @Query(value = "SELECT * FROM prompt ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Prompt> extractRandomPrompt();

    @Query(value = "SELECT * FROM prompt ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Optional<List<Prompt>> extractRandomPrompts(@Param("limit") Integer limit);

    @Query(value = "SELECT * FROM prompt WHERE group_code = :groupCode ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Optional<List<Prompt>> extractRandomPromptsByGroupCode(@Param("groupCode") String groupCode, @Param("limit") Integer limit);

}
