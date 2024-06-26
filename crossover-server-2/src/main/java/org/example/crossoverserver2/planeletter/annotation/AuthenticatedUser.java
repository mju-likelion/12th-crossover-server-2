package org.example.crossoverserver2.planeletter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //파라미터에 사용할 어노테이션
@Retention(RetentionPolicy.RUNTIME) //런타임까지 유지
public @interface AuthenticatedUser {
}
