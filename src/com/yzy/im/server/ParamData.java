package com.yzy.im.server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.yzy.im.util.CommonUtil;
import com.yzy.im.util.LogUtil;

import android.text.TextUtils;

/**
 * 执行RESEAPI时，需要的参数
 * com.yzy.im.server.ParamData
 * @author yuanzeyao <br/>
 * create at 2014年8月29日 下午4:37:47
 */
public class ParamData extends HashMap<String,String>
{
  private static final String TAG = "ParamData";
  
  public ParamData(String method)
  {
    put(IConstants.METHOD,method);
    put(IConstants.APIKEY,IConstants.mapikey);
  }
  

  

  
  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    for(Map.Entry<String, String> entry:entrySet())
    {
      sb.append(entry.getKey()).append("=").append(entry.getValue());
    }
    LogUtil.getLogger().v(sb.toString());
    return sb.toString();
  }
  
  public String getParamString() throws UnsupportedEncodingException
  {
    StringBuilder sb=new StringBuilder();
    for(Map.Entry<String, String> entry:entrySet())
    {
      sb.append(entry.getKey()).append("=").append(CommonUtil.urlEncode(entry.getValue())).append("&");
    }
    sb.deleteCharAt(sb.length()-1);
    LogUtil.getLogger().v(sb.toString());
    return sb.toString();
  }
  
}
