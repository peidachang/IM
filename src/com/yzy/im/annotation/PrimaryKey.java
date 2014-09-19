package com.yzy.im.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��ݿ���Ӧ�����ע�⣬��������Զ�Ӧ���������
 * com.universal.framwork.annotation.PrimaryKey
 * @author yuanzeyao <br/>
 * create at 2014��5��30�� ����5:20:28
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey
{
  /**
   * ���������
   * @return
   */
  public String name() default "";
  
  /**
   * ���Ĭ��ֵ
   * @return
   */
  public String defaultValue() default "";
  
  /**
   * �Ƿ��Զ���
   * @return
   */
  public boolean autoIncrement() default false;
}
