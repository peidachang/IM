package com.yzy.im.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.yzy.im.R;
import com.yzy.im.customview.ClearEditText;
import com.yzy.im.util.LogUtil;
import com.yzy.im.util.SharePreferenceHelper;

public class LoginActivity extends Activity implements OnClickListener
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
      LogUtil.getLogger().d("name-->"+mName.getText());
      if(mName.getText().toString().equals("gavin") && mpwd.getText().toString().equals("123"))
      {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        LoginActivity.this.startActivity(intent);
        SharePreferenceHelper.getInstance(this).setStringValue(SharePreferenceHelper.KEY_LOGIN_NAME, mName.getText().toString());
        finish();
      }
      
    }
  }


}
