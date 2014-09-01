package com.yzy.im.bean;

import java.io.Serializable;

import com.yzy.im.IMApplication;
import com.yzy.im.util.SharePreferenceHelper;

public class IMMessage implements Serializable
{
  private static final String TAG = "PushMessage";
  private String userid;
  private String channelid;
  private String nick;
  private int headid;
  private long time_samp;
  private String message;
  private String tag;
  
  public IMMessage(String message,String tag)
  {
    this.userid=SharePreferenceHelper.getInstance(IMApplication.getInstance()).getUserId();
    this.channelid=SharePreferenceHelper.getInstance(IMApplication.getInstance()).getChannelId();
    this.headid=SharePreferenceHelper.getInstance(IMApplication.getInstance()).getHeadId();
    this.nick=SharePreferenceHelper.getInstance(IMApplication.getInstance()).getNick();
    this.time_samp=System.currentTimeMillis();
    this.message=message;
    this.tag=tag;
  }

  public String getUserid()
  {
    return userid;
  }

  public void setUserid(String userid)
  {
    this.userid = userid;
  }

  public String getChannelid()
  {
    return channelid;
  }

  public void setChannelid(String channelid)
  {
    this.channelid = channelid;
  }

  public String getNick()
  {
    return nick;
  }

  public void setNick(String nick)
  {
    this.nick = nick;
  }

  public int getHeadid()
  {
    return headid;
  }

  public void setHeadid(int headid)
  {
    this.headid = headid;
  }

  public long getTime_samp()
  {
    return time_samp;
  }

  public void setTime_samp(long time_samp)
  {
    this.time_samp = time_samp;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public String getTag()
  {
    return tag;
  }

  public void setTag(String tag)
  {
    this.tag = tag;
  }
  
  @Override
  public String toString()
  {
    return "PushMessage [userid=" + userid + ", channelid=" + channelid
        + ", nick=" + nick + ", headid=" + headid + ", time_samp="
        + time_samp + ", message=" + message + ", tag=" + tag + "]";
  }
}
