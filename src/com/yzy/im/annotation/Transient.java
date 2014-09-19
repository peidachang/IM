package com.yzy.im.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ˵����ֶ�Ϊ͸���ֶΣ�Ҳ���ǲ��ô�����ݿ���ֶ�
 * com.universal.framwork.annotation.Transient
 * @author yuanzeyao <br/>
 * create at 2014��5��30�� ����5:43:01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transient
{

}
