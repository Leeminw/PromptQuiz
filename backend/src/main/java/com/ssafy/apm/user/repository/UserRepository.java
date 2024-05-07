package com.ssafy.apm.user.repository;

import com.ssafy.apm.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    boolean existsUserByUserName(String userName);
    Optional<List<User>> findTop10ByOrderByTeamScoreDesc();
    Optional<List<User>> findTop10ByOrderBySoloScoreDesc();
    Optional<List<User>> findTop10ByOrderByTotalScoreDesc();
 }
