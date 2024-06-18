package org.example.crossoverserver2.planeletter.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class LoginRequestData {

    @NotNull(message = "아이디를 입력해주세요.")
    private String id;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

}
