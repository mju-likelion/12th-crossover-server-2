package org.example.crossoverserver2.planeletter.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.dto.request.auth.LoginRequestData;
import org.example.crossoverserver2.planeletter.dto.request.auth.SignUpRequestData;
import org.example.crossoverserver2.planeletter.dto.ResponseDto;
import org.example.crossoverserver2.planeletter.dto.response.clause.ClauseListResponseData;
import org.example.crossoverserver2.planeletter.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/sign-up")
    public ResponseEntity<ResponseDto<ClauseListResponseData>> loadSignUpPage(){
        ClauseListResponseData clauseListResponseData = authService.getClauses();
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"로그인 창 조회 성공",clauseListResponseData),HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Valid SignUpRequestData signUpRequestData){
        authService.signUp(signUpRequestData);
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequestData loginRequestData){
        authService.login(loginRequestData);
    }


}
