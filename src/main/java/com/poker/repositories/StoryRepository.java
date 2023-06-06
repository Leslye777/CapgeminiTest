package com.poker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poker.entities.Story;
import com.poker.enums.StoryStatus;

public interface StoryRepository extends JpaRepository<Story, Long>{

	List<Story> findBySessionId(Long sessionId);

	List<Story> findByStatus(StoryStatus status);
	
	@Query("SELECT s.session.id FROM Story s WHERE s.id = :storyId")
    Long findSessionIdByStoryId(Long storyId);

}
