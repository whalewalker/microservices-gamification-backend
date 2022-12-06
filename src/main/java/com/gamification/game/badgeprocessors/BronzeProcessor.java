package com.gamification.game.badgeprocessors;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.domain.BadgeType;
import com.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.gamification.game.domain.BadgeType.BRONZE;

@Component
public class BronzeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(
            int currentScore,
            List<ScoreCard> scoreCardList,
            ChallengeSolvedDto solvedChallenge
    ) {
       return currentScore > 50 ? Optional.of(BRONZE) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BRONZE;
    }
}
