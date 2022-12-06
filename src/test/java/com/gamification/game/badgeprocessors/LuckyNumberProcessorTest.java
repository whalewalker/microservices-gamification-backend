package com.gamification.game.badgeprocessors;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.domain.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LuckyNumberProcessorTest {
    private LuckyNumberProcessor badgeProcessor;

    @BeforeEach
    void setup() {
        badgeProcessor = new LuckyNumberProcessor();
    }

    @Test
    void shouldGiveBadgeIfFactorsIsLuckNumber() {
        Optional<BadgeType> badge = badgeProcessor.processForOptionalBadge(10, List.of(),
                new ChallengeSolvedDto(1L, true, 42, 13, 1L, "john"));
        assertThat(badge).contains(BadgeType.LUCKY_NUMBER);
    }

    @Test
    void shouldNotGiveBadgeIfFactorsIsNotLuckyNumber() {
        Optional<BadgeType> badge = badgeProcessor.processForOptionalBadge(200, List.of(),
                new ChallengeSolvedDto(1L, true, 12, 13, 1L, "john"));
        assertThat(badge).isEmpty();
    }
}