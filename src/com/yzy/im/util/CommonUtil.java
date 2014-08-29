package com.yzy.im.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class CommonUtil
{
  private static final String TAG = "CommonUtil";
  
  public static int getScreenWidth(Context context)
  {
    DisplayMetrics dm=new DisplayMetrics();
    WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    wm.getDefaultDisplay().getMetrics(dm);
    return dm.widthPixels;
  }
  
  public static int getScreenHeight(Context context)
  {
    DisplayMetrics dm=new DisplayMetrics();
    WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    wm.getDefaultDisplay().getMetrics(dm);
    return dm.heightPixels;
  }
}
