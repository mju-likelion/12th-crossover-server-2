package org.example.crossoverserver2.planeletter.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class PasswordHashEncryption {

    private static final String PBKDF2_WITH_SHA1 = "PBKDF2WithHmacSHA1";

    private final int iterationCount;// 반복 횟수(암호화 강도)
    private final int keyLength; // 키 길이
    private final String salt;  // 랜덤 문자열 salt 값

    public PasswordHashEncryption (@Value("${encryption.pbkdf2.iteration-count}")final int iterationCount,
                                   @Value("${encryption.pbkdf2.key-length}")final int keyLength,
                                   @Value("${encryption.pbkdf2.salt}")final String salt){
        this.iterationCount = iterationCount;
        this.keyLength = keyLength;
        this.salt = salt;
    }

    public String encrypt(final String plainPassword){
        try {
            KeySpec keySpec = new PBEKeySpec(plainPassword.toCharArray(), salt.getBytes(), iterationCount, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2_WITH_SHA1);
            byte[] encodedPassword = keyFactory
                    .generateSecret(keySpec)
                    .getEncoded();
            return Base64.getEncoder()
                    .withoutPadding()
                    .encodeToString(encodedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("알 수 없는 이유로 회원가입에 실패했습니다.");
        }

    }

    public boolean matches(final String plainPassword, final String hashedPassword) {
        return encrypt(plainPassword).equals(hashedPassword);
    }

}
