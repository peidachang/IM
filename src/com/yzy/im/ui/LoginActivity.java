package com.yzy.im.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.yzy.im.IMApplication;
import com.yzy.im.R;
import com.yzy.im.bean.IMMessage;
import com.yzy.im.callback.IEventCallback;
import com.yzy.im.customview.ClearEditText;
import com.yzy.im.server.IConstants;
import com.yzy.im.util.LogUtil;
import com.yzy.im.util.SharePreferenceHelper;
import com.yzy.im.util.ToastUtils;

public class LoginActivity extends Activity implements OnClickListener,IEventCallback
{
  private static final String TAG = "LoginActivity";
  private ClearEditText mName;
  private ClearEditText mpwd;
  private Button btnLogin;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.setContentView(R.layout.login_layout);
    initView();
    IMApplication.getInstance().getCallback().add(this);
  }
  
  private void initView()
  {
    mName=(ClearEditText)this.findViewById(R.id.username);
    mpwd=(ClearEditText)this.findViewById(R.id.password);
    btnLogin=(Button)this.findViewById(R.id.login);
    btnLogin.setOnClickListener(this);
  }

  @Override
  public void onClick(View view)
  {
    if(view.getId()==R.id.login)
    {
      PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY,IConstants.mapikey);
    }
  }

  @Override
  public void onMessage(IMMessage message)
  {
  }



  @Override
  public void onNotify(String title, String content)
  {
  }

  @Override
  public void onNetChange(boolean isNetConnected)
  {
  }

  @Override
  public void onBind(Context context, int errorCode, String content)
  {
    if(errorCode==0)
    {
      ToastUtils.AlertMessageInBottom("Success");
    }else
    {
      ToastUtils.AlertMessageInBottom("Fail");
    }
  }
  
  @Override
  protected void onDestroy()
  {
    IMApplication.getInstance().getCallback().remove(this);
    super.onDestroy();
  }


}
