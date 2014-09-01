package com.yzy.im.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.yzy.im.R;
import com.yzy.im.util.SharePreferenceHelper;

public class SplashActivity extends Activity
{
  private static final String TAG = "SplashActivity";
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    View view=LayoutInflater.from(this).inflate(R.layout.logo, null);
    this.setContentView(view);
    Animation anim=new AlphaAnimation(0.1f, 1.0f);
    anim.setDuration(300);
    anim.setAnimationListener(new AnimationListener()
    {
      
      @Override
      public void onAnimationStart(Animation arg0)
      {
      }
      
      @Override
      public void onAnimationRepeat(Animation arg0)
      {
        
      }
      
      @Override
      public void onAnimationEnd(Animation arg0)
      {
        String name=SharePreferenceHelper.getInstance(SplashActivity.this).getStringVaule(SharePreferenceHelper.KEY_LOGIN_NAME, "");
        if(TextUtils.isEmpty(name))
        {
          Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
          SplashActivity.this.startActivity(intent);
        }else
        {
          Intent intent=new Intent(SplashActivity.this,MainActivity.class);
          SplashActivity.this.startActivity(intent);
        }
        SplashActivity.this.finish();
      }
    });
    view.startAnimation(anim);
   
  }
}