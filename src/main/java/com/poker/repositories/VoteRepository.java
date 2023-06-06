package com.poker.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poker.entities.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long>{


	boolean existsByVotersIdAndStoryId(Long userId, Long storyId);

	Optional<List<Vote>> findByStoryId(Long storyId);

}
