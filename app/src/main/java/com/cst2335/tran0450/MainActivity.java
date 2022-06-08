package com.cst2335.tran0450;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_constraint);
         Switch onOffSwitch = (Switch) findViewById(R.id.on_off_switch);
          onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  String message = getString(R.string.snackbar_message);
                  String checked = getString(R.string.checked);
                  String notChecked = getString(R.string.unchecked);
                  Snackbar snackbar;
                if(buttonView.isChecked()){
                    snackbar = Snackbar.make(
                            findViewById(R.id.on_off_switch),
                            message+" "+checked,
                            Snackbar.LENGTH_LONG);
                    onOffSwitch.setText(R.string.checked);

                }
                else {
                    snackbar = Snackbar.make(
                            findViewById(R.id.on_off_switch),
                            message+" "+notChecked,
                            Snackbar.LENGTH_LONG);
                    onOffSwitch.setText(R.string.unchecked);
                }
                  snackbar.setAction(R.string.undo, click ->
                          buttonView.setChecked(!isChecked)).show();
              }
          });
    }
    public void showToast(View view) {
        Toast.makeText(
                this,
                this.getResources().getString(R.string.toast_message),
                Toast.LENGTH_LONG).show();
    }





}