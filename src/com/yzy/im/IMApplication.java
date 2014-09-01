package com.yzy.im;

import java.util.ArrayList;

import com.baidu.frontia.FrontiaApplication;
import com.yzy.im.callback.IEventCallback;
import com.yzy.im.util.SharePreferenceHelper;

public class IMApplication extends FrontiaApplication
{
  private static final String TAG = "IMApplication";
  
  private static  IMApplication _instance=null;
  private SharePreferenceHelper sp=null;
  private ArrayList<IEventCallback> callbacks=new ArrayList<IEventCallback>();
  
  public static IMApplication getInstance()
  {
    if(_instance!=null)
    {
      return _instance;
    }
    return null;
  }
  
  public SharePreferenceHelper getSharePreference()
  {
    if(sp==null)
    {
      sp=SharePreferenceHelper.getInstance(this);
    }
    return sp;
  }
  
  public ArrayList<IEventCallback> getCallback()
  {
    return callbacks;
  }
  
  @Override
  public void onCreate()
  {
    super.onCreate();
    _instance=this;
  }
}
