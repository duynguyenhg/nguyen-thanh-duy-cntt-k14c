package com.example.garuuu.dubaott;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Thoitiet> arrayList;

    public CustomAdapter(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View View, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //tao bien inflater
        View = inflater.inflate(R.layout.dong_listview,null);

        Thoitiet thoitiet = arrayList.get(i); //goi class tao bien thoi tiet. lay gia tri trong bien, truyen vao bien i
        TextView txtDay = (TextView) View.findViewById(R.id.textviewNgay);
        TextView txtStatus = (TextView) View.findViewById(R.id.textviewTrangthai);
        TextView txtMax = (TextView) View.findViewById(R.id.textviewMaxTemp);
        TextView txtMin = (TextView) View.findViewById(R.id.textviewMinTemp);
        ImageView imgStatus = (ImageView) View.findViewById(R.id.imageviewTrangthai);

        txtDay.setText(thoitiet.Day);
        txtStatus.setText(thoitiet.Status);
        txtMax.setText(thoitiet.MaxTemp+"°C");
        txtMin.setText(thoitiet.MinTemp+"°C");

        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoitiet.Image+".png").into(imgStatus); //dung thu vien picaso load anh tu duong dan ve
        return View;
    }
}
