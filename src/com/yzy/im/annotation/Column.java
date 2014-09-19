package com.yzy.im.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ����ݿ���е�ĳһ�к�ĳ������ĳһ���Թ�j��4
 * com.universal.framwork.annotation.Column
 * @author yuanzeyao <br/>
 * create at 2014��5��30�� ����4:51:28
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column
{
  public String name() default "";
  
  public String defaultValue() default "";
}
