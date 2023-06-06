package com.poker.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poker.entities.Story;
import com.poker.enums.StoryStatus;
import com.poker.services.StoryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/stories")
public class StoryResource {

	@Autowired
    private StoryService storyService;
	

    @PostMapping("/{sessionId}")
    public ResponseEntity<Story> createStory(@PathVariable Long sessionId, @RequestBody Story story) {
      
    	Story createdStory = storyService.createStory(sessionId, story);
        
    	if(createdStory==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	}
    	return ResponseEntity.status(HttpStatus.CREATED).body(createdStory);    		        
    }

    @GetMapping
    public ResponseEntity<List<Story>> getAllStories() {
        List<Story> stories = storyService.getAllStories();
        return ResponseEntity.ok(stories);
    }
    
    @GetMapping("/voteStatus/{id}")
    public ResponseEntity<Story> changeVoteStatus(@PathVariable Long id) {
    	Story story = storyService.changeStateStory(id);
    	return ResponseEntity.ok(story);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable Long id) {
        Optional<Story> storyOptional = storyService.getStoryById(id);
        if (storyOptional.isPresent()) {
            Story story = storyOptional.get();
            return ResponseEntity.ok(story);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<Story>> getStoriesBySessionId(@PathVariable Long sessionId) {
        List<Story> stories = storyService.getStoriesBySessionId(sessionId);
        return ResponseEntity.ok(stories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStory(@PathVariable Long id, @RequestBody Story updatedStory) {
        Optional<Story> storyOptional = storyService.getStoryById(id);
        if (storyOptional.isPresent()) {
            Story story = storyOptional.get();
            story.setDescription(updatedStory.getDescription());
            story.setStatus(updatedStory.getStatus());
            storyService.updateStory(story);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Story>> getStoriesByStatus(@PathVariable StoryStatus status) {
        List<Story> stories = storyService.getStoriesByStatus(status);
        return ResponseEntity.ok(stories);
    }
}
