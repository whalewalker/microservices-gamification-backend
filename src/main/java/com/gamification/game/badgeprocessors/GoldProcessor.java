package com.gamification.game.badgeprocessors;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.domain.BadgeType;
import com.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.gamification.game.domain.BadgeType.GOLD;
@Component
public class GoldProcessor implements BadgeProcessor{
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedDto solvedChallenge) {
        return currentScore > 400 ?
                Optional.of(GOLD) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return GOLD;
    }
}
