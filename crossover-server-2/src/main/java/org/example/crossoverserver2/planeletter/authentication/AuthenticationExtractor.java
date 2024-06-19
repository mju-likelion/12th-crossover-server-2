package org.example.crossoverserver2.planeletter.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.exception.AuthorizedException;
import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;
import org.springframework.stereotype.Component;


public class AuthenticationExtractor {
    public static final String TOKEN_COOKIE_NAME = "AccessToken";

    public static String extract(final HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies!=null){
            for (Cookie c : cookies){
                if(TOKEN_COOKIE_NAME.equals(c.getName())){
                    return JwtEncoder.decodeJwtBearerToken(c.getValue());
                }
            }

        }
        throw new AuthorizedException(ErrorCode.TOKEN_NOT_FOUND);
    }

}
