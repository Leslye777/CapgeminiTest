package com.poker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poker.entities.Session;
import com.poker.entities.Story;
import com.poker.enums.StoryStatus;
import com.poker.repositories.StoryRepository;

@Service
public class StoryService {

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private SessionService sessionService;

	public Story createStory(Long sessionId, Story story) {	    
		
		Session session = sessionService.getSessionById(sessionId).get();
		story.setSession(session);
		story.setStatus(StoryStatus.PENDING);	
		Story storyReturn = storyRepository.save(story);
		sessionService.addStory(sessionId, storyReturn);
		return storyReturn;

	}
	//fazer a 2 e mudar o enum para int
	public Story changeStateStory(Long id) {
		Story story = getStoryById(id).get();
		if(story.getStatus()==StoryStatus.PENDING) {
			story.setStatus(StoryStatus.VOTING);
		}else if(story.getStatus()==StoryStatus.VOTING){
			story.setStatus(StoryStatus.VOTED);
		}
		return storyRepository.save(story);
	}

	public List<Story> getAllStories() {
		return storyRepository.findAll();
	}

	public List<Story> getStoriesBySessionId(Long sessionId) {
		return storyRepository.findBySessionId(sessionId);
	}

	public Optional<Story> getStoryById(Long id) {
		return storyRepository.findById(id);
	}

	public void updateStory(Story updatedStory) {
		storyRepository.save(updatedStory);
	}

	public void deleteStory(Long id) {
		storyRepository.deleteById(id);
	}

	public List<Story> getStoriesByStatus(StoryStatus status) {
		return storyRepository.findByStatus(status);
	}
}
