package com.example.dev.mistakeerror.error_component;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev.mistakeerror.R;
import com.example.dev.mistakeerror.database.DBHelper;
import com.example.dev.mistakeerror.database.Item;
import com.example.dev.mistakeerror.shownote.ShowNote;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by dev on 20.01.17..
 */

public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.MyViewHolder> {

    private ArrayList<Item> items;
    private View view;
    private Context context;


    ErrorAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.error_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item s = items.get(position);
        holder.titleTextView.setText(s.getTitle());
        holder.dateText.setText(s.getDate());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView dateText;
        Button buttonRemove;
        DBHelper dbHelper;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) view.findViewById(R.id.TV1);
            dateText = (TextView) view.findViewById(R.id.TV2);
            buttonRemove = (Button) view.findViewById(R.id.BTNRemove);
            itemView.setOnClickListener(this);


            dbHelper = new DBHelper(context);

            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag", "removenote click");
                    ErrorDialog errorDialog = new ErrorDialog(context, ErrorAdapter.MyViewHolder.this);
                    errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    errorDialog.show();
//                    removeNote();

                }
            });
        }

        public void removeNote() {
            dbHelper.removeNote(items.get(getAdapterPosition()).getId());
            items.remove(getAdapterPosition());
            notifyDataSetChanged();
            Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show();
        }


        public void showNote(Integer position) {
            position = getAdapterPosition();
            Intent intent = new Intent(context, ShowNote.class);
            intent.putExtra("id", items.get(position).getId());
            intent.putExtra("title", titleTextView.getText().toString());
            intent.putExtra("date", dateText.getText());
            context.startActivity(intent);
        }

        public void test(){
            Log.d("tag", "test working");
        }

        @Override
        public void onClick(View v) {
            showNote(getAdapterPosition());
            Log.d(TAG, "onClick: " + getAdapterPosition());
        }
    }
}
