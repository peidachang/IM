package com.yzy.im.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.yzy.im.IMApplication;
import com.yzy.im.R;
import com.yzy.im.adapter.FacePagerAdapter;
import com.yzy.im.adapter.MessageAdapter;
import com.yzy.im.adapter.PerPageAdapter;
import com.yzy.im.bean.IMMessage;
import com.yzy.im.bean.MessageItem;
import com.yzy.im.bean.User;
import com.yzy.im.callback.IEventCallback;
import com.yzy.im.customview.CirclePageIndicator;
import com.yzy.im.customview.InputLinearLayout;
import com.yzy.im.customview.InputLinearLayout.onKeyBoradListener;
import com.yzy.im.customview.JazzyViewPager;
import com.yzy.im.customview.MsgListView;
import com.yzy.im.model.PushAsyncTask;
import com.yzy.im.server.IConstants;
import com.yzy.im.util.LogUtil;
import com.yzy.im.util.SharePreferenceHelper;

public class ChatActivity extends BaseActivity implements OnClickListener,onKeyBoradListener,
          OnItemClickListener,OnPageChangeListener,OnTouchListener,TextWatcher,IEventCallback
{
  private static final String TAG = "ChatActivity";
  private static final int NUMOFEM=20;
  private static final int NUMOFPERPAGE=21;
  private Button btnSend;
  private Button btnShake;
  private EditText mMsgEdit;
  private User user;
  private ImageButton mImgBtn;
  private View face_panel;
  private InputMethodManager imm;
  
  private JazzyViewPager mViewPager;
  private CirclePageIndicator mPageIndicator;
  private MsgListView mListView;
  private MessageAdapter adapter;
  private InputLinearLayout root;
  
  private boolean isNeedShowFacePanel=false;
  private boolean isKeyBoradShow=false;
  
  private int currentPage=0;
  
  private ArrayList<String> mFaceKeySet;
  private ArrayList<Integer> mFaceValueSet;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.chat_layout);
    
    initView();
    user=(User) this.getIntent().getSerializableExtra("user");
    SharePreferenceHelper.getInstance(this).setTalkUserId(user.getUserId());
    ActionBar actionBar=this.getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    String data = getResources().getString(R.string.talk_with);
    data = String.format(data,user.getNick());
    actionBar.setTitle(data);
    actionBar.setDisplayShowCustomEnabled(true);
  }
  
  private void initView()
  {
    btnSend=(Button)this.findViewById(R.id.send_btn);
    mImgBtn=(ImageButton)this.findViewById(R.id.face_btn);
    mMsgEdit=(EditText)this.findViewById(R.id.msg_et);
    face_panel=this.findViewById(R.id.face_ll);
    root=(InputLinearLayout) this.findViewById(R.id.root);
    btnSend.setOnClickListener(this);
    mImgBtn.setOnClickListener(this);
    root.setOnKeyBoradListener(this);
    
    
    mViewPager=(JazzyViewPager)this.findViewById(R.id.face_pager);
    mPageIndicator=(CirclePageIndicator)this.findViewById(R.id.indicator);
    mPageIndicator.setOnPageChangeListener(this);
    mMsgEdit.addTextChangedListener(this);
    
    initFacePanel();
    initMsgListView();
    
    //初始化输入法
    imm=(InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
  }
  
  private void initMsgListView()
  {
    mListView=(MsgListView) this.findViewById(R.id.msg_listView);
    mListView.setOnTouchListener(this);
    mListView.setPullLoadEnable(false);
    mListView.setPullRefreshEnable(false);
    ArrayList<MessageItem> msgs=new ArrayList<MessageItem>();
    //如果从Notification过来的，则需要显示
    IMMessage notification=(IMMessage) this.getIntent().getSerializableExtra("msg");
    if(notification!=null)
    {
     
      MessageItem item=new MessageItem(notification.getNick(), notification.getMessage(), notification.getTime_samp(), true, notification.getHeadid());
      msgs.add(item);
      
      String data = getResources().getString(R.string.talk_with);
      data = String.format(data,notification.getNick());
      this.getSupportActionBar().setTitle(data);
    }
   
    adapter=new MessageAdapter(msgs, this);
    mListView.setAdapter(adapter);
  }
  
  private void initFacePanel()
  {
     HashMap<String,Integer> mFaceMap=IMApplication.getInstance().getFaceMap();
     //ArrayList<Integer> mFaceList=new ArrayList(mFaceMap.values());
     mFaceKeySet=new ArrayList(mFaceMap.keySet());
     mFaceValueSet=new ArrayList(mFaceMap.values());
     int size=mFaceMap.size();
     LogUtil.getLogger().i("size-->mFaceMap size is"+size);
     int page=(size%NUMOFEM==0)?(size/NUMOFEM):(size/NUMOFEM+1);
     LogUtil.getLogger().i("size-->page size is"+page);
     ArrayList<View> views=new ArrayList<View>();
     for(int i=0;i<page;i++)
     {
       LogUtil.getLogger().d("i-->"+i);
       View view=LayoutInflater.from(this).inflate(R.layout.face_layout, null);
       GridView grid=(GridView) view.findViewById(R.id.grid);
       grid.setOnItemClickListener(this);
       LogUtil.getLogger().d("grid-->"+grid);
       ArrayList<Integer>  tmp=new ArrayList<Integer>();
       for(int j=i*NUMOFPERPAGE;j<(i+1)*NUMOFPERPAGE && j<=mFaceMap.size();j++)
       {
         if(j==((i+1)*NUMOFPERPAGE)-1||j==mFaceValueSet.size())
         {
           tmp.add(R.drawable.emotion_del_selector);
         }
         else
         {
           tmp.add(mFaceValueSet.get(j));
         }
          
       }
       PerPageAdapter adapter=new PerPageAdapter(this,tmp);
       grid.setAdapter(adapter);
       views.add(view);
     }
     FacePagerAdapter fAdapter=new FacePagerAdapter(views, mViewPager);
     mViewPager.setAdapter(fAdapter);
     mPageIndicator.setViewPager(mViewPager, 0);
    
  }

  @Override
  public void onClick(View v)
  {
    if(v.getId()==R.id.send_btn)
    {
      sendMessage();
      mMsgEdit.setText("");
      imm.hideSoftInputFromWindow(mMsgEdit.getWindowToken(), 0);
    }else if(v.getId()==R.id.face_btn)
    {
      if(face_panel!=null)
        
        if(isKeyBoradShow)
        {
          isNeedShowFacePanel=true;
          //先隐藏键盘
          imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }else
        {
          face_panel.setVisibility(View.VISIBLE);
        }
    }else if(v.getId()==R.id.action_right)
    {
      //ToastUtils.AlertMessageInBottom("Shake");
     sendShake();
    }
    
  }
  
  private void sendShake()
  {
    IMMessage msg=new IMMessage(IConstants.MSG_SHAKE, "tag");
    PushAsyncTask task=new PushAsyncTask();
    task.execute(new Gson().toJson(msg),user.getUserId(),null);
  }

  private void sendMessage()
  {
    IMMessage msg=new IMMessage(mMsgEdit.getText().toString(), "tag");
    MessageItem item=new MessageItem(msg.getNick(), msg.getMessage(), msg.getTime_samp(), false, msg.getHeadid());
    adapter.addMessage(item);
    PushAsyncTask task=new PushAsyncTask();
    task.execute(new Gson().toJson(msg),user.getUserId(),null);
  }

  @Override
  public void onKeyBoardShow()
  {
    LogUtil.getLogger().d("keyborad-->show");
    isKeyBoradShow=true;
    face_panel.setVisibility(View.GONE);
  }

  @Override
  public void onKeyBoardHide()
  {
    LogUtil.getLogger().d("keyborad-->hide");
    isKeyBoradShow=false;
    if(isNeedShowFacePanel)
    {
      face_panel.setVisibility(View.VISIBLE);
      isNeedShowFacePanel=false;
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id)
  {
    //这段代码的逻辑来自way，直接拿来主义
    if((Integer)view.getTag()==PerPageAdapter.ITEM_DEL)
    {
      //点击删除
      int selectPos=mMsgEdit.getSelectionStart();//获取光标的位置
      //等于0就没有必要删除
      if(selectPos>0)
      {
        String content=mMsgEdit.getText().toString();
        String text=content.substring(selectPos-1);
        if(text.equals("]"))
        {
          int start=content.lastIndexOf("[");
          int end=selectPos;
          mMsgEdit.getText().delete(start,end);
          return;
        }
        
        mMsgEdit.getText().delete(selectPos-1,selectPos);
      }
      
    }else
    {
      //点击Emoji
      int which=currentPage*NUMOFEM+position;
      if(which<mFaceValueSet.size())
      {
        Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(), mFaceValueSet.get(which));
        if(bitmap!=null)
        {
          int rawHeight=bitmap.getHeight();
          int rawWidth=bitmap.getWidth();
          
          int newHeight=40;
          int newWidth=40;
          
          
       // 计算缩放因子
          float heightScale = ((float) newHeight) / rawHeight;
          float widthScale = ((float) newWidth) / rawWidth;
          
          Matrix matrix = new Matrix();
          matrix.postScale(heightScale, widthScale);
          
          Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
              rawWidth, rawHeight, matrix, true);
          
          ImageSpan imgSpan=new ImageSpan(this, newBitmap);
          String emojiStr=mFaceKeySet.get(which);
          
          SpannableString spanString=new SpannableString(emojiStr);
          spanString.setSpan(imgSpan, emojiStr.indexOf("["), emojiStr.indexOf("]")+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
          mMsgEdit.getText().append(spanString);
        }else
        {
          String ori = mMsgEdit.getText().toString();
          int index = mMsgEdit.getSelectionStart();
          StringBuilder stringBuilder = new StringBuilder(ori);
          stringBuilder.insert(index, mFaceKeySet.get(which));
          mMsgEdit.setText(stringBuilder.toString());
          mMsgEdit.setSelection(index + mFaceKeySet.get(which).length());
        }
      }
      
    }
  }

  @Override
  public void onPageScrollStateChanged(int arg0)
  {
  }

  @Override
  public void onPageScrolled(int arg0, float arg1, int arg2)
  {
  }

  @Override
  public void onPageSelected(int position)
  {
     currentPage=position;
  }

  @Override
  public boolean onTouch(View v, MotionEvent event)
  {
    return false;
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after)
  {
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count)
  {
    //设置btnSend是否可用
    if(s.length()>0)
    {
      btnSend.setEnabled(true);
    }else
    {
      btnSend.setEnabled(false);
    }
  }

  @Override
  public void afterTextChanged(Editable s)
  {
    if(s==null || s.length()==0)
    {
      btnSend.setEnabled(false);
    }
  }
  
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event)
  {
    if(keyCode==KeyEvent.KEYCODE_BACK )
    {
      if(face_panel!=null && face_panel.getVisibility()==View.VISIBLE)
      {
        face_panel.setVisibility(View.GONE);
        return true;
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public void onMessage(IMMessage msg)
  {
    //只处理自己的消息，别人的消息不处理
    if(msg.getMessage().equals(IConstants.MSG_NEW_USER) || msg.getMessage().equals(IConstants.MSG_NEW_USER_REPLY))
      return;
    if(msg.getUserid().equals(user.getUserId()))
    {
      MessageItem item=new MessageItem(msg.getNick(), msg.getMessage(), msg.getTime_samp(), true, msg.getHeadid());
      if(item.getMessage().equals(IConstants.MSG_SHAKE))
      {
        Animation anim=AnimationUtils.loadAnimation(this, R.anim.shakeanim);
        root.startAnimation(anim);
      }else
      {
        adapter.addMessage(item);
      }
    }
   
    
  }

  @Override
  public void onBind(Context context, int errorCode, String content)
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
  protected void onDestroy()
  {
    super.onDestroy();
    //清空当前的聊天对象
    user=null;
    SharePreferenceHelper.getInstance(this).setTalkUserId(null);
   
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.chat, menu);
    MenuItem item= menu.findItem(R.id.action_shake);
    //在2.x版本中通过这种方式
    btnShake=(Button) MenuItemCompat.getActionView(item).findViewById(R.id.action_right);
    //3.0以后版本可以使用这种方式
    //btnShake=(Button)item.getActionView().findViewById(R.id.send_shake);
    btnShake.setText(R.string.send_shake);
    btnShake.setOnClickListener(this);
    return super.onCreateOptionsMenu(menu);
  }
  
  


}
