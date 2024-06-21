package org.example.crossoverserver2.planeletter.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.example.crossoverserver2.planeletter.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(HttpServletResponse httpServletResponse){
        ResponseCookie cookie =ResponseCookie.from("AccessToken", null)
                .maxAge(0)
                .build();

        httpServletResponse.addHeader("Set-Cookie", cookie.toString());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"로그아웃 성공"),HttpStatus.OK);
    }
}
