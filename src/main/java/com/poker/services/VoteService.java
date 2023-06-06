package com.poker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poker.entities.Story;
import com.poker.entities.User;
import com.poker.entities.Vote;
import com.poker.enums.StoryStatus;
import com.poker.repositories.SessionRepository;
import com.poker.repositories.StoryRepository;
import com.poker.repositories.UserRepository;
import com.poker.repositories.VoteRepository;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    
    @Autowired
    private StoryRepository storyRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SessionRepository sessionRepository;
    
    public void voteForStory(Long userId, Long storyId, Integer voteOption) {
            	    	
    	Story story = storyRepository.findById(storyId)
    		    .orElseThrow(() -> new IllegalArgumentException("Story not found"));

    	User user = userRepository.findById(userId).
    			orElseThrow(() -> new IllegalArgumentException("User not found"));
    	
    	Long sessionId = storyRepository.findSessionIdByStoryId(storyId);
    	
		if(!sessionRepository.existsBySessionIdAndUserId(sessionId, userId)) {
			throw new IllegalArgumentException("Not an active user");
		}
		
    	if (story.getStatus() == StoryStatus.VOTING) {
            boolean hasVoted = voteRepository.existsByVotersIdAndStoryId(userId, storyId);
            if (!hasVoted) {
                // Criar o voto e associá-lo ao usuário, história e opção de voto
                Vote vote = new Vote();
                vote.setOption(voteOption);
                vote.setStory(story);
                vote.setVoters(user);

                voteRepository.save(vote);
                
                storyRepository.save(story);
            }else {
            	throw new IllegalArgumentException("User already voted");
            }
        }else {
            throw new IllegalArgumentException("Story is not in the VOTING status");
        }
    
    }
    
    public void closeVotes(Long voteId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new IllegalArgumentException("Vote not found"));

        if (vote.getStory().getStatus() == StoryStatus.VOTING) {
            Story story = vote.getStory();
            story.setStatus(StoryStatus.VOTED);
            storyRepository.save(story);
        } else {
            throw new IllegalArgumentException("Story is not in the VOTING status");
        }
    }
    
    public List<Vote> votesByStory(Long storyId){
    	
		List<Vote> votes = voteRepository.findByStoryId(storyId)
				.orElseThrow(() -> new IllegalArgumentException("Story doesn't exist "));
    	
    	return votes;
    	
    }

}
