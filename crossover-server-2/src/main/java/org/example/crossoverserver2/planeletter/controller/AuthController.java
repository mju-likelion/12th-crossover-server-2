package org.example.crossoverserver2.planeletter.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crossoverserver2.planeletter.authentication.JwtEncoder;
import org.example.crossoverserver2.planeletter.authentication.JwtTokenProvider;
import org.example.crossoverserver2.planeletter.dto.request.auth.LoginRequestData;
import org.example.crossoverserver2.planeletter.dto.request.auth.SignUpRequestData;
import org.example.crossoverserver2.planeletter.dto.ResponseDto;
import org.example.crossoverserver2.planeletter.dto.response.clause.ClauseListResponseData;
import org.example.crossoverserver2.planeletter.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/sign-up")
    public ResponseEntity<ResponseDto<ClauseListResponseData>> loadSignUpPage(){
        ClauseListResponseData clauseListResponseData = authService.getClauses();
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"회원가입 페이지 조회 성공",clauseListResponseData),HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto<Void>> signUp(@RequestBody @Valid SignUpRequestData signUpRequestData){
        authService.signUp(signUpRequestData);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "회원가입 성공"),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid LoginRequestData loginRequestData, HttpServletResponse response){
        UUID id = authService.login(loginRequestData);

        String accessToken = jwtTokenProvider.createToken(String.valueOf(id));
        ResponseCookie cookie = ResponseCookie.from("AccessToken", JwtEncoder.encodeJwtBearerToken(accessToken))
                .maxAge(Duration.ofMinutes(60 * 30))//30분
                .path("/")//모든 경로에서 접근가능
                .build();

        log.info(cookie.toString());
        response.addHeader("Set-Cookie",cookie.toString());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "로그인 성공"),HttpStatus.OK);
    }


}
