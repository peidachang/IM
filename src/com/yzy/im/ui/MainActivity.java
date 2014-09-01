package com.yzy.im.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.yzy.im.R;
import com.yzy.im.bean.IMMessage;
import com.yzy.im.callback.IEventCallback;
import com.yzy.im.server.IMManager;
import com.yzy.im.slidemenu.BaseSlidingFragmentActivity;
import com.yzy.im.slidemenu.SlidingMenu;
import com.yzy.im.util.CommonUtil;

@SuppressLint("NewApi")
public class MainActivity extends BaseSlidingFragmentActivity implements IEventCallback
{
  private SlidingMenu mSlidingMenu;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    addLeft();
    
    IMManager manager=new IMManager();
    manager.pushMessage("Hello,Every One");
  }
  
  @SuppressLint("NewApi")
  private void addLeft() {
    FrameLayout left = new FrameLayout(this);
    left.setId("LEFT".hashCode());
    setBehindContentView(left);
    getSupportFragmentManager()
    .beginTransaction()
    .replace("LEFT".hashCode(), new MenuFragment())
    .commit();
    
    SlidingMenu sm = getSlidingMenu();
    sm.setMode(SlidingMenu.LEFT);
    sm.setShadowDrawable(R.drawable.shadow);
    sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
   
    int screenWidth=CommonUtil.getScreenWidth(this);
    sm.setFadeEnabled(true);
    sm.setFadeDegree(0.35f);
    sm.setShadowWidth(screenWidth/40);
    sm.setBehindWidth((int)(screenWidth*0.5));
    sm.setBehindScrollScale(0.3333f);
    
  }
  

  @Override
  public void onMessage(IMMessage message)
  {
  }

  @Override
  public void onBind(String method, int errorCode, String content)
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


}
