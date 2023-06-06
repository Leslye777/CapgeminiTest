package com.poker.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poker.dto.VoteDto;
import com.poker.entities.Vote;
import com.poker.services.VoteService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/votes")
public class VoteResource {

	@Autowired
	private VoteService voteService;
	
    @GetMapping("/{id}")
	public ResponseEntity<?> votesByStory(@PathVariable Long id) {
    	
    	List<Vote> votes = new ArrayList<>();
    	
		try {
			votes = voteService.votesByStory(id);
			if(votes.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No votes yet");
			}
			return ResponseEntity.ok(votes);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping("/vote")
	public ResponseEntity<String> voteForStory(@RequestBody VoteDto vote) {

		try {
			voteService.voteForStory(vote.getUserId(), vote.getStoryId(), vote.getVoteOption());
			return ResponseEntity.ok("Voted successfully");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
