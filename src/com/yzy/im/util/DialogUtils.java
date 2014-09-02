package com.yzy.im.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yzy.im.R;

public class DialogUtils 
{
  public static ProgressDialog createProcgressDialog(Context mContext,String info)
  {
    ProgressDialog dialog=null;
    if(mContext==null)
      return null;
    else
    {
      dialog=new ProgressDialog(mContext);
      dialog.show();
      dialog.setContentView(R.layout.progress_dialog);
      if(!TextUtils.isEmpty(info))
      {
        ((TextView) dialog.findViewById(R.id.tv_info)).setText(info);;
      }
      dialog.setCancelable(true);
      Window window=dialog.getWindow();
      WindowManager.LayoutParams lp=window.getAttributes();
      lp.width=320;
      lp.height=320;
      window.setAttributes(lp);
      dialog.setCanceledOnTouchOutside(false);
    }
    
    return dialog;
  }
}
