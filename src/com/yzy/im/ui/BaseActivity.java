package com.yzy.im.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.yzy.im.AppManager;
import com.yzy.im.IMApplication;
import com.yzy.im.annotation.Injector;
import com.yzy.im.bean.IMMessage;
import com.yzy.im.callback.IEventCallback;
/**
 * 所有Activity的基类，由于使用了ActionBar,所以继承自ActionBarActivity
 * com.yzy.im.ui.BaseActivity
 * @author yuanzeyao <br/>
 * create at 2014年9月16日 下午1:16:47
 */
public class BaseActivity extends ActionBarActivity implements IEventCallback
{
  private static final String TAG = "BaseActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    AppManager.getInstance().addActivity(this);
    IMApplication.getInstance().addEventCallback(this);
  }
  
  @Override
  protected void onDestroy()
  {
    AppManager.getInstance().removeActivity(this);
    IMApplication.getInstance().removeEventCallback(this);
    super.onDestroy();
  }

  @Override
  public void onMessage(IMMessage message)
  {
  }

  @Override
  public void onBind(Context context, int errorCode, String content)
  {
  }

  @Override
  public void onNotify(String title, String content)
  {
  }

  @Override
  public void onNetChange(boolean isNetConnected)
  {
  }
  
  
  @Override
  public void setContentView(int layoutResID)
  {
    super.setContentView(layoutResID);
    Injector.initInJectViewId(this,this.getWindow().getDecorView());
  }



  @Override
  public void setContentView(View view, LayoutParams params)
  {
    super.setContentView(view, params);
    Injector.initInJectViewId(this,this.getWindow().getDecorView());
  }



  @Override
  public void setContentView(View view)
  {
    super.setContentView(view);
    Injector.initInJectViewId(this,this.getWindow().getDecorView());
  }

}
