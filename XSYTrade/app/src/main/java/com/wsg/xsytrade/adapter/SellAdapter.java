package com.wsg.xsytrade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.entity.Sell;
import com.wsg.xsytrade.util.UtilTools;

import java.util.List;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.adapter
 * 文件名：SellAdapter
 * 创建者：wsg
 * 创建时间：2017/9/22  15:27
 * 描述：闲置求购适配器
 */

public class SellAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private List<Sell> mList;
    //布局加载器
    private LayoutInflater inflater;
    private Sell data;

        private Callback mCallback;


           /**

            * 自定义接口，用于回调按钮点击事件到Activity

            * @author Ivan Xu

            * 2014-11-26

            */





           public interface Callback {

               public void click(View v);

           }




    public SellAdapter(Context mContext, List<Sell> mList, Callback callback) {
        this.mContext = mContext;
        this.mList = mList;
        mCallback = callback;
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
            viewHolder1.ll_ll=(LinearLayout) view.findViewById(R.id.sell_item_ll);


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


        viewHolder1.iv_message.setOnClickListener(this);
        viewHolder1.iv_message.setTag(i);

        if (data.getImage()!=null){
            UtilTools.getImage(mContext,viewHolder1.iv_logo,mList.get(i).getImage());
        }

//        list!=null && !list.isEmpty()

//            int a=data.getPhoto().size();
//
//            L.d(Integer.toString(a));
//
//            ImageView[] imageViews = new ImageView[a];
//
//            for (int j = 0; j <a; j++) {
//                final ImageView imageView = new ImageView(mContext);
//                imageView.setLayoutParams(new LinearLayout.LayoutParams(UtilTools.getWidth(mContext)/3, UtilTools.getHeight(mContext)/3));
//                UtilTools.getImage(mContext,imageView,data.getPhoto().get(j));
//                imageViews[j] = imageView;
//                viewHolder1.ll_ll.addView(imageViews[j]);
//
//
//            }



        //将逻辑改成必须添加图片，就不用考虑这部分了


//        else {
//            //还是加个什么吧，要不然的话，老崩溃，我也不知道为什么
//            //稍微加点东西，让人感觉不到，不可见就OK了
//
//            TextView t=new TextView(mContext);
//            t.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2));
//            t.setVisibility(View.INVISIBLE);
//            viewHolder1.ll_ll.addView(t);
//
//
//
//
//        }





        return view;
    }

    //响应按钮点击事件,调用子定义接口，并传入View
    @Override
    public void onClick(View view) {
        mCallback.click(view);
    }


    class ViewHolder{
        private LinearLayout ll_ll;
        private ImageView iv_logo;
        private TextView tv_name;
        private TextView tv_title;
        private TextView tv_content;
        private  ImageView iv_message;
    }










}
