package org.example.crossoverserver2.planeletter.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crossoverserver2.planeletter.dto.request.auth.SignUpRequestData;
import org.example.crossoverserver2.planeletter.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build(); //테스트 전에 항상 mockMvc 생성
    }

    @Test
    void loadSignUpPage() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/auth/sign-up")
        );
        resultActions.andExpect(status().isOk());
    }

    @Test
    void signUp() throws Exception {
        SignUpRequestData requestData = new SignUpRequestData("test123","test1234!","TestName", "test@example.com" );

        doNothing().when(authService).signUp(any(SignUpRequestData.class));

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestData))
        );


        resultActions.andExpect(status().isCreated());
    }

    @Test
    void login() {
    }
}