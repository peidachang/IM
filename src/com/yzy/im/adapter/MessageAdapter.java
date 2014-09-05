package com.yzy.im.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzy.im.R;
import com.yzy.im.bean.MessageItem;

public class MessageAdapter extends BaseAdapter
{
  private static final String TAG = "MessageAdapter";
  private ArrayList<MessageItem> messages;
  private Context mContext;
  
  public MessageAdapter(ArrayList<MessageItem> messages,Context mContext)
  {
    this.messages=messages;
    this.mContext=mContext;
  }

  @Override
  public int getCount()
  {
    return messages.size();
  }

  @Override
  public Object getItem(int position)
  {
    return messages.get(position);
  }

  @Override
  public long getItemId(int position)
  {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    MessageItem item=messages.get(position);
    ViewHolder holder;
    if(convertView==null)
    {
      if(!item.isIsfrom())
      {
        convertView=LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, null);
      }else
      {
        convertView=LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, null);
      }
      holder=new ViewHolder();
      holder.imgHead=(ImageView) convertView.findViewById(R.id.icon);
      holder.tvMsg=(TextView)convertView.findViewById(R.id.textView2);
      holder.tvTime=(TextView)convertView.findViewById(R.id.datetime);
      convertView.setTag(holder);
    }else
    {
      holder=(ViewHolder) convertView.getTag();
    }
    
    holder.imgHead.setImageResource(item.getHeadImg());
    holder.tvMsg.setText(item.getMessage());
    holder.tvTime.setText(item.getTime_samp()+"");
    
    return convertView;
  }
  
  public void addMessage(MessageItem item)
  {
    messages.add(item);
    notifyDataSetChanged();
  }
  
  @Override
  public int getViewTypeCount()
  {
    return 2;
  }
  
  @Override
  public int getItemViewType(int position)
  {
    MessageItem item=messages.get(position);
    if(item.isIsfrom())
    {
      return 1;
    }else
    {
      return 0;
    }
  }
  
  public static class ViewHolder
  {
    public ImageView imgHead;
    private TextView tvMsg;
    private TextView tvTime;
    
  }
}
