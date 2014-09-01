package com.yzy.im.callback;

import android.content.Context;

import com.yzy.im.bean.IMMessage;

public interface IEventCallback
{
  public abstract void onMessage(IMMessage message);

  public abstract void onBind(Context context, int errorCode, String content);

  public abstract void onNotify(String title, String content);

  public abstract void onNetChange(boolean isNetConnected);

}
