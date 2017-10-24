package com.wsg.xsytrade.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wsg.xsytrade.MainActivity;
import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.MyUser;
import com.wsg.xsytrade.util.L;
import com.wsg.xsytrade.view.CustomDialog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wsg.xsytrade.R.id.et_name;
import static com.wsg.xsytrade.R.id.et_password;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：LoginActivity
 * 创建者：wsg
 * 创建时间：2017/9/16  17:19
 * 描述：登录页面
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(et_name)
    EditText etName;
    @BindView(et_password)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btn_registered)
    Button btnRegistered;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.btnLogi_qq)
    ImageView btnLogiQq;
    private CustomDialog dialog;


    //    QQ登录相关
    private static final String QQAPPID = "1106263164";
    private Tencent mTencent;
    private IUiListener loginListener;
    private IUiListener userInfoListener;
    private String scope;
    private UserInfo userInfo;
    ImageView img_login_qq;
    private String username;
    private String emal;
    private String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mTencent = Tencent.createInstance("1106383173", this.getApplicationContext());
        initData();
    }

    private void initData() {
        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        dialog.setCancelable(false);
    }

    @OnClick({R.id.btnLogin, R.id.btn_registered, R.id.tv_forget, R.id.btnLogi_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                //1.获取输入框的值
                final String name = etName.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    dialog.show();
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if (e == null) {



                                //注册环信  实现登录

                                //开启线程

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //因为环信的id不能重复，那咋们就是用 bmob用户系统的objectid吧

                                        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
                                        String n=userInfo.getObjectId();

                                        //注册环信用户

                                        try {
                                            EMClient.getInstance().createAccount(n,password);//同步方法
                                        } catch (HyphenateException e1) {
                                            L.i(Integer.toString(e1.getErrorCode())+e1.getDescription());
                                            e1.printStackTrace();
                                        }
                                        //登录环信

                                        EMClient.getInstance().login(n,password,new EMCallBack() {//回调
                                            @Override
                                            public void onSuccess() {
                                                EMClient.getInstance().groupManager().loadAllGroups();
                                                EMClient.getInstance().chatManager().loadAllConversations();
                                                Log.d("main", "登录聊天服务器成功！");
                                            }

                                            @Override
                                            public void onProgress(int progress, String status) {

                                            }

                                            @Override
                                            public void onError(int code, String message) {
                                                Log.d("main", "登录聊天服务器失败！");
                                            }
                                        });



                                    }
                                }).start();





                                //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();

                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败：请检查登录信息或稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_registered:
                startActivity(new Intent(LoginActivity.this,RegisteredActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;
            case R.id.btnLogi_qq:
//                Toast.makeText(LoginActivity.this,"等待等待再等待",Toast.LENGTH_SHORT).show();

                scope = "all";
                initLoginListener();
                initUserInfoListener();
                qqLogin();
                break;
        }
    }
    /*
qq登录开始
 */
    private void qqLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(LoginActivity.this, scope, loginListener);

        }
    }
    private void initLoginListener() {
        loginListener = new IUiListener() {
            /**
             * {"ret":0,"pay_token":"D3D678728DC580FBCDE15722B72E7365",
             * "pf":"desktop_m_qq-10000144-android-2002-",
             * "query_authority_cost":448,
             * "authority_cost":-136792089,
             * "openid":"015A22DED93BD15E0E6B0DDB3E59DE2D",
             * "expires_in":7776000,
             * "pfkey":"6068ea1c4a716d4141bca0ddb3df1bb9",
             * "msg":"",
             * "access_token":"A2455F491478233529D0106D2CE6EB45",
             * "login_cost":499}
             */
            @Override
            public void onComplete(Object value) {
                // TODO Auto-generated method stub
                if(value==null){
                    Toast.makeText(getApplicationContext(),"返回结果为空",Toast.LENGTH_LONG).show();
                    return;
                }
                System.out.println("有数据返回..");
                try {
                    JSONObject jo = (JSONObject) value;
                    if(null!=jo&&jo.length()==0){
                        Toast.makeText(getApplicationContext(),"返回结果为空",Toast.LENGTH_LONG).show();
                    }
                    //处理结果
                    System.out.println(jo.toString());

                    String msg = jo.getString("msg");
                    int ret = jo.getInt("ret");

                    System.out.println("json=" + String.valueOf(jo));


                    System.out.println("json=" + String.valueOf(jo));

                    System.out.println("msg="+msg);
                    if (ret == 0) {
                        String openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        //下面两个方法非常重要，否则会出现client request's parameters are invalid, invalid openid
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);


                        System.out.println("开始获取用户信息");
                        if(mTencent.getQQToken() == null){
                            System.out.println("qqtoken ====================== null");
                        }

                        userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
                        userInfo.getUserInfo(userInfoListener);
                    }
//                  if (ret == 0) {
//                      Toast.makeText(MainActivity.this, "登录成功",
//                              Toast.LENGTH_LONG).show();
//
//                      String openID = jo.getString("openid");
//                      String accessToken = jo.getString("access_token");
//                      String expires = jo.getString("expires_in");
//                      mTencent.setOpenId(openID);
//                      mTencent.setAccessToken(accessToken, expires);
//                  }






                } catch (Exception e) {
                    // TODO: handle exception
                    e.toString();
                }

            }


            @Override
            public void onError(UiError uiError) {
                Toast.makeText(getApplicationContext(),"登录错误",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),"登录取消",Toast.LENGTH_LONG).show();
            }
        };
    }


    private void initUserInfoListener() {
        userInfoListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                System.out.print(arg0.toString());
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                System.out.print("cancel");
            }
            /**
             * {"is_yellow_year_vip":"0","ret":0,
             * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
             * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "nickname":"攀爬←蜗牛","yellow_vip_level":"0","is_lost":0,"msg":"",
             * "city":"黄冈","
             * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
             * "vip":"0","level":"0",
             * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "province":"湖北",
             * "is_yellow_vip":"0","gender":"男",
             * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
             */
            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null){
                    System.out.println("agr0=1234567null");
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    System.out.println("json======" + String.valueOf(jo));
                    nickName=jo.getString("nickname");
                    emal=jo.getString("figureurl_qq_1");
                  System.out.println("nickname"+nickName);
                  Toast.makeText(LoginActivity.this, "你好，" + nickName,
                          Toast.LENGTH_LONG).show();




                    dialog.show();

                    //我草实现逻辑，正他妈的哎

                    //根据账号信息截取QQ号，获得邮箱

                    MyUser user = new MyUser();
                    user.setUsername(nickName);
                    user.setPassword("123456");
                    user.setEmail(emal +"@qq.com");
                    user.setAge(20);
                    user.setSex(true);
                    user.setDesc(getString(R.string.text_nothing));
                    user.signUp(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if(e==null){
                                L.i("Bmob注册成功");
                            }else{
                                L.i("Bmob注册失败");
                            }
                        }
                    });





                    //登录bmob
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if(e==null){
                                L.i("Bmob登陆成功");



                                //注册环信

                                MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
                                String n=userInfo.getObjectId();

                                //注册环信用户

                                try {
                                    EMClient.getInstance().createAccount(n,"123456");//同步方法
                                } catch (HyphenateException e1) {
                                    e1.printStackTrace();
                                    L.i(Integer.toString(e1.getErrorCode())+e1.getDescription());
                                }


                                //登录环信

                                EMClient.getInstance().login(n,"123456",new EMCallBack() {//回调
                                    @Override
                                    public void onSuccess() {
                                        EMClient.getInstance().groupManager().loadAllGroups();
                                        EMClient.getInstance().chatManager().loadAllConversations();
                                        L.i("登录聊天服务器成功！");
                                    }

                                    @Override
                                    public void onProgress(int progress, String status) {

                                    }

                                    @Override
                                    public void onError(int code, String message) {
                                        L.i("登录聊天服务器失败！"+message);
                                    }
                                });


                                dialog.dismiss();
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                L.i("Bmob登陆失败");
                            }

                        }
                    });











                    if(ret == 100030){
                        //权限不够，需要增量授权
                        Runnable r = new Runnable() {
                            public void run() {
                                mTencent.reAuth(LoginActivity.this, "all", new IUiListener() {

                                    @Override
                                    public void onError(UiError arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onComplete(Object arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onCancel() {
                                        // TODO Auto-generated method stub

                                    }
                                });
                            }
                        };

                        LoginActivity.this.runOnUiThread(r);
                    }else{
                        username = jo.getString("nickname");
//                        sex = jo.getString("gender");

                        if(isUsernameExists())
                        {

                        }
                        else {

                        }
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }


            }


        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //由于在一些低端机器上，因为内存原因，无法返回到回调onComplete里面，是以onActivityResult的方式返回
        if(requestCode==11101&&resultCode==RESULT_OK){
            //处理返回的数据/
            if(data==null){
                Toast.makeText(getApplicationContext(),"返回数据为空",Toast.LENGTH_LONG);
            }else{
                Tencent.handleResultData(data,loginListener);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getUserId(String username) {
        String id = null;
        //打开或创建test.db数据库
        SQLiteDatabase db = openOrCreateDatabase("qingning.db", Context.MODE_PRIVATE, null);

        //打开或创建test.db数据库
        // SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("select * from users where username=?",new String[]{username});
        System.out.println("username"+c.getCount());

        c.moveToFirst();
        for(int j=0;j<c.getCount();j++)
        { c.moveToPosition(j);
            id=c.getString(c.getColumnIndex("id"));
            System.out.println("id:"+id);
        }
        db.close();
        return id;
    }

    private boolean isUsernameExists(){
        //打开或创建test.db数据库
        SQLiteDatabase db = openOrCreateDatabase("qingning.db", Context.MODE_PRIVATE, null);



        Cursor c = db.rawQuery("select * from users where username=?",new String[]{username});
        if(c.getCount()==1){
            db.close();
            return true;
        }
        else{
            System.out.println("false:cccc");
            System.out.println("username"+c.getCount());
        }
        return false;
    }

    /*
    qq登录结束
     */
}
