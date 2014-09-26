package com.yzy.im.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ����ݿ��еı��ĳһ�����j��4
 * com.universal.framwork.annotation.Table
 * @author yuanzeyao <br/>
 * create at 2014��5��30�� ����4:48:52
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table
{
  public String name() default "";
}
