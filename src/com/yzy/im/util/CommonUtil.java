package com.yzy.im.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.yzy.im.IMApplication;
import com.yzy.im.server.IConstants;

@SuppressLint("NewApi")
public class CommonUtil implements IConstants
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
  
  public static String getSignature(TreeMap<String,String> sortedParams,String channel) throws IOException
  {
    // 先将参数以其参数名的字典序升序进行排序
    Set<Entry<String, String>> entrys = sortedParams.entrySet();
   
    // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
    StringBuilder basestring = new StringBuilder();
    sortedParams.put(TIMESTAMP,
        Long.toString(System.currentTimeMillis() / 1000));
    sortedParams.remove(SIGN);
    
    basestring.append(HTTP_METHOD);
    basestring.append(mUrl);
    basestring.append(channel);
    
    for (Entry<String, String> param : entrys) {
      basestring.append(param.getKey()).append("=").append(param.getValue());
    }
    basestring.append(secretkey);
    
    return MD5Util.encode(basestring.toString());
  }
  
  public static String getStringById(int id)
  {
    return IMApplication.getInstance().getResources().getString(id);
  }
}
