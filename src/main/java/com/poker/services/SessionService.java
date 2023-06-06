package com.poker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poker.dto.CreateSessionDto;
import com.poker.entities.Session;
import com.poker.entities.Story;
import com.poker.entities.User;
import com.poker.repositories.SessionRepository;

@Service
public class SessionService {

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private UserService userService;

	public Session createSession(CreateSessionDto requestSession) {
		Session session = new Session();
		List<User> activeUsers = new ArrayList<>();

		User user = userService.getUserById(requestSession.getUserId()).orElse(null);
		if (user == null) {
			return null;
		}
		activeUsers.add(user);

		session.setDeckType(requestSession.getDeckType());
		session.setTitle(requestSession.getTitle());
		session.setActiveUsers(activeUsers);

		session = sessionRepository.save(session);
		Long sessionId = session.getId();
		String inviteLink = "localhost:4200/session/" + sessionId;
		session.setInviteLink(inviteLink);
		 
		return sessionRepository.save(session);
	}

	public Optional<Session> getSessionById(Long sessionId) {
		Optional<Session> session = sessionRepository.findById(sessionId);
		return session;
	}

	public void deleteSession(Long sessionId) {
		sessionRepository.deleteById(sessionId);
	}

	public Session joinSession(Long sessionId, User user) {
    	Session session = sessionRepository.findById(sessionId).orElse(null);
    	if (session == null) {
             return null;
        }

    	user = userService.createUser(user);
    	
        session.getActiveUsers().add(user);
       
    	return  sessionRepository.save(session);
    }
	
	public Session addStory(Long sessionId, Story story) {
    	Session session = sessionRepository.findById(sessionId).orElse(null);
    	if (session == null) {
             return null;
        }
    	
    	session.getStories().add(story);
    	
    	
    	return  sessionRepository.save(session);
	}

}
