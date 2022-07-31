package id.co.sm.portalmocasender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String UserToken;
    private EditText title,message,token;
    private Button sendbtn, send2btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for sending notification to all

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        // for settings for particular user
         FirebaseInstanceId.getInstance().getInstanceId()
            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                 @Override
                 public void onComplete(@NonNull Task<InstanceIdResult> task) {
                 if(task.isSuccessful()){
                     UserToken = Objects.requireNonNull(task.getResult()).getToken();

                     Log.d("tooo","Token"+UserToken);

                 }
            }
         });



        title = findViewById(R.id.title_id);
        message = findViewById(R.id.message_id);
        message = findViewById(R.id.token_id);

        sendbtn =findViewById(R.id.send_btn);
        send2btn =findViewById(R.id.send_btn);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().toString().isEmpty() && !message.getText().toString().isEmpty()){

                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",title.getText().toString(),
                            message.getText().toString(),getApplicationContext(),MainActivity.this);
                    notificationsSender.SendNotifications();
                }else {
                    Toast.makeText(MainActivity.this, "Please give your data", Toast.LENGTH_LONG).show();
                }
            }
        });

        send2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!title.getText().toString().isEmpty() && !message.getText().toString().isEmpty()&&
                !token.getText().toString().isEmpty()){

                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token.getText().toString()
                            message.getText().toString(),getApplicationContext(),MainActivity.this);
                    notificationsSender.SendNotifications();
                }else {
                    Toast.makeText(MainActivity.this, "Enter Details", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}