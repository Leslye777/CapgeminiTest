package com.poker.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.poker.entities.Story;
import com.poker.entities.User;
import com.poker.entities.Vote;
import com.poker.enums.StoryStatus;
import com.poker.repositories.SessionRepository;
import com.poker.repositories.StoryRepository;
import com.poker.repositories.UserRepository;
import com.poker.repositories.VoteRepository;

public class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private VoteService voteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVoteForStory() {
        Long userId = 1L;
        Long storyId = 1L;
        Integer voteOption = 1;

        User user = new User();
        user.setId(userId);

        Story story = new Story();
        story.setId(storyId);
        story.setStatus(StoryStatus.VOTING);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story));
        Mockito.when(storyRepository.findSessionIdByStoryId(storyId)).thenReturn(1L);
        Mockito.when(sessionRepository.existsBySessionIdAndUserId(1L, userId)).thenReturn(true);
        Mockito.when(voteRepository.existsByVotersIdAndStoryId(userId, storyId)).thenReturn(false);

        voteService.voteForStory(userId, storyId, voteOption);

        Mockito.verify(voteRepository, Mockito.times(1)).save(Mockito.any(Vote.class));
        Mockito.verify(storyRepository, Mockito.times(1)).save(story);
    }

    @Test
    public void testVoteForStory_InvalidUser() {
        Long userId = 1L;
        Long storyId = 1L;
        Integer voteOption = 1;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> voteService.voteForStory(userId, storyId, voteOption));
    }

    @Test
    public void testVoteForStory_InvalidStory() {
        Long userId = 1L;
        Long storyId = 1L;
        Integer voteOption = 1;

        User user = new User();
        user.setId(userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> voteService.voteForStory(userId, storyId, voteOption));
    }

    @Test
    public void testVoteForStory_NotActiveUser() {
        Long userId = 1L;
        Long storyId = 1L;
        Integer voteOption = 1;

        User user = new User();
        user.setId(userId);

        Story story = new Story();
        story.setId(storyId);
        story.setStatus(StoryStatus.VOTING);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story));
        Mockito.when(storyRepository.findSessionIdByStoryId(storyId)).thenReturn(1L);
        Mockito.when(sessionRepository.existsBySessionIdAndUserId(1L, userId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> voteService.voteForStory(userId, storyId, voteOption));
    }

    @Test
    public void testVoteForStory_UserAlreadyVoted() {
        Long userId = 1L;
        Long storyId = 1L;
        Integer voteOption = 1;

        User user = new User();
        user.setId(userId);

        Story story = new Story();
        story.setId(storyId);
        story.setStatus(StoryStatus.VOTING);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story));
        Mockito.when(storyRepository.findSessionIdByStoryId(storyId)).thenReturn(1L);
        Mockito.when(sessionRepository.existsBySessionIdAndUserId(1L, userId)).thenReturn(true);
        Mockito.when(voteRepository.existsByVotersIdAndStoryId(userId, storyId)).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> voteService.voteForStory(userId, storyId, voteOption));
    }

    @Test
    public void testVoteForStory_StoryNotInVotingStatus() {
        Long userId = 1L;
        Long storyId = 1L;
        Integer voteOption = 1;

        User user = new User();
        user.setId(userId);

        Story story = new Story();
        story.setId(storyId);
        story.setStatus(StoryStatus.PENDING);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story));

        assertThrows(IllegalArgumentException.class,
                () -> voteService.voteForStory(userId, storyId, voteOption));
    }


    @Test
    public void testCloseVotes() {
    	Long voteId = 1L;

    	Vote vote = Mockito.mock(Vote.class); 
    	vote.setId(voteId);

        Story story = new Story();
        story.setId(1L);
        story.setStatus(StoryStatus.VOTING);

        Mockito.when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));
        Mockito.when(vote.getStory()).thenReturn(story);

        voteService.closeVotes(voteId);

        Mockito.verify(storyRepository, Mockito.times(1)).save(story);
    }

    @Test
    public void testCloseVotes_InvalidVote() {
        Long voteId = 1L;

        Mockito.when(voteRepository.findById(voteId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> voteService.closeVotes(voteId));
    }
    @Test
    public void testCloseVotes_StoryNotInVotingStatus() {
        Long voteId = 1L;

        Vote vote = Mockito.mock(Vote.class); 
        vote.setId(voteId);

        Story story = new Story();
        story.setId(1L);
        story.setStatus(StoryStatus.VOTED);

        Mockito.when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));
        Mockito.when(vote.getStory()).thenReturn(story);

        assertThrows(IllegalArgumentException.class, () -> voteService.closeVotes(voteId));
    }



    @Test
    public void testVotesByStory() {
        Long storyId = 1L;

        Vote vote1 = new Vote();
        vote1.setId(1L);

        Vote vote2 = new Vote();
        vote2.setId(2L);

        List<Vote> votes = Arrays.asList(vote1, vote2);

        Mockito.when(voteRepository.findByStoryId(storyId)).thenReturn(Optional.of(votes));

        List<Vote> result = voteService.votesByStory(storyId);

        assertEquals(votes, result);
    }

    @Test
    public void testVotesByStory_InvalidStory() {
        Long storyId = 1L;

        Mockito.when(voteRepository.findByStoryId(storyId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> voteService.votesByStory(storyId));
    }
}
