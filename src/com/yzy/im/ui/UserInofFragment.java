package com.yzy.im.ui;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yzy.im.R;

public class UserInofFragment extends Fragment
{
  private static final String TAG = "UserInofFragment";
  private static  final int IMAGE_HALFWIDTH = 30;
  //用户二维码名片
  private ImageView img_qrcode;
  
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    return inflater.inflate(R.layout.userinfo, container,false);
  }
  
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState)
  {
    super.onViewCreated(view, savedInstanceState);
    img_qrcode=(ImageView)view.findViewById(R.id.img_qr_code);
    try
    {
      img_qrcode.setImageBitmap(cretaeBitmap("Hello"));
    } catch (WriterException e)
    {
      e.printStackTrace();
    }
  }
  
  public Bitmap cretaeBitmap(String str) throws WriterException {
    Bitmap mBitmap=BitmapFactory.decodeResource(this.getResources(), R.drawable.h0);
    Matrix m = new Matrix();
    float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
    float sy = (float) 2 * IMAGE_HALFWIDTH
        / mBitmap.getHeight();
    m.setScale(sx, sy);
    mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
        mBitmap.getWidth(), mBitmap.getHeight(), m, false);
    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    hints.put(EncodeHintType.MARGIN, 1);

    BitMatrix matrix = new MultiFormatWriter().encode(str,
        BarcodeFormat.QR_CODE, 400, 400, hints);
    int width = matrix.getWidth();
    int height = matrix.getHeight();
    int halfW = width / 2;
    int halfH = height / 2;
    int[] pixels = new int[width * height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
            && y > halfH - IMAGE_HALFWIDTH
            && y < halfH + IMAGE_HALFWIDTH) {
          pixels[y * width + x] = mBitmap.getPixel(x - halfW
              + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
        } else {
          if (matrix.get(x, y)) {
            pixels[y * width + x] = 0xff000000;
          } else {
            pixels[y * width + x] = 0xffffffff;
          }
        }
      }
    }
    Bitmap bitmap = Bitmap.createBitmap(width, height,
        Bitmap.Config.ARGB_8888);
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

    return bitmap;
  }
}
