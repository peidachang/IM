package com.yzy.im.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
  /**
   * md5加密的工具方法
   * @throws UnsupportedEncodingException 
   */
  public static String encode(String basestring) {
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.reset();
      md.update(CommonUtil.urlEncode(basestring).getBytes());
      byte[] md5 = md.digest();
      StringBuilder sb=new StringBuilder();
      for (byte b : md5)
        sb.append(String.format("%02x", b & 0xff));
      
      return sb.toString();
    } catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    
    return null;
  }
}