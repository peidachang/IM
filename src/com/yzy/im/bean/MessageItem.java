package com.yzy.im.bean;

public class MessageItem
{
  private static final String TAG = "MessageItem";
  private long time_samp;
  private String message;
  private boolean isfrom=true;
  private String name;
  private int headImg;
  
  public MessageItem(String name,String message,long time_samp,boolean isfrom,int headImg)
  {
    this.time_samp=time_samp;
    this.message=message;
    this.isfrom=isfrom;
    this.name=name;
    this.headImg=headImg;
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

  public boolean isIsfrom()
  {
    return isfrom;
  }

  public void setIsfrom(boolean isfrom)
  {
    this.isfrom = isfrom;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public int getHeadImg()
  {
    return headImg;
  }

  public void setHeadImg(int headImg)
  {
    this.headImg = headImg;
  }
  
  
  
  
}
