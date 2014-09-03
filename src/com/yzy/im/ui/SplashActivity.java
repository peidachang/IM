package com.yzy.im.ui;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.yzy.im.R;
import com.yzy.im.config.IMConfiguration;

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
        try
        {
          IMConfiguration.loadProperties(SplashActivity.this.getAssets().open("im.properties"));
        } catch (IOException e)
        {
          e.printStackTrace();
        }
      }
      
      @Override
      public void onAnimationRepeat(Animation arg0)
      {
        
      }
      
      @Override
      public void onAnimationEnd(Animation arg0)
      {
        //if(TextUtils.isEmpty(name))
        {
          Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
          SplashActivity.this.startActivity(intent);
        }
//        else
//        {
//          Intent intent=new Intent(SplashActivity.this,MainActivity.class);
//          SplashActivity.this.startActivity(intent);
//        }
        SplashActivity.this.finish();
      }
    });
    view.startAnimation(anim);
   
  }
}
