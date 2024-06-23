package org.example.crossoverserver2.planeletter.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import static org.example.crossoverserver2.planeletter.constant.RegexPatterns.APPLICATION_PASSWORD_PATTERN;
import static org.example.crossoverserver2.planeletter.constant.RegexPatterns.APPLICATION_USERID_PATTERN;

@Getter
@AllArgsConstructor
public class LoginRequestData {

    @NotNull(message = "아이디를 입력해주세요.")
    @Pattern(regexp = APPLICATION_USERID_PATTERN, message = "아이디는 영문, 숫자를 모두 포함해야 합니다.")
    @Length(max = 9)
    private String id;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 13, message = "비밀번호는 8자 이상, 14자 미만으로 작성해야 합니다.")
    @Pattern(regexp = APPLICATION_PASSWORD_PATTERN, message ="비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다.")
    private String password;

}
