package com.gamification.game;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.domain.BadgeType;
import lombok.Value;

import java.util.List;

public interface GameService {
    /**
     * Process a new attempt for a given user
     * @param challenge the challenge data with the users, factors etc
     * @return a {@link GameResult} object containing the new score and badge obtained
     */

    GameResult newAttemptForUser(ChallengeSolvedDto challenge);

    @Value
    class GameResult{
        int score;
        List<BadgeType> badges;
    }
}
