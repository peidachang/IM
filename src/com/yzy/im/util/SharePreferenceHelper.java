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
  
  public static final String KEY_USER_ID="userId";
  public static final String KEY_CHANNEL_ID="ChannelId";
  public static final String KEY_NICK="nick";
  public static final String KEY_HEAD_ID="headId";
  public static final String KEY_APP_ID="appid";
  
  public static final String KEY_TALK_USER_ID="takluserId";
  
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
  
  private void setStringValue(String key,String value)
  {
    if(!TextUtils.isEmpty(key))
    {
      editor=sp.edit();
      editor.putString(key, value);
      editor.commit();
      editor=null;
    }
  }
  
  private String getStringVaule(String key,String defaultValue)
  {
    String result=null;
    if(!TextUtils.isEmpty(key))
    {
      result=sp.getString(key, defaultValue);
    }
    return result;
  }
  
  private void setIntValue(String key,int value)
  {
    if(!TextUtils.isEmpty(key))
    {
      editor=sp.edit();
      editor.putInt(key, value);
      editor.commit();
      editor=null;
    }
  }
  
  private int getIntVaule(String key,int defaultValue)
  {
    int result=-1;
    if(!TextUtils.isEmpty(key))
    {
      result=sp.getInt(key, defaultValue);
    }
    return result;
  }
  
  
  
  public String getUserId()
  {
    return getStringVaule(KEY_USER_ID, "");
  }
  
  public void setUserId(String value)
  {
    setStringValue(KEY_USER_ID, value);
  }
  
  public String getChannelId()
  {
    return getStringVaule(KEY_CHANNEL_ID, "");
  }
  
  public void setChannelId(String value)
  {
    setStringValue(KEY_CHANNEL_ID, value);
  }
  
  public String getNick()
  {
    return getStringVaule(KEY_NICK, "");
  }
  
  public void setNick(String value)
  {
    setStringValue(KEY_NICK, value);
  }
  
  public int getHeadId()
  {
    return getIntVaule(KEY_HEAD_ID, -1);
  }
  
  public void setHeadId(int headId)
  {
    setIntValue(KEY_HEAD_ID, headId);
  }
  public String getAppId()
  {
    return getStringVaule(KEY_APP_ID, "");
  }
  public void setAppId(String appId)
  {
    setStringValue(KEY_APP_ID, appId);
  }
  
  
  public String getTalkUserId()
  {
    return getStringVaule(KEY_TALK_USER_ID, null);
  }
  
  public void setTalkUserId(String talkUserId)
  {
    setStringValue(KEY_TALK_USER_ID, talkUserId);
  }
  

  
  
  
  

}
