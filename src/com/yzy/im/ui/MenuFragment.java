package com.yzy.im.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
  
  private View view_item_for_personinfo;
  private View view_item_for_addfriend;
  private View view_item_for_myfile;
  private View view_item_for_setting;
  private View view_item_for_about;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View view=inflater.inflate(R.layout.menufragment , container,false);
    
    findView(view);
    
    initEvent();
    
    
    return view;
  }

  private void initEvent()
  {
    img_head.setOnClickListener(this);
    view_item_for_personinfo.setOnClickListener(this);
    view_item_for_addfriend.setOnClickListener(this);
    view_item_for_myfile.setOnClickListener(this);
    view_item_for_setting.setOnClickListener(this);
    view_item_for_about.setOnClickListener(this);
  }
  
  private void findView(View view)
  {
    img_head=(ImageView)view.findViewById(R.id.img_head);
    tv_name=(TextView)view.findViewById(R.id.tv_name);
    view_item_for_personinfo=view.findViewById(R.id.item_for_personinfo);
    view_item_for_addfriend=view.findViewById(R.id.item_for_addfriend);
    view_item_for_myfile=view.findViewById(R.id.item_for_myfile);
    view_item_for_setting=view.findViewById(R.id.item_for_setting);
    view_item_for_about=view.findViewById(R.id.item_for_about);
    
    //暂时写临时数据
    Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.h0);
    img_head.setBackgroundDrawable(new BitmapDrawable(ImageUtil.toRoundBitmap(bitmap, getActivity())));
    tv_name.setText("风中绿竹");
  }

  @Override
  public void onClick(View v)
  {
    if(v==img_head)
    {
      ToastUtils.AlertMessageInCenter("编辑图像");
    }else if(v==view_item_for_personinfo)
    {
      ((MainActivity)this.getActivity()).replaceContentById(R.id.item_for_personinfo);
    }else if(v==view_item_for_addfriend)
    {
      ToastUtils.AlertMessageInCenter("添加好友");
    }else if(v==view_item_for_myfile)
    {
      ToastUtils.AlertMessageInCenter("我的文件");
    }else if(v==view_item_for_setting)
    {
      ToastUtils.AlertMessageInCenter("设置");
    }else if(v==view_item_for_about)
    {
      ToastUtils.AlertMessageInCenter("关于");
    }
  }
  
  
}
