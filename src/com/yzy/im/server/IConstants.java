package com.yzy.im.server;

public interface IConstants
{
  //请求参数
  public static final String METHOD="method";
  
  public static final String APIKEY="apikey";
  
  public static final String USER_ID="user_id";
  
  public static final String DEVICE_TYPE="device_type";
  
  public static final String START="start";
  
  public static final String LIMIT="limit";
  
  public static final String TIMESTAMP="timestamp";
  
  public static final String SIGN="sign";
  
  public static final String EXPIRES="expires";
  
  public static final String V="v";
  
  public static final String PUSH_TYPE="push_type";
  
  public static final String CHANNEL_ID="channel_id";
  
  public static final String TAG="tag";
  
  public static final String MESSAGE_TYPE="message_type";
  
  public static final String MESSAGES="messages";
  
  public static final String MSG_KEYS="msg_keys";
  
  public static final String MESSAGE_EXPIRES="message_expires";
  
  public static final String DEPLOY_STATUS="deploy_status";
  
  public static final String MSG_IDS="msg_ids";
  
  
  //方法名
  public static final String QUERY_BINDLIST="query_bindlist";
  
  public static final String PUSH_MSG="push_msg";
  
  public static final String VERIFY_BIND="verify_bind";
  
  public static final String FETCH_MSG="fetch_msg";
  
  public static final String FETCH_MSGCOUNT="fetch_msgcount";
  
  public static final String DELETE_MSG="delete_msg";
  
  public static final String SET_TAG="set_tag";
  
  public static final String FETCH_TAG="fetch_tag";
  
  public static final String DELETE_TAG="delete_tag";
  
  public static final String QUERY_USER_TAGS="query_user_tags";
  
  public static final String QUERY_DEVICE_TYPE="query_device_type";
  
  
  public static final String HTTP_METHOD="POST";
  
  public static final String TEXT_MESSAGE="0";
  
  public static final String MSG_KEY="msgkey";
  
  public static final String MSG_ADD_FRIEND_PRE="addFriend";
  
  
  /**
   * 推送类型，取值范围为：1～3
      1：单个人，必须指定user_id 和 channel_id （指定用户的指定设备）或者user_id（指定用户的所有设备）
      2：一群人，必须指定 tag
      3：所有人，无需指定tag、user_id、channel_id
   */
  public final static String PUSH_TYPE_USER = "1";
  public final static String PUSH_TYPE_TAG = "2";
  public final static String PUSH_TYPE_ALL = "3";
  
  public static final String SEND_MSG_ERROR = "send_msg_error";
  //特殊的消息
  /**
   * 新用户上线
   */
  public static final String MSG_NEW_USER="*#*#%&";
  
  public static final String MSG_NEW_USER_REPLY="*#*#%&*#*#%&";
  /**
   * 发送窗口抖动
   */
  public static final String MSG_SHAKE="#*#*&%";
  
  
  public static final String mapikey="v39ZGSe6lOZlPLGAQyVeCmCd";
  
  public static final String secretkey="oAAlQzOEaNDvDskvelNduk8PbAzklTXu";
  
  public static final String mUrl="http://channel.api.duapp.com/rest/2.0/channel/";
  
  
  
  
  
  
}
