package com.yzy.im.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
  
  /**
   * 对URL进行UTF-8编码
   * @param url 
   *        需要进行编码的url
   * @return
   *        编码后的url
   * @throws UnsupportedEncodingException
   */
  public static String urlEncode(String url) throws UnsupportedEncodingException
  {
    String tmp=URLEncoder.encode(url, "utf-8");
    return tmp.replace("*", "%2A");
  }
  
  public static  String jsonEncode(String url)
  {
    String tmp=url.replace("\\", "\\\\");
    tmp=tmp.replace("\"", "\\\"");
    tmp=tmp.replace("\'", "\\\'");
    return tmp;
  }
}
