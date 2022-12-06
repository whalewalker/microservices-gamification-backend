package com.gamification.game;

import com.gamification.challenge.ChallengeSolvedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    void postAttempts(@RequestBody ChallengeSolvedDto dto){
        gameService.newAttemptForUser(dto);
    }
}
