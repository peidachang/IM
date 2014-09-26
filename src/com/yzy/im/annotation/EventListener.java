package com.yzy.im.annotation;

import java.lang.reflect.Method;

import android.view.View;
import android.view.View.OnClickListener;

public class EventListener implements OnClickListener
{
  private static final String TAG = "EventListener";
  
  private Object handler;
  private String methodName;
  
  public EventListener(Object handler)
  {
    this.handler=handler;
  }
  
  public EventListener click(String methodName)
  {
    this.methodName=methodName;
    return this;
  }

  @Override
  public void onClick(View view)
  {
    Method method=null;
    if(handler!=null)
    {
      try
      {
        method=handler.getClass().getDeclaredMethod(methodName, View.class);
        if(method!=null)
        {
          method.invoke(handler, view);
        }
      }catch(Exception e)
      {
        e.printStackTrace();
      }
      
    }
  }
  
  
}
