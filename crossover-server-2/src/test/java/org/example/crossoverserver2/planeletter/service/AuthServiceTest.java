package org.example.crossoverserver2.planeletter.service;

import jdk.jfr.Name;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.assertj.core.api.AbstractThrowableAssert;
import org.example.crossoverserver2.planeletter.authentication.PasswordHashEncryption;
import org.example.crossoverserver2.planeletter.dto.request.auth.LoginRequestData;
import org.example.crossoverserver2.planeletter.dto.request.auth.SignUpRequestData;
import org.example.crossoverserver2.planeletter.exception.ConflictException;
import org.example.crossoverserver2.planeletter.model.User;
import org.example.crossoverserver2.planeletter.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthServiceTest {

   @InjectMocks
   private AuthService authService;

   @Mock
   private UserRepository userRepository;
   @Mock
   private PasswordHashEncryption passwordHashEncryption;


   @Test
   @DisplayName("회원가입 테스트")
   void signUp() {
       //given
       SignUpRequestData requestData = new SignUpRequestData("test123","test1234!","TestName", "test@example.com" );

       //when
       authService.signUp(requestData);

       //then
       ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
       Mockito.verify(userRepository).save(userArgumentCaptor.capture());
       User savedUser = userArgumentCaptor.getValue();
       assertThat(savedUser.getUserId()).isEqualTo(requestData.getId());
   }

   @Test
   void 중복_회원가입_테스트() {
       //given
       SignUpRequestData requestData = new SignUpRequestData("test123","test1234!","TestName", "test@example.com" );
       SignUpRequestData dupIdRequestData = new SignUpRequestData("test123","test1234!","TestName", "test2@example.com" );
       SignUpRequestData dupEmailRequestData = new SignUpRequestData("test1234","test1234!","TestName", "test@example.com" );

       when(userRepository.existsByUserId(requestData.getId())).thenReturn(false);
       when(userRepository.existsByEmail(requestData.getEmail())).thenReturn(false);

       authService.signUp(requestData);

       // 첫 번째 회원가입한 유저 캡처
       ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
       Mockito.verify(userRepository).save(userArgumentCaptor.capture());
       User savedUser = userArgumentCaptor.getValue();


       //when & then
       //아이디 중복일 때
       when(userRepository.existsByUserId(dupIdRequestData.getId())).thenReturn(true);
       assertThrows(ConflictException.class, ()->authService.signUp(dupIdRequestData));

       //이메일 중복일 때
       when(userRepository.existsByEmail(dupEmailRequestData.getEmail())).thenReturn(true);
       assertThrows(ConflictException.class, ()->authService.signUp(dupEmailRequestData));

   }

   @Test
   void login() {
       //given
       User user = User.builder()
               .userId("test123")
               .password(passwordHashEncryption.encrypt("test123!","123"))
               .salt("123")
               .name("test")
               .email("test@test.com")
               .build();

       LoginRequestData loginRequestData = new LoginRequestData("test123", "test123!");

       //when
       when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));
       when(passwordHashEncryption.matches(loginRequestData.getPassword(),user.getSalt(),
               passwordHashEncryption.encrypt(user.getPassword(),user.getSalt()))).thenReturn(true);

       UUID userUuid = authService.login(loginRequestData);

       //then
       assertThat(userUuid).isEqualTo(user.getId());
   }
}
