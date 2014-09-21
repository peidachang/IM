package com.yzy.im.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzy.im.R;
import com.yzy.im.util.ImageUtil;
import com.yzy.im.util.ToastUtils;

public class MenuFragment extends Fragment implements OnClickListener
{
  private static final String TAG = "MenuFragment";
  private ImageView img_head;
  private TextView tv_name;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View view=inflater.inflate(R.layout.menufragment , container,false);
    
    img_head=(ImageView)view.findViewById(R.id.img_head);
    tv_name=(TextView)view.findViewById(R.id.tv_name);
    
    img_head.setOnClickListener(this);
    
    Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.h0);
    img_head.setBackgroundDrawable(new BitmapDrawable(ImageUtil.toRoundBitmap(bitmap, getActivity())));
    tv_name.setText("风中绿竹");
    return view;
  }

  @Override
  public void onClick(View v)
  {
    if(v==img_head)
    {
      ToastUtils.AlertMessageInCenter("编辑图像");
    }
  }
  
  
}
