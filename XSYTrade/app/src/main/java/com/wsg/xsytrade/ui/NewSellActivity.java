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
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.MyUser;
import com.wsg.xsytrade.entity.Sell;
import com.wsg.xsytrade.util.DemiUitls;
import com.wsg.xsytrade.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：NewSellActivity
 * 创建者：wsg
 * 创建时间：2017/9/21  19:24
 * 描述：添加代售商品
 */

public class NewSellActivity extends BaseActivity {

    @BindView(R.id.iv_addphoto)
    ImageView ivAddphoto;
    @BindView(R.id.newsell_ll)
    LinearLayout newsellLl;
    private Sell sell;
    private String mname;
    private String image;
    private String mtitle;
    private String mcontent;
    private List<LocalMedia> selectList;
    private List<String> mphoto = new ArrayList<String>();

    private int a = 0;
    private String path;

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

    @OnClick({R.id.newsell_bt,R.id.iv_addphoto})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.newsell_bt:


                if (a == 0) {
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
                    final String[] filePaths = new String[selectList.size()];
                    for (int i = 0; i <selectList.size() ; i++) {
                        filePaths[i] =selectList.get(i).getPath() ;
                    }
                    BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> list, List<String> list1) {
                            //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                            //2、urls-上传文件的完整url地址
                            if(list1.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                                //do something

                                L.d(list1.toString());
                                sell.getPhoto().addAll(list1);
                                saveSell();
                            }
                        }

                        @Override
                        public void onProgress(int i, int i1, int i2, int i3) {
                            Toast.makeText(NewSellActivity.this,"图片正在上传请稍等",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(int i, String s) {
                            Toast.makeText(NewSellActivity.this,i+s,Toast.LENGTH_LONG).show();
                        }
                    });








                } else {
                    Toast.makeText(NewSellActivity.this, "亲，输入框不能为空哦~~~", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.iv_addphoto:
                toPictures();
                break;
        }
    }


    private void toPictures() {
        PictureSelector.create(NewSellActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compress(true)// 是否压缩 true or false
                .compressMode(LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .compressMaxKB(50)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    a = 5;
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的


                    ImageView[] imageViews = new ImageView[selectList.size()];

                    L.d(Integer.toString(selectList.size()));

                    for (int i = 0; i < imageViews.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(DemiUitls.px2dip(this,100), DemiUitls.px2dip(this,100)));
                        Glide.with(this).load(selectList.get(i).getPath()).into(imageView);
                        //转换成字符流并添加到，集合

                        imageViews[i] = imageView;
                        String s = selectList.get(i).getPath();


                        newsellLl.addView(imageView);
                    }


                    break;
            }
        }
    }



}


