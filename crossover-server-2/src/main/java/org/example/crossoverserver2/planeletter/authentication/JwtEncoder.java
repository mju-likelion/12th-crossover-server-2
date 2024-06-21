package org.example.crossoverserver2.planeletter.authentication;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JwtEncoder {
    public static String encodeJwtBearerToken(String token){
        String BEARER = "Bearer ";
        return URLEncoder.encode(BEARER + token, StandardCharsets.UTF_8).replaceAll("\\+","%20");
    }

    public static String decodeJwtBearerToken(String value){
        return URLDecoder.decode(value,StandardCharsets.UTF_8).substring(7);
    }
}
