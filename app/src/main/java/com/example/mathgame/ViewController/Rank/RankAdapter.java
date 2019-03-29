package com.example.mathfastgame.ViewController.Rank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mathfastgame.Model.User;
import com.example.mathfastgame.R;

import java.util.ArrayList;

public class RankAdapter extends BaseAdapter {
    ArrayList<User> arrData;
    Context context;

    public RankAdapter(ArrayList<User> arrData, Context context) {
        this.arrData = arrData;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (arrData.size()<=5){
            return arrData.size();
        }else {
            return 5;
        }

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_rank,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        User user=arrData.get(position);
        viewHolder.setUpView(user,position);
        return convertView;
    }
    class ViewHolder{
        TextView txtSTT,txtNameUser,txtPoint;
        ImageView imgCham;

        public ViewHolder(View view) {
            txtSTT=view.findViewById(R.id.txt_stt);
            txtNameUser=view.findViewById(R.id.txt_name_user);
            txtPoint=view.findViewById(R.id.txt_point);
            imgCham=view.findViewById(R.id.img_cham);
        }
        public void setUpView(User user,int pos){
            txtSTT.setText((pos+1)+"");
            txtNameUser.setText(user.getNameUser());
            txtPoint.setText(user.getPoint()+"");
            if (pos==0){
                imgCham.setVisibility(View.VISIBLE);
                txtSTT.setVisibility(View.GONE);
            }else {
                imgCham.setVisibility(View.GONE);
                txtSTT.setVisibility(View.VISIBLE);
            }
        }
    }
}
