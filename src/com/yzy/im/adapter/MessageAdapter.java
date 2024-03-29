package com.yzy.im.adapter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzy.im.IMApplication;
import com.yzy.im.R;
import com.yzy.im.bean.MessageItem;
import com.yzy.im.util.TimeUtils;

public class MessageAdapter extends BaseAdapter
{
  public static final Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");
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
    holder.tvMsg.setText(convertNormalStringToSpannableString(item.getMessage()));
    
    holder.tvTime.setText(TimeUtils.convert(item.getTime_samp()));
    
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
  
  //这段代码用正则表达式解析emoji,来自way,拿来主义
  private CharSequence convertNormalStringToSpannableString(String message) {
    // TODO Auto-generated method stub
    String hackTxt;
    if (message.startsWith("[") && message.endsWith("]")) {
      hackTxt = message + " ";
    } else {
      hackTxt = message;
    }
    SpannableString value = SpannableString.valueOf(hackTxt);

    Matcher localMatcher = EMOTION_URL.matcher(value);
    while (localMatcher.find()) {
      String str2 = localMatcher.group(0);
      int k = localMatcher.start();
      int m = localMatcher.end();
      if (m - k < 8) {
        if (IMApplication.getInstance().getFaceMap()
            .containsKey(str2)) {
          Integer face = (Integer) IMApplication.getInstance().getFaceMap().get(str2);
          Bitmap bitmap = BitmapFactory.decodeResource(
              mContext.getResources(), face);
          if (bitmap != null) {
            ImageSpan localImageSpan = new ImageSpan(mContext,
                bitmap, ImageSpan.ALIGN_BASELINE);
            value.setSpan(localImageSpan, k, m,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
          }
        }
      }
    }
    return value;
  }

}
