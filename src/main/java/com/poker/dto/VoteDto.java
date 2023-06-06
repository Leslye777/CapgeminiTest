package com.poker.dto;


public class VoteDto {
	
    private Long userId;
    private Long storyId;
    private Integer voteOption;

    public VoteDto() {
    
    }

    public VoteDto(Long userId, Long storyId, Integer voteOption) {
		super();
		this.userId = userId;
		this.storyId = storyId;
		this.voteOption = voteOption;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

	public Integer getVoteOption() {
		return voteOption;
	}

	public void setVoteOption(Integer voteOption) {
		this.voteOption = voteOption;
	}   

}
