package com.cst2335.tran0450;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private AppCompatActivity parentActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_details, container, false);

        Bundle fromChatRoom = this.getArguments();
        if(fromChatRoom!= null) {
            String messageFromChatBox = fromChatRoom.getString("MESSAGE");
            Long IDFromChatBox = fromChatRoom.getLong("ID");
            int typeFromChatBox = fromChatRoom.getInt("TYPE");
            boolean checked = false;
            if(typeFromChatBox == 0) {
                checked = true;
            }

            TextView message = view.findViewById(R.id.messagehere);
            TextView id = view.findViewById(R.id.idhere);
            CheckBox checkBox = view.findViewById(R.id.checkbox);

            message.setText("Message:"+messageFromChatBox);
            id.setText("ID= "+IDFromChatBox);
            checkBox.setChecked(checked);
        }
        Button hideButton = (Button) view.findViewById(R.id.hideButton);
        hideButton.setOnClickListener( clk -> {

            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });



       return  view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}