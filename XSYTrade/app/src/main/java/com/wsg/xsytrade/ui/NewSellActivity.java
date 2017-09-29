package com.wsg.xsytrade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.MyUser;
import com.wsg.xsytrade.entity.Sell;
import com.wsg.xsytrade.util.L;
import com.wsg.xsytrade.util.UtilTools;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.newsell_pt)
    ImageView newsellPt;
    @BindView(R.id.newsell_ll)
    LinearLayout newsellLl;
    private Sell sell;
    private String mname;
    private String image;
    private String mtitle;
    private String mcontent;
    private List<LocalMedia> selectList;
    private List<String> mphoto=new ArrayList<String>();

    private  int  a=0;

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


    private void saveSell() {
        sell.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(NewSellActivity.this, "添加成功~~~", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NewSellActivity.this, "添加失败~~~~~~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick({R.id.newsell_pt, R.id.newsell_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.newsell_pt:
                toPictures();
                break;
            case R.id.newsell_bt:


                if(a==0){
                    Toast.makeText(NewSellActivity.this, "亲，必须添加图片哦~~~~~~", Toast.LENGTH_SHORT).show();
                    return;

                }


                MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
                mname = userInfo.getUsername();
                image = userInfo.getImage();
                mtitle = newsellTitle.getText().toString().trim();
                mcontent = newsellContent.getText().toString().trim();


                if (!TextUtils.isEmpty(mname) && !TextUtils.isEmpty(mtitle) && !TextUtils.isEmpty(mcontent)) {
                    sell = new Sell();
                    sell.setName(mname);
                    sell.setImage(image);
                    sell.setTitle(mtitle);
                    sell.setContent(mcontent);
                    //保存图片字节流
                    sell.setPhoto(mphoto);

                    L.d(Integer.toString(mphoto.size()));


                    saveSell();


                } else {
                    Toast.makeText(NewSellActivity.this, "亲，输入框不能为空哦~~~", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    private void toPictures() {
        PictureSelector.create(NewSellActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


//    @Override
//    public void onActivityReenter(int resultCode, Intent data) {
//        super.onActivityReenter(resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (resultCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片选择结果回调
//                    selectList = PictureSelector.obtainMultipleResult(data);
//                    // 例如 LocalMedia 里面返回三种path
//                    // 1.media.getPath(); 为原图path
//                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
//                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
//                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//
//
//                    ImageView[] imageViews = new ImageView[selectList.size()];
//                    for (int i = 0; i < imageViews.length; i++) {
//                        ImageView imageView = new ImageView(this);
//                        imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
//                        Glide.with(this).load(selectList.get(i).getPath()).into(imageView);
//                        imageViews[i] = imageView;
//
//                        newsellLl.addView(imageView);
//                    }
//
//
//
//
//                    break;
//            }
//        }
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    a=5;
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的



                    ImageView[] imageViews = new ImageView[selectList.size()];
                    for (int i = 0; i < imageViews.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                        Glide.with(this).load(selectList.get(i).getPath()).into(imageView);
                        //转换成字符流并添加到，集合

                        imageViews[i] = imageView;
                        String s=selectList.get(i).getPath();
                        mphoto.add(UtilTools.putImage(this,s));


                        newsellLl.addView(imageView);
                    }



                    break;
            }
        }
    }





}


