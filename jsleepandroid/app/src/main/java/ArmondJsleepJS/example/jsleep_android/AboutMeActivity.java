package ArmondJsleepJS.example.jsleep_android;

import static ArmondJsleepJS.example.jsleep_android.MainActivity.ACC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ArmondJsleepJS.example.jsleep_android.model.Account;
import ArmondJsleepJS.example.jsleep_android.model.Renter;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import ArmondJsleepJS.example.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * About Me page of application. Here users can register renters, or top up their balance
 * @Author Armond Harer
 */
public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;

    TextView nama, email, balance;
    TextView RentName, RentAddress, RentNum;
    EditText RRName, RRAdd, RRNum, TopUpAmount;
    CardView RenterInfo, RegisterRenter;
    Button Register1, Register2, Cancel, TopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mApiService = UtilsApi.getApiService();
        mContext = this;

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
        TopUp = findViewById(R.id.btnTopUp);
        TopUpAmount = findViewById(R.id.et_topUpAmount);

        nama.setText(ACC.name);
        email.setText(ACC.email);
        balance.setText(Double.toString(ACC.balance));

        //if account has no renter, show renter registration. Otherwise show renter information
        if (ACC.renter != null) {
            RegisterRenter.setVisibility(View.INVISIBLE);
            Register1.setVisibility(View.INVISIBLE);
            RentName.setText(ACC.renter.username);
            RentAddress.setText(ACC.renter.address);
            RentNum.setText(ACC.renter.phonenumber);
        } else {
            RenterInfo.setVisibility(View.INVISIBLE);
            RegisterRenter.setVisibility(View.GONE);
            Register1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RegisterRenter.setVisibility(View.VISIBLE);
                    Register1.setVisibility(View.GONE);
                }
            });

            //when clicked, register a new renter
            Register2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String RName = RRName.getText().toString();
                    String RAdd = RRAdd.getText().toString();
                    String RNum = RRNum.getText().toString();
                    mApiService.registerRenter(ACC.id, RName, RAdd, RNum).enqueue(new Callback<Renter>() {
                        @Override
                        public void onResponse(Call<Renter> call, Response<Renter> response) {
                            MainActivity.ACC.renter = response.body();
                            RegisterRenter.setVisibility(View.GONE);
                            RenterInfo.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "NEW RENTER REGISTERED", Toast.LENGTH_SHORT).show();
                            RentName.setText(RName);
                            RentAddress.setText(RAdd);
                            RentNum.setText(RNum);
                        }

                        @Override
                        public void onFailure(Call<Renter> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "RENTER REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            //Cancel registering new renter
            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RegisterRenter.setVisibility(View.GONE);
                    Register1.setVisibility(View.VISIBLE);
                }
            });
        }

        //Top up account balance
        TopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TopUpAmount.getText().toString().isEmpty()){
                    Toast.makeText(AboutMeActivity.this, "Empty Input", Toast.LENGTH_LONG).show();
                }
                else{
                Double TopUpCash = Double.parseDouble(TopUpAmount.getText().toString());
                mApiService.topUp(ACC.id, TopUpCash).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        ACC.balance = (Double.parseDouble(balance.getText().toString())+Double.parseDouble(TopUpAmount.getText().toString()));
                        balance.setText(Double.toString(ACC.balance));
                        TopUpAmount.setText("");
                        Toast.makeText(AboutMeActivity.this, "TOP UP SUCCESSFUL", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(AboutMeActivity.this,"TOP UP FAILED", Toast.LENGTH_LONG);
                    }
                });
            }}
        });
    }
}