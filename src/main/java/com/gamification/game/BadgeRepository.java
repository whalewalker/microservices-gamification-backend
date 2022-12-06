package com.gamification.game;

import com.gamification.game.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeCard, Long> {
    /**
     * Retrives all BadgeCards for a given user
     *
     * @param userId the id of the user to look for BadgeCards
     * @return the list of BadgeCards, order by the most recent first.
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestamp(Long userId);
}
