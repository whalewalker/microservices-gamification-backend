package com.gamification.game.badgeprocessors;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.domain.BadgeType;
import com.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.gamification.game.domain.BadgeType.FIRST_WON;

@Component
public class FirstWonBadgeProcessor implements BadgeProcessor{

    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedDto solvedChallenge) {
        return scoreCardList.size() == 1
                ? Optional.of(FIRST_WON) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return FIRST_WON;
    }
}
