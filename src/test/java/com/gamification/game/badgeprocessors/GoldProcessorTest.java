package com.gamification.game.badgeprocessors;

import com.gamification.game.domain.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class GoldProcessorTest {
    private GoldProcessor badgeProcessor;

    @BeforeEach
    void setup(){
        badgeProcessor = new GoldProcessor();
    }

    @Test
    void shouldGiveBadgeIfScoreOverThreshold(){
        Optional<BadgeType> badge = badgeProcessor.processForOptionalBadge(500, List.of(), null);
        assertThat(badge).contains(BadgeType.GOLD);
    }

    @Test
    void shouldNotGiveBadgeIfScoreBelowThreshold(){
        Optional<BadgeType> badge = badgeProcessor.processForOptionalBadge(200, List.of(), null);
        assertThat(badge).isEmpty();
    }
}