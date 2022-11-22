package ArmondJsleepJS.example.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    TextView nama, email, balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        nama = findViewById(R.id.tv_userName);
        email = findViewById(R.id.tv_userEmail);
        balance = findViewById(R.id.tv_userBalance);
    }
}