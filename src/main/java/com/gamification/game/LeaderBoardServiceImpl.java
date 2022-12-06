package com.gamification.game;

import com.gamification.game.LeaderBoardService;
import com.gamification.game.domain.LeaderBoardRow;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
//        Get score only
        List<LeaderBoardRow> scoreOnly = scoreRepository.findFirst10();
//        Combine with badges
        return scoreOnly.stream().map(row -> {
            List<String> badges = badgeRepository.findByUserIdOrderByBadgeTimestamp(
                    row.getUserId()).stream()
                    .map(b -> b.getBadgeType().getDescription())
                    .collect(Collectors.toList());
            return row.withBadges(badges);
        }).collect(Collectors.toList());

    }
}
