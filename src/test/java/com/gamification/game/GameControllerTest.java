package com.gamification.game;

import com.gamification.challenge.ChallengeSolvedDto;
import com.gamification.game.domain.BadgeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
@AutoConfigureJsonTesters
class GameControllerTest {

    @MockBean
    @Qualifier("gameServiceImpl") // Spring boot mapped it with  this implementation
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ChallengeSolvedDto> jacksonTester;

    @Test
    void postValidAttemptTest() throws Exception {
        ChallengeSolvedDto dto = new ChallengeSolvedDto(
                1L,
                true,
                11, 11,
                1L, "john"
        );
        given(gameService.newAttemptForUser(eq(dto)))
                .willReturn(new GameService.GameResult(10, List.of(BadgeType.SILVER)));

        MockHttpServletResponse response = mockMvc.perform(
                post("/attempts").contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonTester.write(dto).getJson())
        ).andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}