package com.cst2335.tran0450;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
    FrameLayout frameLayout;
    int positionClicked = 0;
    static Toast t;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        context =getApplicationContext();
        listView = (ListView) findViewById(R.id.listView);
         boolean isTablet = findViewById(R.id.frameLayout) != null;

        inputText = (EditText) findViewById(R.id.inputText) ;
        messages = new ArrayList<Message>();

        listView.setLongClickable(true);
        listView.setClickable(true);

        loadDataFromDatabase();


        adapter = new MyListAdapter(ChatRoomActivity.this, messages);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("MESSAGE", messages.get(i).getMessage() );
            dataToPass.putInt("POSITION", i);
            dataToPass.putLong("ID",l);
            dataToPass.putInt("TYPE", messages.get(i).getType());

            if(isTablet)
            {
                DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            onDelete(position,adapter);
            return true;
        });


        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(click -> {
            String text = inputText.getText().toString();
            if (text == null || text.length() == 0) {
                makeToast("Enter an item.");
            } else {
                ContentValues newRowValues = new ContentValues();
                newRowValues.put(MyOpener.COL_MESSAGE,text);
                newRowValues.put(MyOpener.COL_TYPE,0);
                long id = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

                messages.add(new Message(inputText.getText().toString(),0,id));;
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
                ContentValues newRowValues = new ContentValues();
                newRowValues.put(MyOpener.COL_MESSAGE,text);
                newRowValues.put(MyOpener.COL_TYPE,1);
                db.insert(MyOpener.TABLE_NAME, null, newRowValues);
                long id = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

                messages.add(new Message(inputText.getText().toString(),1,id));;
                inputText.setText("");
            }

            listView.setAdapter(adapter);
        });


    }
    private void loadDataFromDatabase() {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGE, MyOpener.COL_TYPE};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int messageColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGE);
        int typeColIndex = results.getColumnIndex(MyOpener.COL_TYPE);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String message = results.getString(messageColumnIndex);
            int type = results.getInt(typeColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            messages.add(new Message(message,type,id));
        }
    }


        private static void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }

    public void onDelete (int index, MyListAdapter adapter){
        Long onDeleteMessageId = messages.get(index).getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
        builder.setTitle(R.string.alertTitle);
        builder.setMessage(getText(R.string.alertMessage1)+""+ index +"\n"+getText(R.string.alertMessage2)+""+onDeleteMessageId);



        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes,(dialog, which) -> {removeItem(index);});
        builder.setNegativeButton(R.string.No, (dialog, which) -> {dialog.cancel();});
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }


    protected void removeItem(int i) {
        Message c = messages.get(i);
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
        messages.remove(i);
      adapter.notifyDataSetChanged();
    }





//    protected void updateContact(Message c)
//    {
//        //Create a ContentValues object to represent a database row:
//        ContentValues updatedValues = new ContentValues();
//        updatedValues.put(MyOpener.COL_TYPE, c.getType());
//        updatedValues.put(MyOpener.COL_MESSAGE, c.getMessage());
//
//        //now call the update function:
//        db.update(MyOpener.TABLE_NAME, updatedValues, MyOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
//    }


}