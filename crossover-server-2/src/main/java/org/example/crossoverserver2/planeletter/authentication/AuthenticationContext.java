package org.example.crossoverserver2.planeletter.authentication;

import lombok.Getter;
import lombok.Setter;
import org.example.crossoverserver2.planeletter.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
@Getter
@Setter
public class AuthenticationContext {
    private User principal; //인증된 유저

}
