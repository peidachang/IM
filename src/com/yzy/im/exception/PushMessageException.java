package com.yzy.im.exception;

public class PushMessageException extends Exception
{
  private static final String TAG = "PushMessageException";
  
  public PushMessageException(String message)
  {
    super(message);
  }
}
