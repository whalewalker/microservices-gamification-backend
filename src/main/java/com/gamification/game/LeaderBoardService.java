package com.gamification.game;

import com.gamification.game.domain.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {
    /**
     * @return the current board ranked from high to low score
     */
    List<LeaderBoardRow> getCurrentLeaderBoard();
}
