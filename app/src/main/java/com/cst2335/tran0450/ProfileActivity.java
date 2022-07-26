package com.cst2335.tran0450;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class ProfileActivity extends AppCompatActivity {
    private ImageButton imgView;
    private Button toChat;
    private Button toWeatherForeCast;
    private EditText profileEmail;
    public static final String TAG = "PROFILE_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileEmail = (EditText) findViewById(R.id.profileEmail);
        toChat = (Button) findViewById(R.id.toChatButton);
        toWeatherForeCast = (Button) findViewById(R.id.toWeatherForecast) ;
        Intent fromMain = getIntent();
        String emailFromMain = fromMain.getStringExtra("Email");

        ;
        profileEmail.setText(emailFromMain);


        imgView = (ImageButton) findViewById(R.id.imageButton);
        ActivityResultLauncher<Intent> myPictureTakerLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                        , new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                    Intent data = result.getData();
                                    Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                                    imgView.setImageBitmap(imgbitmap); // the imageButton
                                } else if (result.getResultCode() == Activity.RESULT_CANCELED)
                                    Log.i(TAG, "User refused to capture a picture.");
                            }
                        });

        imgView.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                myPictureTakerLauncher.launch(takePictureIntent);
            }

        });

        toChat.setOnClickListener(v -> {
            Intent goToChat = new Intent(ProfileActivity.this, ChatRoomActivity.class);
//                goToProfile.putExtra("Email", inputEmail);
            startActivity(goToChat);
        });

        toWeatherForeCast.setOnClickListener(v -> {
            Intent goToWeatherForeCast = new Intent(ProfileActivity.this, WeatherForecast.class);
//                goToProfile.putExtra("Email", inputEmail);
            startActivity(goToWeatherForeCast);
        });
    }
}