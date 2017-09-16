package com.wsg.xsytrade.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.MyUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：RegisteredActivity
 * 创建者：wsg
 * 创建时间：2017/9/16  19:17
 * 描述：注册页面
 */

public class RegisteredActivity extends BaseActivity {
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.rb_boy)
    RadioButton rbBoy;
    @BindView(R.id.rb_girl)
    RadioButton rbGirl;
    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btnRegistered)
    Button btnRegistered;

    //性别
    private boolean isGender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegistered)
    public void onViewClicked() {
        //获取到输入框的值
        String name = etUser.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();


        //判断是否为空
        if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) &
                !TextUtils.isEmpty(pass) &
                !TextUtils.isEmpty(password) &
                !TextUtils.isEmpty(email)) {

            //判断两次输入的密码是否一致
            if (pass.equals(password)) {

                //先把性别判断一下
                mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.rb_boy) {
                            isGender = true;
                        } else if (checkedId == R.id.rb_girl) {
                            isGender = false;
                        }
                    }
                });

                //判断简介是否为空
                if (TextUtils.isEmpty(desc)) {
                    desc = getString(R.string.text_nothing);
                }

                //注册
                MyUser user = new MyUser();
                user.setUsername(name);
                user.setPassword(password);
                user.setEmail(email);
                user.setAge(Integer.parseInt(age));
                user.setSex(isGender);
                user.setDesc(desc);

                user.signUp(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if(e==null){
                            Toast.makeText(RegisteredActivity.this, R.string.text_registered_successful, Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(RegisteredActivity.this, getString(R.string.text_registered_failure) + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, R.string.text_two_input_not_consistent, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.text_tost_empty), Toast.LENGTH_SHORT).show();
        }
    }
}
