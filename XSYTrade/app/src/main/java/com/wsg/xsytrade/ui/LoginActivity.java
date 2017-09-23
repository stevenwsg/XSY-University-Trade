package com.wsg.xsytrade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wsg.xsytrade.MainActivity;
import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.MyUser;
import com.wsg.xsytrade.view.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
                Toast.makeText(LoginActivity.this,"等待等待再等待",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
