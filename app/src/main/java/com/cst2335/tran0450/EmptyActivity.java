package com.cst2335.tran0450;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        Bundle fromChatRoom = this.getIntent().getExtras();
        if(fromChatRoom!= null) {
            String messageFromChatBox = fromChatRoom.getString("MESSAGE");
            Long IDFromChatBox = fromChatRoom.getLong("ID");
            int typeFromChatBox = fromChatRoom.getInt("TYPE");
            boolean checked = false;
            if (typeFromChatBox == 0) {
                checked = true;
            }



            DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, dFragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment. Calls onCreate() in DetailFragment


        }
    }
}