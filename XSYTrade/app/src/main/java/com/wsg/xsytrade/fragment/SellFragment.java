package com.wsg.xsytrade.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wsg.xsytrade.R;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.fragment
 * 文件名：SellFragment
 * 创建者：wsg
 * 创建时间：2017/9/16  21:37
 * 描述：闲置求售
 */

public class SellFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, null);
        return view;
    }
}
