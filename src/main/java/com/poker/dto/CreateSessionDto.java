package com.poker.dto;

public class CreateSessionDto {
	
    private Long userId;
    private String title;
    private String deckType;
    
    public CreateSessionDto() {
        
    }
    
    public CreateSessionDto(Long userId, String title, String deckType) {
        this.userId = userId;
        this.title = title;
        this.deckType = deckType;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
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
}
