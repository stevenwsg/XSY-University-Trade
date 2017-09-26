package com.wsg.xsytrade.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.MyUser;
import com.wsg.xsytrade.entity.Sell;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：NewSellActivity
 * 创建者：wsg
 * 创建时间：2017/9/21  19:24
 * 描述：添加代售商品
 */

public class NewSellActivity extends BaseActivity {
    private Sell sell;
    private String mname;
    private String image;
    private String mtitle;
    private  String mcontent;

    @BindView(R.id.newsell_title)
    EditText newsellTitle;
    @BindView(R.id.newsell_content)
    EditText newsellContent;
    @BindView(R.id.newsell_bt)
    Button newsellBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsell);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.newsell_bt)
    public void onViewClicked() {
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        mname=userInfo.getUsername();
        image=userInfo.getImage();
        mtitle=newsellTitle.getText().toString().trim();
        mcontent=newsellContent.getText().toString().trim();



        if (!TextUtils.isEmpty(mname)&& !TextUtils.isEmpty(mtitle)&&!TextUtils.isEmpty(mcontent)){
            sell=new Sell();
            sell.setName(mname);
            sell.setImage(image);
            sell.setTitle(mtitle);
            sell.setContent(mcontent);


            saveSell();



        }
        else {
            Toast.makeText(NewSellActivity.this,"亲，输入框不能为空哦~~~",Toast.LENGTH_SHORT).show();

        }

    }


    private void saveSell(){
        sell.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(NewSellActivity.this,"添加成功~~~",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(NewSellActivity.this,"添加失败~~~~~~",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
