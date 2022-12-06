package com.gamification.game;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.badgeprocessors.BadgeProcessor;
import com.gamification.game.domain.BadgeCard;
import com.gamification.game.domain.BadgeType;
import com.gamification.game.domain.ScoreCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    //Spring inject all the @component beans in this list
    private final List<BadgeProcessor> badgeProcessors;


    @Override
    public GameResult newAttemptForUser(ChallengeSolvedDto challenge) {
        if (challenge.isCorrect()) {
            ScoreCard scoreCard = new ScoreCard(challenge.getUserId(), challenge.getAttemptId());
            scoreRepository.save(scoreCard);

            log.info(
                    "User {} stored {} points for attempt id {}",
                    challenge.getUserAlias(),
                    scoreCard.getScore(), challenge.getAttemptId()
            );

            List<BadgeCard> badgeCards = processForBadges(challenge);

            return new GameResult(scoreCard.getScore(),
                    badgeCards.stream().map(BadgeCard::getBadgeType)
                            .collect(Collectors.toList())
            );
        } else {
            log.info(
                    "Attempt id {} is not correct. User {} does not get score",
                    challenge.getAttemptId(), challenge.getUserAlias()
            );
            return new GameResult(0, List.of());
        }
    }


    /**
     * Checks the total score and the different score_cards obtained * to give new badges in case their conditions are met.
     */
    private List<BadgeCard> processForBadges(final ChallengeSolvedDto solvedChallenge) {

        Optional<Integer> optTotalScore = scoreRepository.
                getTotalScoreForUser(solvedChallenge.getUserId());
        if (optTotalScore.isEmpty()) return Collections.emptyList();
        int totalScore = optTotalScore.get();

        List<ScoreCard> scoreCardList = scoreRepository
                .findByUserIdOrderByScoreTimeStampDesc(solvedChallenge.
                        getUserId());
        Set<BadgeType> alreadyGotBadges = badgeRepository
                .findByUserIdOrderByBadgeTimestamp(solvedChallenge.
                        getUserId())
                .stream()
                .map(BadgeCard::getBadgeType)
                .collect(Collectors.toSet());

        // Calls the badge processors for badges that the user doesn't have yet
        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
                .filter(bp -> !alreadyGotBadges.contains(bp.badgeType()))
                .map(bp -> bp.processForOptionalBadge(totalScore, scoreCardList, solvedChallenge))
                .flatMap(Optional::stream) // returns an empty stream if empty // maps the optionals if present to new BadgeCards
                .map(badgeType -> new BadgeCard(solvedChallenge.getUserId(), badgeType))
                .collect(Collectors.toList());
        badgeRepository.saveAll(newBadgeCards);
        return newBadgeCards;
    }
}
