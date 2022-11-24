package ArmondJsleepJS.example.jsleep_android;

import static ArmondJsleepJS.example.jsleep_android.MainActivity.ACC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ArmondJsleepJS.example.jsleep_android.model.Renter;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;

    TextView nama, email, balance;
    TextView RentName, RentAddress, RentNum;
    EditText RRName, RRAdd, RRNum;
    CardView RenterInfo, RegisterRenter;
    Button Register1, Register2, Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        nama = findViewById(R.id.tv_userName);
        email = findViewById(R.id.tv_userEmail);
        balance = findViewById(R.id.tv_userBalance);

        RentName = findViewById(R.id.tv_RenterNameIn);
        RentAddress = findViewById(R.id.tv_RenterAddressIn);
        RentNum = findViewById(R.id.tv_RenterNumberIn);

        RenterInfo = findViewById(R.id.cardviewRenterInfo);
        RegisterRenter = findViewById(R.id.cardviewRegisterRenter);

        RRName = findViewById(R.id.RegRenterName);
        RRAdd = findViewById(R.id.RegRenterAddress);
        RRNum = findViewById(R.id.RegRenterNumber);

        Register1 = findViewById(R.id.RegisterRenter);
        Register2 = findViewById(R.id.RegisterButton2);
        Cancel = findViewById(R.id.RegisterCancel);

        nama.setText(ACC.name);
        email.setText(ACC.email);
        balance.setText(Double.toString(ACC.balance));

        if (ACC.renter != null) {
            RegisterRenter.setVisibility(View.GONE);
            Register1.setVisibility(View.GONE);
        } else {
            String RName = RRName.getText().toString();
            String RAdd = RRAdd.getText().toString();
            String RNum = RRNum.getText().toString();
            RenterInfo.setVisibility(View.GONE);
            Register1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mApiService.registerRenter(ACC.id,RName,RAdd,RNum).enqueue(new Callback<Renter>() {
                        @Override
                        public void onResponse(Call<Renter> call, Response<Renter> response) {
                            ACC.renter = response.body();
                            Toast.makeText(getApplicationContext(), "NEW RENTER REGISTERED", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Renter> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "RENTER REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}