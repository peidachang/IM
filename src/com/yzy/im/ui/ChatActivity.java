package com.yzy.im.ui;

import com.yzy.im.R;

import android.app.Activity;
import android.os.Bundle;

public class ChatActivity extends Activity
{
  private static final String TAG = "ChatActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.chat_layout);
  }
}
