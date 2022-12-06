package com.gamification.game;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.badgeprocessors.BadgeProcessor;
import com.gamification.game.domain.BadgeCard;
import com.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.gamification.game.domain.BadgeType.FIRST_WON;
import static com.gamification.game.domain.BadgeType.SILVER;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    private GameServiceImpl gameService;
    @Mock
    private  ScoreRepository scoreRepository;
    @Mock
    private  BadgeRepository badgeRepository;

    @Mock
    private BadgeProcessor badgeProcessor;
    private ChallengeSolvedDto challengeSolvedDto;

    @BeforeEach
    void setup(){
        gameService = new GameServiceImpl(scoreRepository,
                badgeRepository, List.of(badgeProcessor));
    }



    @Test
    void checkForWrongAttemptTest(){
        challengeSolvedDto = new ChallengeSolvedDto(1L, false, 11, 11, 2L, "john_doe");

        GameService.GameResult result = gameService.newAttemptForUser(challengeSolvedDto);

        then(result.getScore()).isEqualTo(0);
        then(result.getBadges()).isEmpty();
    }

    @Test
    void checkForCorrectAttemptTest(){
        long userId = 1L, attemptId = 10L;
        challengeSolvedDto = new ChallengeSolvedDto(attemptId, true, 11, 11, userId, "john_doe");
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);

        given(scoreRepository.getTotalScoreForUser(userId)).willReturn(Optional.of(10));
        given(scoreRepository.findByUserIdOrderByScoreTimeStampDesc(userId)).willReturn(List.of(scoreCard));
        given(badgeRepository.findByUserIdOrderByBadgeTimestamp(userId)).willReturn(List.of(new BadgeCard(userId, FIRST_WON)));
        given(badgeProcessor.processForOptionalBadge(10, List.of(scoreCard), challengeSolvedDto)).willReturn(Optional.of(SILVER));
        given(badgeProcessor.badgeType()).willReturn(SILVER);


        GameService.GameResult result = gameService.newAttemptForUser(challengeSolvedDto);

        then(result).isEqualTo(new GameService.GameResult(
                10, List.of(SILVER)
        ));

        verify(scoreRepository).save(scoreCard);
        verify(badgeRepository).saveAll(List.of(new BadgeCard(userId, SILVER)));
    }
}