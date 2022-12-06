package com.gamification.game;

import com.gamification.game.domain.BadgeType;
import com.gamification.game.domain.LeaderBoardRow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(LeaderBoardController.class)
class LeaderBoardControllerTest {

    @MockBean
    @Qualifier("leaderBoardServiceImpl") // Spring boot inject the bean with this name
    private LeaderBoardService leaderBoardService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<List<LeaderBoardRow>> listJacksonTester;

    @Test
    void getLeaderBoardTest() throws Exception {
        List<LeaderBoardRow> leaderBoardRow = List.of(new LeaderBoardRow(1L, 300L,
                List.of(BadgeType.LUCKY_NUMBER.getDescription())));


        given(leaderBoardService.getCurrentLeaderBoard())
                .willReturn(leaderBoardRow);

        MockHttpServletResponse response = mockMvc.perform(
                get("/leaders")).andReturn().getResponse();

        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString())
                .isEqualTo(listJacksonTester.write(leaderBoardRow).getJson());
    }
}