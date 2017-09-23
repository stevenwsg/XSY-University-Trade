package com.wsg.xsytrade;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.wsg.xsytrade.fragment.BuyFragment;
import com.wsg.xsytrade.fragment.MessageFragment;
import com.wsg.xsytrade.fragment.SellFragment;
import com.wsg.xsytrade.fragment.UserFragment;
import com.wsg.xsytrade.util.ShareUtils;
import com.wsg.xsytrade.util.StaticClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//主页面

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //去掉阴影
        getSupportActionBar().setElevation(0);
        initData();
        initViews();
    }

    private void initData() {
        ShareUtils.putBoolean(this, StaticClass.SHARE_IS_LOGIN,false);
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.text_sell));
        mTitle.add(getString(R.string.text_buy));
        mTitle.add(getString(R.string.text_message));
        mTitle.add(getString(R.string.text_user_info));



        mFragment = new ArrayList<>();
        mFragment.add(new SellFragment());
        mFragment.add(new BuyFragment());
        mFragment.add(new MessageFragment());
        mFragment.add(new UserFragment());

    }

    private void initViews() {
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
