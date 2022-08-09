package com.cst2335.tran0450;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Message> message;
    private int type;


    public MyListAdapter(Context context, ArrayList<Message> message) {
        this.context = context;
        this.message = message;
    }


    @Override
    public int getCount() {
        return message.size();
    }

    @Override
    public Object getItem(int position) {
        return message.get(position);
    }

    public int getType(int position) {
        return message.get(position).getType();
    }
    public String getMessage(int position) {
        return message.get(position).getMessage();
    }
    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView;

//        LayoutInflater inflater = (LayoutInflater) this.getSystemService;
        if(getType(position) == 0) {
            newView = LayoutInflater.from(context).inflate(R.layout.activity_list_view_row_send, parent, false);
            ;
        }
        else {
            newView = LayoutInflater.from(context).inflate(R.layout.activity_list_view_row_receive, parent, false);
            ;

        }


        TextView name = newView.findViewById(R.id.text_view_item_name);
        ImageView image = newView.findViewById(R.id.listImage);

//        name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("Clicked", "Clicked");
//            }
//        });
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("Clicked", "Clicked");
//            }
//        });

        name.setText(getMessage(position));


        return newView;
    }
}