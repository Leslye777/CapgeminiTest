package com.poker.resources;

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

import com.poker.dto.CreateSessionDto;
import com.poker.entities.Session;
import com.poker.entities.User;
import com.poker.services.SessionService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/sessions")
public class SessionResource {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody CreateSessionDto sessionRequest) {
        Session session = sessionService.createSession(sessionRequest);
        if(session != null) {
        	return new ResponseEntity<>(session, HttpStatus.CREATED);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long sessionId) {
    	Optional<Session> sessionOptional = sessionService.getSessionById(sessionId);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            return new ResponseEntity<>(session, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{sessionId}")
    public ResponseEntity<Session> joinSession(@RequestBody User user, @PathVariable Long sessionId) {
        Session session = sessionService.joinSession(sessionId, user);
        
        if(session != null) {
        	return new ResponseEntity<>(session, HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }        
    }

}
