package com.yzy.im.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.Properties;

import android.text.TextUtils;

public class IMConfiguration
{
  private static final String TAG = "IMConfiguration";
  private static Properties defaultProperty;
  
  static
  {
    defaultProperty = new Properties();
  }
  
  public static boolean loadProperties(String path)
  {
    try
    {
      File file = new File(path);
      if (file.exists() && file.isFile())
      {
        defaultProperty.load(new FileInputStream(file));
        return true;
      }
    }
    catch (Exception ignore)
    {
    }
    return false;
  }
  
  public static boolean loadProperties(InputStream is)
  {
    try
    {
      defaultProperty.load(is);
      return true;
    }
    catch (Exception ignore)
    {
    }
    return false;
  }
  
  public static void setProperty(String vKey, String vValue)
  {
    defaultProperty.setProperty(vKey, vValue);
  }
  
  public static boolean getBoolean(String name, boolean defaultValue)
  {
    String value = getProperty(name);
    try
    {
      if (!TextUtils.isEmpty(value))
      {

        return Boolean.valueOf(value);
      }
      else
      {

        return defaultValue;
      }
    }
    catch (Exception e)
    {

      return defaultValue;
    }
  }

  /**
   * int 数据
   * 
   * @param name
   * @param fallbackValue
   * @return
   */
  public static int getIntProperty(String name, int fallbackValue)
  {
    String value = getProperty(name);
    try
    {
      if (!TextUtils.isEmpty(value))
      {

        return Integer.parseInt(value);
      }
      else
      {

        return fallbackValue;
      }
    }
    catch (Exception nfe)
    {

      return fallbackValue;
    }
  }

  /**
   * long 数据
   * 
   * @param name
   * @return
   */
  public static long getLongProperty(String name)
  {
    String value = getProperty(name);
    try
    {
      return Long.parseLong(value);
    }
    catch (NumberFormatException nfe)
    {
      return -1;
    }
  }

  /**
   * String 数据
   * 
   * @param name
   * @return
   */
  public static String getProperty(String name)
  {
    return getProperty(name, null);
  }

  public static String getProperty(String name, String fallbackValue)
  {
    String value = null;
    try
    {
      value = defaultProperty.getProperty(name);
    }
    catch (AccessControlException ace)
    {
      value = fallbackValue;
    }
    return replace(value);
  }

  private static String replace(String value)
  {
    if (null == value)
    {
      return value;
    }
    String newValue = value;
    int openBrace = 0;
    if (-1 != (openBrace = value.indexOf("{", openBrace)))
    {
      int closeBrace = value.indexOf("}", openBrace);
      if (closeBrace > (openBrace + 1))
      {
        String name = value.substring(openBrace + 1, closeBrace);
        if (name.length() > 0)
        {
          newValue = value.substring(0, openBrace) + getProperty(name) + value.substring(closeBrace + 1);

        }
      }
    }
    
    if (newValue.equals(value))
    {
      return value;
    }
    else
    {
      return replace(newValue);
    }
  }
}
