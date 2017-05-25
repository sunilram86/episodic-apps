package com.example.episodicevents;

import com.fasterxml.jackson.core.JsonParser;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.*;

import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventsControllerWebTest {

    @Autowired
    TestRestTemplate template;

    @Test
    public void testcreateEvent() throws Exception {
        String json = getJSON("/data.json");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "/";

        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, requestEntity, String.class);

        MatcherAssert.assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void testgetEvent() throws Exception {

        String url = "/recent";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.GET, entity, String.class);

        MatcherAssert.assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        JsonArray eventList= new com.google.gson.JsonParser().parse(responseEntity.getBody()).getAsJsonArray();

      //  MatcherAssert.assertThat(responseEntity.getBody(), equalTo(asList(String.class)));

       assertThat(eventList.get(0).getAsJsonObject().get("type").getAsString(), equalTo("play"));

    }



    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }

}