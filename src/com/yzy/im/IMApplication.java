package com.yzy.im;

import com.baidu.frontia.FrontiaApplication;

public class IMApplication extends FrontiaApplication
{
  private static final String TAG = "IMApplication";
  
  private static  IMApplication _instance=null;
  
  public static IMApplication getInstance()
  {
    if(_instance!=null)
    {
      return _instance;
    }
    return null;
  }
  
  @Override
  public void onCreate()
  {
    super.onCreate();
    _instance=this;
  }
}
