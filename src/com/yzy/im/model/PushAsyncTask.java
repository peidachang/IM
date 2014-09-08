package com.yzy.im.model;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.yzy.im.R;
import com.yzy.im.callback.IPushMessageCallback;
import com.yzy.im.exception.PushMessageException;
import com.yzy.im.server.IConstants;
import com.yzy.im.server.IMManager;
import com.yzy.im.util.CommonUtil;

public class PushAsyncTask extends AsyncTask<Object, Void, String>
{
  private static final String TAG = "PushAsyncTask";
  private IPushMessageCallback callback;
  

  @Override
  protected String doInBackground(Object... objs)
  {
    String result=null;
    try
    {
      if(objs.length!=3)
      {
       throw new PushMessageException(CommonUtil.getStringById(R.string.need_three_param));
      }
      String message=(String)objs[0];
      String userId=(String)objs[1];
      callback=(IPushMessageCallback)objs[2];
      if(TextUtils.isEmpty(userId))
        result=IMManager.getInstance().pushMessage(message);
      else
        result=IMManager.getInstance().pushMessage(message, userId);
    }catch(PushMessageException e)
    {
      result=null;
      e.printStackTrace();
    }
    
    return result;
  }
  
  @Override
  protected void onPostExecute(String result)
  {
    if(callback!=null)
    {
      if( result!=null)
      {
        if(result.contains(IConstants.SEND_MSG_ERROR))
        {
            callback.onFailure();
        }else
        {
            callback.onSuccess();
        }
      }else
      {
        callback.onFailure();
      }
    }
    
    
  }
}
