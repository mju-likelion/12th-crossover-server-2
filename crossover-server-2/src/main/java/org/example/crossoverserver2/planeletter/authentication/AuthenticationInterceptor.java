package org.example.crossoverserver2.planeletter.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.exception.AuthorizedException;
import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;
import org.example.crossoverserver2.planeletter.model.User;
import org.example.crossoverserver2.planeletter.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        String accessToken = AuthenticationExtractor.extract(request);
        UUID id = UUID.fromString(jwtTokenProvider.getPayload(accessToken)); // accessToken에서 payload추출
        User user = findUserByUserId(id);
        authenticationContext.setPrincipal(user);
        return true; //true여야 컨트롤러로 전달됨
    }

    private User findUserByUserId(UUID id){
        return userRepository.findById(id).orElseThrow(()-> new AuthorizedException(ErrorCode.TOKEN_INVALID));
    }


}
