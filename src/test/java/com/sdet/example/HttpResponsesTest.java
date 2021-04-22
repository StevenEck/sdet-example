package com.sdet.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //using random port avoids conflicts
public class HttpResponsesTest {

    public static final String JSON_TIME_KEY = "instant";
    private static String URL_PREFIX = "http://localhost:";
    private static String URL_POSTFIX = "/";
    private static String EXPECTED_MESSAGE = "Welcome to the machine";

    @LocalServerPort
    private int port;

    @Autowired //controller is injected before the test methods are run.
    private TestRestTemplate restTemplate;



    @Test
    public void bodyShouldContainExpectedMessageViaHTTP() throws Exception {
        String response = this.restTemplate.getForObject(testURL(),
                String.class);

        assertThat(response).contains(EXPECTED_MESSAGE);
    }


    @Test
    public void responseShouldContainCorrectTimeStamp() throws Exception {
        Instant currentTimePlusLimit = Instant.now().plusMillis(500);
        Instant currentTime = Instant.now();
        String response = this.restTemplate.getForObject(testURL(),
                String.class);
        Instant timestamp = getTimeStampResponseFromJSON(response);

        assertThat(timestamp).isBetween(currentTime, currentTimePlusLimit);
    }


    private String testURL() {
        return URL_PREFIX + port + URL_POSTFIX;
    }

    private Instant getTimeStampResponseFromJSON(String response) {
        JsonObject jobjInstant = new Gson().fromJson(response, JsonObject.class);
        String responseTime = jobjInstant.get(JSON_TIME_KEY).getAsString();
        Instant instantTime = Instant.parse(responseTime);
        return instantTime;
    }

}

