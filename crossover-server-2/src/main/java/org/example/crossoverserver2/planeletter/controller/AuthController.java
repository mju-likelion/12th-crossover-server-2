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
import org.example.crossoverserver2.planeletter.model.User;
import org.example.crossoverserver2.planeletter.service.AuthService;
import org.example.crossoverserver2.planeletter.service.ClauseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {


    private final AuthService authService;
    private final ClauseService clauseService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/sign-up")
    public ResponseEntity<ResponseDto<ClauseListResponseData>> loadSignUpPage(){
        ClauseListResponseData clauseListResponseData = authService.getClauses();
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"회원가입 페이지 조회 성공",clauseListResponseData),HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto<Void>> signUp(@RequestBody @Valid SignUpRequestData signUpRequestData){
        User user = authService.signUp(signUpRequestData);
        clauseService.essentialRegister(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "회원가입 성공"),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid LoginRequestData loginRequestData, HttpServletResponse response){
        UUID id = authService.login(loginRequestData);

        String accessToken = jwtTokenProvider.createToken(String.valueOf(id));
        ResponseCookie cookie = ResponseCookie.from("AccessToken", JwtEncoder.encodeJwtBearerToken(accessToken))
                .maxAge(Duration.ofSeconds(60 * 30))//30분
                .path("/")//모든 경로에서 접근가능
                .sameSite("None").httpOnly(true)//브라우저에서 쿠키에 접근 못하도록
                .secure(true)//https 사용 시에만 토큰 사용
                .domain(".likelion-crossover-team2.com")//해당 도메인 및 서브 도메인에서만 사용할 수 있도록 설정
                .build();

        log.info(cookie.toString());
        response.addHeader("Set-Cookie",cookie.toString());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "로그인 성공"),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(HttpServletResponse httpServletResponse){
        ResponseCookie cookie =ResponseCookie.from("AccessToken", null)
                .maxAge(0)
                .sameSite("None").httpOnly(true)//보안을 위해 설정
                .secure(true)
                .path("/")
                .build();
        httpServletResponse.addHeader("Set-Cookie", cookie.toString());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"로그아웃 성공"),HttpStatus.OK);
    }
}
