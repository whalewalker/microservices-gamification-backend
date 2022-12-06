package com.gamification.game.badgeprocessors;

import com.gamification.game.domain.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BronzeProcessorTest {
    private BronzeProcessor badgeProcessor;

    @BeforeEach
    void setup(){
        badgeProcessor = new BronzeProcessor();
    }

    @Test
    void shouldGiveBadgeIfScoreOverThreshold(){
        Optional<BadgeType> badgeType = badgeProcessor
                .processForOptionalBadge(60, List.of(), null);
        assertThat(badgeType).contains(BadgeType.BRONZE);
    }

    @Test
    void shouldNotGiveBadgeIfScoreBelowThreshold(){
        Optional<BadgeType> badgeType = badgeProcessor
                .processForOptionalBadge(40, List.of(), null);
        assertThat(badgeType).isEmpty();
    }
}