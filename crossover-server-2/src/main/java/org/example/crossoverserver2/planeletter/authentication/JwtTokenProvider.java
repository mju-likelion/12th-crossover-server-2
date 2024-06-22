package org.example.crossoverserver2.planeletter.authentication;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.crossoverserver2.planeletter.exception.AuthorizedException;
import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey key; // secret key
    private final long validityInMillisecond; //유효 시간

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String key,
                            @Value("${security.jwt.token.expire-length}")final long validityInMillisecond){
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)); //바이트 배열로 변환
        this.validityInMillisecond = validityInMillisecond;
    }

    public String createToken(final String payload){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMillisecond);

        return Jwts.builder()
                .setSubject(payload)//payload(==userId)로 설정
                .setIssuedAt(now)//발급시간
                .setExpiration(expiration)//만료시간
                .signWith(key, SignatureAlgorithm.HS256)//암호화한 키와 HS256 서명 알고리즘을 사용하여 토큰에 서명
                .compact();//위 설정을 바탕으로 JWT 토큰 생성, 문자열로 반환
    }

    public String getPayload(final String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)//시크릿 키로 토큰의 서명을 검증
                    .build()// 설정 완료된 파서 생성
                    .parseClaimsJws(token)//만료 시간 검증, 토큰 파싱
                    .getBody()//파싱된 토큰에서 body추출
                    .getSubject();//body에서 subject 추출
        }catch (JwtException e){
            throw new AuthorizedException(ErrorCode.TOKEN_INVALID);
        }
    }
}
