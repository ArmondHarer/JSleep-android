package ArmondJsleepJS.example.jsleep_android;

import static ArmondJsleepJS.example.jsleep_android.MainActivity.ACC;
import static ArmondJsleepJS.example.jsleep_android.RoomDetail.ThisRoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ArmondJsleepJS.example.jsleep_android.model.Payment;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import ArmondJsleepJS.example.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Payment Confirmation Activity. Buyer can see how much money will be
 * left in their account balance after booking the room
 */
public class PaymentConfirmation extends AppCompatActivity {
    public static Payment ThisPayment;
    BaseApiService mApiService;
    Context mContext;
    Button ConfirmPayment, CancelPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        Bundle PaymentDetails = getIntent().getExtras();

        ConfirmPayment = findViewById(R.id.BuyButton);
        CancelPayment = findViewById(R.id.CancelButton);

        TextView UserName = findViewById(R.id.tv_PaymentUserNameIn);
        TextView RenterName = findViewById(R.id.tv_PaymentRenterNameIn);
        TextView UserBalance = findViewById(R.id.tv_PaymentUserBalanceIn);

        TextView RoomName = findViewById(R.id.tv_PaymentRoomNameIn);
        TextView RoomPrice = findViewById(R.id.tv_PaymentRoomPriceIn);
        TextView To = findViewById(R.id.tv_PaymentToIn);
        TextView From = findViewById(R.id.tv_PaymentFromIn);

        TextView TotalPrice = findViewById(R.id.tv_TotalPriceIn);
        TextView RemainingBalance = findViewById(R.id.tv_BalanceAfterIn);

        UserName.setText(ACC.name);
        RenterName.setText(ACC.renter.username);
        UserBalance.setText(Double.toString(ACC.balance));

        RoomName.setText(ThisRoom.name);
        RoomPrice.setText(Double.toString(ThisRoom.price.price));

        To.setText(PaymentDetails.getString("To"));
        From.setText(PaymentDetails.getString("From"));
        TotalPrice.setText(Double.toString(PaymentDetails.getDouble("TotalPayment")));
        RemainingBalance.setText(Double.toString(ACC.balance - PaymentDetails.getDouble("TotalPayment")));
        Intent BackToMain = new Intent(PaymentConfirmation.this, MainActivity.class);

        ConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.accept(ThisPayment.id).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Toast.makeText(mContext, "Room booking successful", Toast.LENGTH_SHORT).show();

                        startActivity(BackToMain);
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(mContext, "Room booking unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        CancelPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.accept(ThisPayment.id).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        startActivity(BackToMain);
                        Toast.makeText(mContext, "Cancelling payment", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(mContext, "ERROR: Cancellation failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}