package com.example.cte1.stack_overflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ListViewItem> data;
    private int layout;

    public ListviewAdapter(Context context, int layout, ArrayList<ListViewItem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }

    @Override
    public int getCount(){return data.size();}

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
/*
    public String getmid(int position){return data.get(position).getmid();}
    public String getbtitle(int position){return data.get(position).getbtitle();}
    public String getrecommend(int position){return data.get(position).getrecommend();}
    public String getbhits(int position){return data.get(position).getbhits();}
    public String getbdate(int position){return data.get(position).getbdate();}
    */
    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }

        ListViewItem listviewitem=data.get(position);

        TextView mid = (TextView)convertView.findViewById(R.id.mid);
        mid.setText(listviewitem.getmid());

        TextView btitle = (TextView)convertView.findViewById(R.id.btitle);
        btitle.setText(listviewitem.getbtitle());

        TextView recommend = (TextView)convertView.findViewById(R.id.recommend);
        recommend.setText(listviewitem.getrecommend());

        TextView bhits = (TextView)convertView.findViewById(R.id.bhits);
        bhits.setText(listviewitem.getbhits());

        TextView bdate=(TextView)convertView.findViewById(R.id.bdate);
        bdate.setText(listviewitem.getbdate());

        return convertView;
    }
}