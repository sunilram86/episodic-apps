package com.example.episodicshows.viewings;

import com.example.episodicshows.episodes.Episode;
import com.example.episodicshows.episodes.EpisodeRepository;
import com.example.episodicshows.shows.Show;
import com.example.episodicshows.users.*;
import com.example.episodicshows.shows.ShowRepository;
import com.example.episodicshows.users.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ViewingsControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ViewingRepository viewingRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    EpisodeRepository episodeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    public void testPatchViewings_forCreate() throws Exception {

        User u = new User();
        u.setEmail("suniWWWl.g@gmail.com");
        u = this.userRepository.save(u);

        Show s = new Show();
        s.setName("sunilsuper6");
        s = this.showRepository.save(s);

        Episode e = new Episode();
        e.setShowId(s.getId());
        e.setSeasonNumber(2);
        e.setEpisodeNumber(1);
        this.episodeRepository.save(e);



        Map<String, Object> payload = new HashMap<String, Object>() {
            {
                put("episodeId", e.getId());
                put("updatedAt", "2017-05-04T11:45:34.9182");
                put("timecode", 79);
            }
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = patch("/users/{id}/viewings", u.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        List<Viewing> v = viewingRepository.findByUserIdOrderByUpdatedAtDesc(u.getId());

        assertThat(v.get(0).getShowId(), notNullValue());

    }


    @Test
    @Transactional
    @Rollback
    public void testPatchViewings_forUpdate() throws Exception {

        Show s = new Show();
        s.setName("sunilsuper6");
        this.showRepository.save(s);

        Episode e = new Episode();
        e.setShowId(s.getId());
        e.setSeasonNumber(2);
        e.setEpisodeNumber(1);
        this.episodeRepository.save(e);

        User u = new User();
        u.setEmail("suniWWWl.g@gmail.com");
        this.userRepository.save(u);

        String testDate = "2017-05-04 05:45:43 ";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        Date date = formatter.parse(testDate);

        Viewing v = new Viewing();
        v.setUpdatedAt(date);
        v.setShowId(s.getId());
        v.setEpisodeId(e.getId());
        v.setUserId(u.getId());
        v.setTimecode(80);
        this.viewingRepository.save(v);

        Map<String, Object> payload = new HashMap<String, Object>() {
            {
                put("episodeId", e.getId());
                put("updatedAt", "2017-05-04T11:45:34.9182");
                put("timecode", 79);
            }
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = patch("/users/{id}/viewings", u.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        List<Viewing> v1 = viewingRepository.findByUserIdOrderByUpdatedAtDesc(u.getId());

        assertThat(v1.get(0).getShowId(), notNullValue());

    }


    @Test
    @Transactional
    @Rollback
    public void testgetViewings() throws Exception {

        Show s = new Show();
        s.setName("sunilsuper6");
        this.showRepository.save(s);

        Episode e = new Episode();
        e.setShowId(s.getId());
        e.setSeasonNumber(2);
        e.setEpisodeNumber(1);
        this.episodeRepository.save(e);

        User u = new User();
        u.setEmail("suniWWWl.g@gmail.com");
        this.userRepository.save(u);

        String testDate = "2017-05-04 05:45:43 ";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        Date date = formatter.parse(testDate);

        Viewing v = new Viewing();
        v.setUpdatedAt(date);
        v.setShowId(s.getId());
        v.setEpisodeId(e.getId());
        v.setUserId(u.getId());
        v.setTimecode(65);
        this.viewingRepository.save(v);


        this.mvc.perform(get("/users/{id}/recently-watched", u.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].show.id", notNullValue()))
                .andExpect(jsonPath("$.[0].show.name",is("sunilsuper6")))
                .andExpect(jsonPath("$.[0].episode.episodeNumber", is (1)))
                .andExpect(jsonPath("$.[0].episode.title", is("S2 E1")))
                .andExpect(jsonPath("$.[0].timecode", is(65)));

        assertThat(viewingRepository.count(), is(1L));


    }

}