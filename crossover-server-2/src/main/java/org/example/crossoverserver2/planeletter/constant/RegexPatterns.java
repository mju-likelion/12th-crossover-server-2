package org.example.crossoverserver2.planeletter.constant;

public class RegexPatterns {

    public static final String APPLICATION_USERID_PATTERN = "^(?=.*[A-Za-z])(?=.*[\\d])[A-Za-z\\d]{1,9}$";//영문자 1개 이상, 숫자 1개 이상 포함한 9자리 이하 문자열
    public static final String APPLICATION_PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*_\\-+='|\\\\(){}\\[\\]:;'\"<>,.?/]).{8,13}$"; //영문자, 숫자, 특수문자를 포함하는 8~13자리 문자열
    public static final String APPLICATION_USERNAME_PATTERN = "^[가-힣]{2,9}$";//2~10자 미만의 한글로 된 문자열

}
