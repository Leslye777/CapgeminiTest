package com.poker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poker.entities.Session;

public interface SessionRepository extends JpaRepository<Session, Long>{

	@Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM SESSIONS_ACTIVE_USERS WHERE SESSIONS_ID = :sessionId AND ACTIVE_USERS_ID = :userId) THEN true ELSE false END FROM DUAL", nativeQuery = true)
    boolean existsBySessionIdAndUserId(Long sessionId, Long userId);
}
