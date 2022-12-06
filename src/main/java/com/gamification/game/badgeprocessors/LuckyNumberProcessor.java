package com.gamification.game.badgeprocessors;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.domain.BadgeType;
import com.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.gamification.game.domain.BadgeType.LUCKY_NUMBER;

@Component
public class LuckyNumberProcessor implements BadgeProcessor{
    private static final int LUCKY_FACTOR = 42;

    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedDto solvedChallenge) {
        return isLuckyNumber(solvedChallenge) ? Optional.of(LUCKY_NUMBER) : Optional.empty();
    }

    private boolean isLuckyNumber(ChallengeSolvedDto solvedChallenge) {
        return solvedChallenge.getFactorA() == LUCKY_FACTOR ||
                solvedChallenge.getFactorB() == LUCKY_FACTOR;
    }

    @Override
    public BadgeType badgeType() {
        return LUCKY_NUMBER;
    }
}
