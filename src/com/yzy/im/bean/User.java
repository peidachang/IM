package com.yzy.im.bean;

import com.yzy.im.R;

public class User
{
  private static final String TAG = "User";
  
  private String userId;
  private String channelId;
  private String nick;
  private String group;
  private int headId;
  
  
  public User(String userId,String channelId,String nick)
  {
    this(userId,channelId,nick,"0",R.drawable.h0);
  }
  public User(String userId,String channelId,String nick,String group,int headId)
  {
    this.userId=userId;
    this.channelId=channelId;
    this.nick=nick;
    this.group=group;
    this.headId=headId;
  }
  public String getUserId()
  {
    return userId;
  }
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  public String getChannelId()
  {
    return channelId;
  }
  public void setChannelId(String channelId)
  {
    this.channelId = channelId;
  }
  public String getNick()
  {
    return nick;
  }
  public void setNick(String nick)
  {
    this.nick = nick;
  }
  public String getGroup()
  {
    return group;
  }
  public void setGroup(String group)
  {
    this.group = group;
  }
  public int getHeadId()
  {
    return headId;
  }
  public void setHeadId(int headId)
  {
    this.headId = headId;
  }
  
  @Override
  public String toString() {
    return "User [userId=" + userId + ", channelId=" + channelId
        + ", nick=" + nick + ", headId=" + headId + ", group="
        + group + "]";
  }
}
