package com.cst2335.tran0450;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity{
    private ListView listView;
    private ArrayList<Message> messages;
    private Button sendButton;
    private Button receiveButton;
    private EditText inputText;
    static Context context;
    private MyListAdapter adapter;
    static Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        
        context =getApplicationContext();
        listView = (ListView) findViewById(R.id.listView);
        inputText = (EditText) findViewById(R.id.inputText) ;
        messages = new ArrayList<Message>();
        messages.add(new Message("aa",1));
        messages.add(new Message("CCC",0));
        listView.setLongClickable(true);

        adapter = new MyListAdapter(ChatRoomActivity.this, messages);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                onDelete(position,adapter);
                return true;
            }

        });


        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(click -> {
            String text = inputText.getText().toString();
            if (text == null || text.length() == 0) {
                makeToast("Enter an item.");
            } else {
                messages.add(new Message(inputText.getText().toString(),0));;
                inputText.setText("");
            }

            listView.setAdapter(adapter);
        });

        receiveButton = (Button) findViewById(R.id.receiveButton);
        receiveButton.setOnClickListener(click -> {
            String text = inputText.getText().toString();
            if (text == null || text.length() == 0) {
                makeToast("Enter an item.");
            } else {
                messages.add(new Message(inputText.getText().toString(),1));;
                inputText.setText("");
            }

            listView.setAdapter(adapter);
        });


    }


    private static void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }

    public void onDelete (int index, MyListAdapter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
        builder.setTitle(R.string.alertTitle);
        builder.setMessage(getText(R.string.alertMessage1)+""+ index +"\n"+getText(R.string.alertMessage2)+""+adapter.getItemId(index));



        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes,(dialog, which) -> {removeItem(index);});
        builder.setNegativeButton(R.string.No, (dialog, which) -> {dialog.cancel();});
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }


    public void removeItem(int i) {
      messages.remove(i);
      adapter.notifyDataSetChanged();
    }


}