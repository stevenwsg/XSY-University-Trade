package com.wsg.xsytrade.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.Feedback;
import com.wsg.xsytrade.view.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：BackActivity
 * 创建者：wsg
 * 创建时间：2017/9/18  16:19
 * 描述：建议反馈
 */

public class BackActivity extends BaseActivity {
    private CustomDialog dialog;
    @BindView(R.id.back_ed)
    EditText backEd;
    @BindView(R.id.back_bt)
    Button backBt;
    private String advice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.back_bt)
    public void onViewClicked() {
        advice=backEd.getText().toString().trim();
        if(!TextUtils.isEmpty(advice)){
            //意见反馈的逻辑，先去bmob建表
            saveFeedbackMsg(advice);

        }else {
            Toast.makeText(BackActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();

        }

    }


    /**
     * 保存反馈信息到Bmob云数据库中
     * @param msg 反馈信息
     */
    private void saveFeedbackMsg(String msg){
        Feedback feedback = new Feedback();
        feedback.setContent(msg);
        feedback.setDeviceType("android");
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(),"反馈成功~~~",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BackActivity.this,"反馈失败~~~",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }






}
