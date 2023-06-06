package com.poker.entities;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String deckType;
    private String inviteLink;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> activeUsers;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "session_id")
    private List<Story> stories;

    public Session() {

    }

    public Session(Long id, String title, String deckType, String inviteLink, List<User> activeUsers,
			List<Story> stories) {
		super();
		this.id = id;
		this.title = title;
		this.deckType = deckType;
		this.inviteLink = inviteLink;
		this.activeUsers = activeUsers;
		this.stories = stories;
	}



	// Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeckType() {
        return deckType;
    }

    public void setDeckType(String deckType) {
        this.deckType = deckType;
    }

    public String getInviteLink() {
        return inviteLink;
    }

    public void setInviteLink(String inviteLink) {
        this.inviteLink = inviteLink;
    }

    public List<User> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(List<User> activeUsers) {
        this.activeUsers = activeUsers;
    }

	public List<Story> getStories() {
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}
    
    
}
