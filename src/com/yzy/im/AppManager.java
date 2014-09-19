package com.yzy.im;

import java.util.Stack;

import android.app.Activity;

import com.yzy.im.ui.BaseActivity;

/**
 * 用于管理应用中所有的Activity的生命周期
 * com.yzy.im.AppManager
 * @author yuanzeyao <br/>
 * create at 2014年9月16日 下午1:23:21
 */
public class AppManager
{
  private static final String TAG = "AppManager";
  
  private AppManager()
  {
    
  }
  public static class SingleHolder
  {
    public static final AppManager instance=new AppManager();
  }
  
  public static AppManager getInstance()
  {
    return SingleHolder.instance;
  }
  public Stack<Activity> allActiivtys;
  public Activity currentActivity=null;
  
  public void addActivity(Activity activity)
  {
    if(allActiivtys==null)
    {
      allActiivtys=new Stack<Activity>();
    }
    allActiivtys.push(activity);
    currentActivity=allActiivtys.peek();
  }
  
  public void removeActivity(Activity activity)
  {
    if(allActiivtys!=null && allActiivtys.size()>0)
    {
      for(Activity tmp:allActiivtys)
      {
        if(tmp==activity)
        {
          allActiivtys.remove(activity);
        }
      }
      
      if(allActiivtys.size()==0)
      {
        currentActivity=null;
      }else
      {
        currentActivity=allActiivtys.peek();
      }
    }else
    {
      currentActivity=null;
    }
    
  }

  public void exitApp(boolean runback)
  {
    for(Activity activity:allActiivtys)
    {
      activity.finish();
    }
    if(!runback)
    {
      System.exit(0);
    }
  }
  
  
}
