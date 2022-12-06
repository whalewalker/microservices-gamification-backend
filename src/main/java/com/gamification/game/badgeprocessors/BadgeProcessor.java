package com.gamification.game.badgeprocessors;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.domain.BadgeCard;
import com.gamification.game.domain.BadgeType;
import com.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {
    /**
     * Processes some or all of the passed parameters and decides if the user
     * is entitled to a badge.
     *
     * @return a BadgeType if the user is entitled to this badge, otherwise empty */

    Optional<BadgeType>  processForOptionalBadge(
            int currentScore, List<ScoreCard> scoreCardList,
            ChallengeSolvedDto solvedChallenge
    );

    /**
     * @return the BadgeType object that this processor is handling.
     * You can use * it to filter processors according to your needs.
     */
    BadgeType badgeType();
}
