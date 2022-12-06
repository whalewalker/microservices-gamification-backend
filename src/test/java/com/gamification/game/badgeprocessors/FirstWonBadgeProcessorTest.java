package com.gamification.game.badgeprocessors;

import com.gamification.game.domain.BadgeType;
import com.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FirstWonBadgeProcessorTest {
    private FirstWonBadgeProcessor badgeProcessor;

    @BeforeEach
    void setup(){
        badgeProcessor = new FirstWonBadgeProcessor();
    }

    @Test
    void shouldGiveBadgeIfFirstTime(){
       Optional<BadgeType> badge = badgeProcessor.processForOptionalBadge(10, List.of
                (new ScoreCard(1L, 1L)), null);

        assertThat(badge).contains(BadgeType.FIRST_WON);
    }

    @Test
    void shouldNotGiveBadgeIfNotFirstTime(){
        Optional<BadgeType> badge = badgeProcessor.processForOptionalBadge(10, List.of
                (new ScoreCard(1L, 1L), new ScoreCard(1L, 2L)), null);

        assertThat(badge).isEmpty();
    }
}