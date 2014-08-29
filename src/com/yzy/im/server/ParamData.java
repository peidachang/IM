package com.yzy.im.server;

import java.util.HashMap;
import java.util.Map;

import com.universal.framwork.util.LogUtil;

import android.text.TextUtils;

/**
 * 执行RESEAPI时，需要的参数
 * com.yzy.im.server.ParamData
 * @author yuanzeyao <br/>
 * create at 2014年8月29日 下午4:37:47
 */
public class ParamData
{
  private static final String TAG = "ParamData";
  private HashMap<String,String> parmas;
  
  public ParamData(String method)
  {
    parmas=new HashMap<String,String>();
    parmas.put(IConstants.METHOD,method);
    parmas.put(IConstants.APIKEY,IConstants.mapikey);
  }
  
  public void put(String key,String value)
  {
    if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value))
    {
      parmas.put(key, value);
    }
  }
  
  public String get(String key)
  {
    if(!TextUtils.isEmpty(key))
    {
      return parmas.get(key);
    }else
    {
      return null;
    }
  }
  
  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    for(Map.Entry<String, String> entry:parmas.entrySet())
    {
      sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
    }
    sb.deleteCharAt(sb.toString().length()-1);
    LogUtil.i("yzy", "toString--->"+sb.toString());
    return super.toString();
  }
  
}
