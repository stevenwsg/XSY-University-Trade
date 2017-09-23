package com.wsg.xsytrade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wsg.xsytrade.entity.Sell;

import java.util.List;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.adapter
 * 文件名：MySellAdapter
 * 创建者：wsg
 * 创建时间：2017/9/22  20:47
 * 描述：我的求购
 */

public class MySellAdapter extends BaseAdapter {

    private Context mContext;
    private List<Sell> mList;
    //布局加载器
    private LayoutInflater inflater;
    private Sell data;



    public MySellAdapter(Context mContext, List<Sell> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder1=null;
        //如果是第一次加载
        if(view==null){
            viewHolder1=new ViewHolder();
            view=inflater.inflate(com.wsg.xsytrade.R.layout.item_mysell,null);
            viewHolder1.mysell_tv_title=(TextView)view.findViewById(com.wsg.xsytrade.R.id.mysell_item_title);
            viewHolder1.mysell_iv_modify=(ImageView)view.findViewById(com.wsg.xsytrade.R.id.mysell_item_modify);
            viewHolder1.mysll_iv_delete=(ImageView) view.findViewById(com.wsg.xsytrade.R.id.mysell_item_delete);
            //设置缓存
            view.setTag(viewHolder1);
        }
        else {
            viewHolder1 = (ViewHolder)view.getTag();
        }


        //设置数据
        data=mList.get(i);
//        L.d(Integer.toString(mList.size()));
//        L.d(data.getTitle());
        viewHolder1.mysell_tv_title.setText(data.getTitle());

//        L.d(data.getTitle());
        return view;
    }


    class ViewHolder{
        private TextView mysell_tv_title;
        private ImageView mysell_iv_modify;
        private  ImageView mysll_iv_delete;
    }
}
