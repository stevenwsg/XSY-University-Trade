package com.wsg.xsytrade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.entity.Sell;

import java.util.List;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.adapter
 * 文件名：SellAdapter
 * 创建者：wsg
 * 创建时间：2017/9/22  15:27
 * 描述：闲置求购适配器
 */

public class SellAdapter extends BaseAdapter {
    private Context mContext;
    private List<Sell> mList;
    //布局加载器
    private LayoutInflater inflater;
    private Sell data;

    public SellAdapter(Context mContext, List<Sell> mList) {
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
            view=inflater.inflate(R.layout.item_sell,null);

            viewHolder1.iv_logo=(ImageView) view.findViewById(R.id.sell_item_logo);
            viewHolder1.tv_name=(TextView)view.findViewById(R.id.sell_item_name);
            viewHolder1.tv_title=(TextView)view.findViewById(R.id.sell_item_title);
            viewHolder1.tv_content=(TextView)view.findViewById(R.id.sell_item_content);
            viewHolder1.iv_message=(ImageView) view.findViewById(R.id.sell_item_message);

            //设置缓存
            view.setTag(viewHolder1);
        }
        else {
            viewHolder1 = (ViewHolder)view.getTag();
        }



        //设置数据
        data=mList.get(i);
        viewHolder1.tv_name.setText(data.getName());
        viewHolder1.tv_title.setText(data.getTitle());
        viewHolder1.tv_content.setText(data.getContent());
        return view;
    }




    class ViewHolder{
        private ImageView iv_logo;
        private TextView tv_name;
        private TextView tv_title;
        private TextView tv_content;
        private  ImageView iv_message;
    }



}
