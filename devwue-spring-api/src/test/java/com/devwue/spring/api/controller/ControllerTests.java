package com.devwue.spring.api.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private static Logger log = LoggerFactory.getLogger(ControllerTests.class);

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void authorized() throws Exception {
        mockMvc.perform(get("/user/authenticate").with(httpBasic("test","test")))
                .andExpect(authenticated().withRoles("ADMIN"));
    }

    @Test
    @WithUserDetails("test")
    public void unauthorized() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/authenticate").with(httpBasic("test", "test")))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();

        printDebug("unauthorized", result);
    }

    private void printDebug(String method, MvcResult result) {
        MockHttpServletResponse response = result.getResponse();

        log.debug("[{}] status: {},location: {}",method, response.getStatus(), response.getHeader("Location"));
    }
}
