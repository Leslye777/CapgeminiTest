package com.poker.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "votes")
public class Vote implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User voters;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    private Integer option; 
    
    
    public Vote() {
    	
    }
    

	public Vote(Long id, User voters, Story story, Integer option) {
		super();
		this.id = id;
		this.voters = voters;
		this.story = story;
		this.option = option;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public User getVoters() {
		return voters;
	}


	public void setVoters(User voters) {
		this.voters = voters;
	}


	public Integer getOption() {
		return option;
	}


	public void setOption(Integer option) {
		this.option = option;
	}	
	
	
	
	
}
