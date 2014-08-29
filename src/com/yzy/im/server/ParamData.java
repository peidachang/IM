package com.yzy.im.server;

import java.util.HashMap;

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
  
}
