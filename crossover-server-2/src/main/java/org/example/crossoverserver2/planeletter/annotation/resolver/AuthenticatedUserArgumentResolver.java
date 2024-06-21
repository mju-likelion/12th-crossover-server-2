package org.example.crossoverserver2.planeletter.annotation.resolver;

import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.annotation.AuthenticatedUser;
import org.example.crossoverserver2.planeletter.authentication.AuthenticationContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@AllArgsConstructor
public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthenticationContext context;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedUser.class);//AuthenticatedUser의 역할임을 명시
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return context.getPrincipal(); //추출해놓은 인증 유저 전달
    }
}
