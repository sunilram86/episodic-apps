package com.example.episodicshows.episodes;

import com.example.episodicshows.shows.Show;
import com.example.episodicshows.shows.ShowRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EpisodesControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    EpisodeRepository episodeRepository;

    @Autowired
    ShowRepository showRepository;


    @Test
    @Transactional
    @Rollback
    public void testpostEpisodes() throws Exception {

        Map<String, Object> payload = new HashMap<String, Object>() {
            {
                put("seasonNumber", 1);
                put("episodeNumber", 2);
            }
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload);

        Show s = new Show();
        s.setName("sunilsuper");
        this.showRepository.save(s);

        MockHttpServletRequestBuilder request=post("/shows/{id}/episodes",s.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.seasonNumber", is(1)))
                .andExpect(jsonPath("$.episodeNumber", is(2)))
                .andExpect(jsonPath("$.title", is("S1 E2")));
    }

    @Test
    @Transactional
    @Rollback
    public void testgetshows() throws Exception {

        Show s = new Show();
        s.setName("sunilsuper");
        this.showRepository.save(s);

        Episode e = new Episode();
        e.setEpisodeNumber(1);
        e.setSeasonNumber(2);
        e.setShowId(s.getId());

        episodeRepository.save(e);



        this.mvc.perform(get("/shows/{id}/episodes", s.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].episodeNumber", is (1)))
                .andExpect(jsonPath("$.[0].seasonNumber", is(2)));

    }
}

