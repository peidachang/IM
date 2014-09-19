package com.yzy.im.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��4������Դ��ע��
 * com.universal.framwork.inject.InjectResource
 * @author yuanzeyao <br/>
 * create at 2014��5��23�� ����11:03:46
 */

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectResource
{
  int id() default -1;
}
