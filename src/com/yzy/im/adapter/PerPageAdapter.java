package com.yzy.im.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yzy.im.R;
import com.yzy.im.util.LogUtil;

public class PerPageAdapter extends BaseAdapter
{
  private static final String TAG = "PerPageAdapter";
  public static final int ITEM_DEL=1;
  public static final int ITEM_EMOJI=2;
  private ArrayList<Integer> faces;
  private Context mContext;
  
  
  public PerPageAdapter(Context mContext,ArrayList<Integer> faces)
  {
    this.mContext=mContext;
    this.faces=faces;
  }

  @Override
  public int getCount()
  {
    return faces.size();
  }

  @Override
  public Object getItem(int arg0)
  {
    return faces.get(arg0);
  }

  @Override
  public long getItemId(int arg0)
  {
    return 0;
  }

  @Override
  public View getView(int position, View contentView, ViewGroup parent)
  {
    if(contentView==null)
    {
      contentView=LayoutInflater.from(mContext).inflate(R.layout.faceitem, null);
      
    }
    if(position==getCount()-1)
    {
      contentView.setTag(ITEM_DEL);
    }else
    {
      contentView.setTag(ITEM_EMOJI);
    }
    ImageView img=(ImageView) contentView.findViewById(R.id.img_face);
   
    if(img!=null)
      LogUtil.getLogger().i("id-->"+faces.get(position));
      img.setImageResource(faces.get(position));
    return contentView;
  }
}
