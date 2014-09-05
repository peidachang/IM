package com.yzy.im.customview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.LinearLayout;
/**
 * 用来 监听键盘的出现和隐藏
 * com.yzy.im.customview.InputLinearLayout
 * @author yuanzeyao <br/>
 * create at 2014年9月5日 上午11:04:47
 */
public class InputLinearLayout extends LinearLayout
{
  private static final String TAG = "InputLinearLayout";
  
  
  public static final int DIFFERENCE_VALUE=50;
  private Handler mHandler=new Handler();
  private onKeyBoradListener listener;
  public InputLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  
  public void setOnKeyBoradListener(onKeyBoradListener listener)
  {
    this.listener=listener;
  }
  
  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh)
  {
    super.onSizeChanged(w, h, oldw, oldh);
    final int fold=oldh;
    final int fh=h;
    if(listener==null)
      return;
    mHandler.postDelayed(new Runnable()
    {
      
      @Override
      public void run()
      {
        
        if(fold-fh>DIFFERENCE_VALUE)
        {
          listener.onKeyBoardShow();
        }else
        {
          listener.onKeyBoardHide();
        }
      }
    },80);
    
  }
  
  public interface onKeyBoradListener
  {
    public void onKeyBoardShow();
    public void onKeyBoardHide();
  }

  
}
