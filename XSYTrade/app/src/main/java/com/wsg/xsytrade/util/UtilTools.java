package com.wsg.xsytrade.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.entity.MyUser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.util
 * 文件名：UtilTools
 * 创建者：wsg
 * 创建时间：2017/9/18  16:03
 * 描述：工具类
 */

public class UtilTools {








    //保存图片到shareutils
    public static void putImageToShare(final Context mContext, ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        //第二步：利用Base64将我们的字节数组输出流转换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步：将String保存shareUtils


        L.d(imgString);

        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        userInfo.setImage(imgString);
        //更新数据
        BmobUser bmobUser = BmobUser.getCurrentUser();
        userInfo.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //修改成功
                    Toast.makeText(mContext, R.string.text_editor_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.text_editor_failure+e.toString()+e.getErrorCode(), Toast.LENGTH_SHORT).show();
                    L.i(R.string.text_editor_failure+e.toString()+e.getErrorCode());
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    L.i(e.getMessage());
                }
            }
        });





        ShareUtils.putString(mContext, "image_title", imgString);
        ShareUtils.putBoolean(mContext,"image_modify",true);



    }

    //读取图片
    public static void getImageToShare(Context mContext, ImageView imageView) {
        //1.拿到string
        String imgString = ShareUtils.getString(mContext, "image_title", "");
        if (!imgString.equals("")) {
            //2.利用Base64将我们string转换
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }




    //保存图片到服务器

    public static String putImage(final Context mContext, String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        //第一步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        //第二步：利用Base64将我们的字节数组输出流转换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步：将String保存shareUtils

        //保存到服务器

        return imgString;



    }




    //读取图片
    public static void getImage(Context mContext, ImageView imageView,String s) {
        //1.拿到string
        String imgString = s;
        if (!imgString.equals("")) {
            //2.利用Base64将我们string转换
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }






    //获取版本号
    public static String getVersion(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return mContext.getString(R.string.text_unknown);
        }
    }


        //获取屏幕
        public static int  getWidth(Context context){
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            return width;

    }

    //获取屏幕
    public static int  getHeight(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();

        return height;

    }























    /**
     * uri转path
     */
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


}
