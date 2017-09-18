package com.wsg.xsytrade.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.entity.MyUser;
import com.wsg.xsytrade.ui.AboutActivity;
import com.wsg.xsytrade.ui.BackActivity;
import com.wsg.xsytrade.ui.DonateActivity;
import com.wsg.xsytrade.ui.ShareActivity;
import com.wsg.xsytrade.ui.UpdateActivity;
import com.wsg.xsytrade.util.L;
import com.wsg.xsytrade.util.UtilTools;
import com.wsg.xsytrade.view.CustomDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wsg.xsytrade.R.id.btn_update_ok;
import static com.wsg.xsytrade.R.id.et_age;
import static com.wsg.xsytrade.R.id.et_desc;
import static com.wsg.xsytrade.R.id.et_sex;
import static com.wsg.xsytrade.R.id.et_username;
import static com.wsg.xsytrade.R.id.profile_image;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.fragment
 * 文件名：UserFragment
 * 创建者：wsg
 * 创建时间：2017/9/16  21:36
 * 描述：个人中心
 */

public class UserFragment extends Fragment implements View.OnClickListener {
    @BindView(profile_image)
    CircleImageView profileImage;
    @BindView(R.id.edit_user)
    TextView editUser;
    @BindView(et_username)
    EditText etUsername;
    @BindView(et_sex)
    EditText etSex;
    @BindView(et_age)
    EditText etAge;
    @BindView(et_desc)
    EditText etDesc;
    @BindView(btn_update_ok)
    Button btnUpdateOk;
    @BindView(R.id.tv_sell)
    TextView tvSell;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_donate)
    TextView tvDonate;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    Unbinder unbinder;
    @BindView(R.id.tv_share)
    TextView tvShare;


    private CustomDialog dialog;
    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        //初始化dialog
        dialog = new CustomDialog(getActivity(), 0, 0,
                R.layout.dialog_photo, R.style.pop_anim_style, Gravity.BOTTOM, 0);
        //提示框以外点击无效
        dialog.setCancelable(false);

        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        //默认是不可点击的/不可输入
        setEnabled(false);

        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        etUsername.setText(userInfo.getUsername());
        etAge.setText(userInfo.getAge() + "");
        etSex.setText(userInfo.isSex() ? getString(R.string.text_boy) : getString(R.string.text_girl_f));
        etDesc.setText(userInfo.getDesc());
    }

    private void setEnabled(boolean b) {
        etUsername.setEnabled(b);
        etSex.setEnabled(b);
        etAge.setEnabled(b);
        etDesc.setEnabled(b);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({profile_image, R.id.edit_user, btn_update_ok, R.id.tv_sell, R.id.tv_buy, R.id.tv_donate, R.id.tv_back, R.id.tv_about, R.id.tv_update,R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case profile_image:
                dialog.show();
                break;
            case R.id.edit_user:
                setEnabled(true);
                btnUpdateOk.setVisibility(View.VISIBLE);
                break;
            case btn_update_ok:
                updateUser();
                break;
            case R.id.tv_sell:
                break;
            case R.id.tv_buy:
                break;
            case R.id.tv_donate:
                startActivity(new Intent(getActivity(), DonateActivity.class));
                break;
            case R.id.tv_back:
                startActivity(new Intent(getActivity(), BackActivity.class));
                break;
            case R.id.tv_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.tv_update:
                startActivity(new Intent(getActivity(), UpdateActivity.class));
                break;
            case R.id.tv_share:
                startActivity(new Intent(getActivity(), ShareActivity.class));
                break;
        }
    }

    private void updateUser() {
        //1.拿到输入框的值
        String username = etUsername.getText().toString();
        String age = etAge.getText().toString();
        String sex = etSex.getText().toString();
        String desc = etDesc.getText().toString();

        //2.判断是否为空
        if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)) {
            //3.更新属性
            MyUser user = new MyUser();
            user.setUsername(username);
            user.setAge(Integer.parseInt(age));
            //性别
            if (sex.equals(getString(R.string.text_boy))) {
                user.setSex(true);
            } else {
                user.setSex(false);
            }
            //简介
            if (!TextUtils.isEmpty(desc)) {
                user.setDesc(desc);
            } else {
                user.setDesc(getString(R.string.text_nothing));
            }
            BmobUser bmobUser = BmobUser.getCurrentUser();
            user.update(bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        //修改成功
                        setEnabled(false);
                        btnUpdateOk.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), R.string.text_editor_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), R.string.text_editor_failure, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), getString(R.string.text_tost_empty), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;

        }

    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profileImage.setImageBitmap(bitmap);
            //保存
            UtilTools.putImageToShare(getActivity(), profileImage);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
