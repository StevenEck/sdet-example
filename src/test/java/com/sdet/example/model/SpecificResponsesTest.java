package com.sdet.example.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdet.example.controller.ApiController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApiController.class)
public class SpecificResponsesTest {

    @Autowired
    private MockMvc mockMvc; //tests layer below server start
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test200Response() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    void testExpectedTimeResponse() throws Exception {
        ApiResponse response = getApiResponse();

        Instant currentExpectedTime = Instant.now();
        assertThat(response.getInstant()).isStrictlyBetween(currentExpectedTime.minusMillis(500), currentExpectedTime.plusMillis(500));
    }

    @Test
    void testExpectedMessage() throws Exception {
        ApiResponse response = getApiResponse();
        assertThat(response.getMessage()).contains("Welcome to the machine");
    }

    private ApiResponse getApiResponse() throws Exception {
        MvcResult mockMvcResult = mockMvc.perform(get("/")).andReturn();
        return objectMapper.readValue(mockMvcResult.getResponse().getContentAsString(), ApiResponse.class);
    }



}
