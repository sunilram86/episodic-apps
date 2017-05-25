package com.example.episodicevents;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.theInstance;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class EventsControllerTest {

    @Autowired
    private  EventsRepository eventsRepository;

    @Autowired
    private  RabbitTemplate rabbitTemplate;

    @Autowired
    MockMvc mvc;

    @Before
    public void clearDb() {
        this.eventsRepository.deleteAll();
    }

    @Test
    public void testcreateEvent_play() throws Exception {
        String json = getJSON("/data.json");

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(this.eventsRepository.count(), notNullValue());

        assertThat(this.rabbitTemplate.getUUID(), notNullValue());

    }


    @Test
    public void testcreateEvent_pause() throws Exception {
        String json = getJSON("/data_pause.json");

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(this.eventsRepository.count(), notNullValue());
    }

    @Test
    public void testcreateEvent_fastForward() throws Exception {
        String json = getJSON("/data_fastForward.json");

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(this.eventsRepository.count(), notNullValue());
    }

    @Test
    public void testcreateEvent_progress() throws Exception {
        String json = getJSON("/data_progress.json");

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(this.eventsRepository.count(), notNullValue());

    }

    @Test
    public void testcreateEvent_scrub() throws Exception {
        String json = getJSON("/data_scrub.json");

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(this.eventsRepository.count(), notNullValue());
    }

    @Test
    public void testGetEvents() throws Exception {


        Play p = new Play();
        p.setCreatedAt("2017-11-08T15:59:13.009");
        p.setShowId(987);
        p.setUserId(52);
        p.setEpisodeId(456);
        p.setType("play");

        Data d = new Data();
        d.setEndOffset(1);
        d.setSpeed((float) 60.5);

        p.setData(d);

        this.eventsRepository.save(p);

        this.mvc.perform(get("/recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].type", is("play")))
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].userId", is(52)))
                .andExpect(jsonPath("$.[0].showId", is(987)))
                .andExpect(jsonPath("$.[0].createdAt", is("2017-11-08T15:59:13.009")));


    }


    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }

}