package com.example.dev.mistakeerror.error_component;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dev.mistakeerror.R;

import java.util.ArrayList;

/**
 * Created by dev on 20.01.17..
 */

public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.MyViewHolder> {

    private ArrayList<Item> items;
    private View view;

    ErrorAdapter(ArrayList<Item> items){
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.error_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item s = items.get(position);
        holder.textView1.setText(s.getTitle());
        holder.textView2.setText(s.getDate());
        holder.textView3.setText("Text3");
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;
        TextView textView3;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) view.findViewById(R.id.TV1);
            textView2 = (TextView) view.findViewById(R.id.TV2);
            textView3 = (TextView) view.findViewById(R.id.TV3);
        }
    }
}
