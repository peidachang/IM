package com.yzy.im.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SharePreferenceHelper
{
  private static final String TAG = "SharePreferenceHelper";
  private static final String SHARE_NAME="property";
  public static final String KEY_LOGIN_NAME="login_name";
  public static final String KEY_LOGIN_PWD="login_pwd";
  private static SharePreferenceHelper instance=null;
  
  private SharedPreferences sp;
  private SharedPreferences.Editor editor;

  public static SharePreferenceHelper getInstance(Context mContext)
  {
    if(instance==null)
    {
      instance=new SharePreferenceHelper(mContext);
    }
    return instance;
  }
  
  private SharePreferenceHelper(Context mContext)
  {
    if(mContext!=null)
    {
      sp=mContext.getApplicationContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
    }
  }
  
  public void setStringValue(String key,String value)
  {
    if(!TextUtils.isEmpty(key)&& !TextUtils.isEmpty(value))
    {
      editor=sp.edit();
      editor.putString(key, value);
      editor.commit();
      editor=null;
    }
  }
  
  public String getStringVaule(String key,String defaultValue)
  {
    String result=null;
    if(!TextUtils.isEmpty(key))
    {
      result=sp.getString(key, defaultValue);
    }
    return result;
  }
  
  

}
