package com.example.jamirahollis.learningsms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText sendPhoneNumber, sendMessageText = null;
    Button sendMessageButton = null;
    TextView receivedPhoneNumber, receivedMessageText = null;
    SmsManager smsManager = SmsManager.getDefault();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendPhoneNumber = findViewById(R.id.sendPhoneNumber);
        sendMessageText = findViewById(R.id.sendMessageText);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        receivedPhoneNumber = findViewById(R.id.receivedPhoneNumber);
        receivedMessageText = findViewById(R.id.receivedMessageText);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = sendPhoneNumber.getText().toString();
                String message = sendMessageText.getText().toString();
                smsManager.sendTextMessage(phoneNumber, null, message,null, null);
            }
        });
        SMSReceiver smsReceiver = new SMSReceiver(this);
        if(!isSmsPermissionGranted()){
            requestReadAndSendSmsPermission();
        }
    }

    public void displayMessage(String number, String message){
        receivedPhoneNumber.setText(number);
        receivedMessageText.setText(message);
    }

    public boolean isSmsPermissionGranted(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private  void requestReadAndSendSmsPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)){

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE}, 1);
    }
}
