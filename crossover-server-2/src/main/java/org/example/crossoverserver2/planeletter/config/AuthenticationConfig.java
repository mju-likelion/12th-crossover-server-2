package org.example.crossoverserver2.planeletter.config;


import lombok.RequiredArgsConstructor;
import org.example.crossoverserver2.planeletter.annotation.resolver.AuthenticatedUserArgumentResolver;
import org.example.crossoverserver2.planeletter.authentication.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver;

    private final String[] excludePathPatterns = {"/auth/login", "/auth/sign-up", "/auth/logout"};

    @Override
    public void addInterceptors(final InterceptorRegistry registry) { //인터셉터 등록 및 동작 범위 설정
        registry.addInterceptor(authenticationInterceptor)
                .excludePathPatterns(excludePathPatterns)
                .addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) { //resolver 등록
        resolvers.add(authenticatedUserArgumentResolver);
    }
}
