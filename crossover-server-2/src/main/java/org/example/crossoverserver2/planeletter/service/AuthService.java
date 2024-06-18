package org.example.crossoverserver2.planeletter.service;

import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.authentication.PasswordHashEncryption;
import org.example.crossoverserver2.planeletter.dto.request.auth.LoginRequestData;
import org.example.crossoverserver2.planeletter.dto.request.auth.SignUpRequestData;
import org.example.crossoverserver2.planeletter.dto.response.clause.ClauseListResponseData;
import org.example.crossoverserver2.planeletter.exception.AuthorizedException;
import org.example.crossoverserver2.planeletter.exception.ConflictException;
import org.example.crossoverserver2.planeletter.exception.ForbiddenException;
import org.example.crossoverserver2.planeletter.exception.NotFoundException;
import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;
import org.example.crossoverserver2.planeletter.model.Clause;
import org.example.crossoverserver2.planeletter.model.User;
import org.example.crossoverserver2.planeletter.model.UserClause;
import org.example.crossoverserver2.planeletter.repository.ClauseRepository;
import org.example.crossoverserver2.planeletter.repository.UserClauseRepository;
import org.example.crossoverserver2.planeletter.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClauseRepository clauseRepository;
    private final PasswordHashEncryption passwordHashEncryption;


    public ClauseListResponseData getClauses(){
        return ClauseListResponseData.clauseListResponseData(clauseRepository.findAll());
    }

    public void signUp(SignUpRequestData signUpRequestData){

        if(userRepository.existsByEmail(signUpRequestData.getEmail())){
            throw new ConflictException(ErrorCode.EMAIL_CONFLICT);
        }
        if (userRepository.existsByUserId(signUpRequestData.getId())){
            throw new ConflictException(ErrorCode.USERID_CONFLICT);
        }

        String salt = String.valueOf(LocalDateTime.now());
        String encryptPassword = passwordHashEncryption.encrypt(signUpRequestData.getPassword(),salt);

        User user = User.builder()
                .name(signUpRequestData.getName())
                .userId(signUpRequestData.getId())
                .email(signUpRequestData.getEmail())
                .password(encryptPassword)
                .salt(salt)
                .build();

        userRepository.save(user);
    }

    public void login(LoginRequestData loginRequestData){
        User user = userRepository.findByUserId(loginRequestData.getId())
                .orElseThrow(()-> new AuthorizedException(ErrorCode.USER_UNAUTHORIZED));
        if(!passwordHashEncryption.matches(loginRequestData.getPassword(), user.getSalt(), user.getPassword())){
            throw new AuthorizedException(ErrorCode.USER_UNAUTHORIZED);
        }

    }



}
