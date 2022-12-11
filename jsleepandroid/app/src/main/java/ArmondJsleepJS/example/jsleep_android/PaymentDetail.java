package ArmondJsleepJS.example.jsleep_android;

import static ArmondJsleepJS.example.jsleep_android.MainActivity.ACC;
import static ArmondJsleepJS.example.jsleep_android.RoomDetail.ThisRoom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ArmondJsleepJS.example.jsleep_android.model.Payment;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import ArmondJsleepJS.example.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Payment Detail activity. Buyers can insert time interval in which they
 * will book a certain room, as well as see how much it would cost them
 * @Author Armond Harer
 */
public class PaymentDetail extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
    TextView To, From, totalPayment;
    double totalPrice;
    Button ConfirmPayment, CancelPayment;
    Date date1, date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        TextView UserName = findViewById(R.id.tv_PaymentUserNameIn);
        TextView RenterName = findViewById(R.id.tv_PaymentRenterNameIn);
        TextView UserBalance = findViewById(R.id.tv_PaymentUserBalanceIn);

        TextView RoomName = findViewById(R.id.tv_PaymentRoomNameIn);
        TextView RoomPrice = findViewById(R.id.tv_PaymentRoomPriceIn);
        TextView RoomArea = findViewById(R.id.tv_PaymentRoomAreaIn);

        UserName.setText(ACC.name);
        RenterName.setText(ACC.renter.username);
        UserBalance.setText(Double.toString(ACC.balance));

        RoomName.setText(ThisRoom.name);
        RoomPrice.setText(Double.toString(ThisRoom.price.price));
        RoomArea.setText(ThisRoom.size + " m^2");

        To = findViewById(R.id.DateToIn);
        From = findViewById(R.id.DateFromIn);
        totalPayment = findViewById(R.id.tv_TotalPriceIn);

        ConfirmPayment = findViewById(R.id.BuyButton);
        CancelPayment = findViewById(R.id.BuyCancel);

        // Add date room will be booked from upon click
        From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int fromYear = c.get(Calendar.YEAR); // current year
                int fromMonth = c.get(Calendar.MONTH); // current month
                int fromDay = c.get(Calendar.DAY_OF_MONTH); // current day
                DatePickerDialog fromDatePicker = new DatePickerDialog(PaymentDetail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        From.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, fromYear, fromMonth, fromDay);
                fromDatePicker.show();

            }
        });

        // Add date room will be booked until upon click
        To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int toYear = c.get(Calendar.YEAR);
                int toMonth = c.get(Calendar.MONTH);
                int toDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog fromDatePicker = new DatePickerDialog(PaymentDetail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        To.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, toYear, toMonth, toDay);
                fromDatePicker.show();
                totalPayment.setVisibility(View.VISIBLE);
            }
        });

        // Show total payment cost until click
        totalPayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
                    date1 = dates.parse(From.getText().toString());
                    date2 = dates.parse(To.getText().toString());
                    long difference = date2.getTime() - date1.getTime();
                    long differenceDates = difference / (24 * 60 * 60 * 1000);
                    double total = ThisRoom.price.price * differenceDates;
                    if (total > 0) {
                        totalPayment.setText("Rp " + total);
                        totalPrice = total;
                    } else {
                        totalPayment.setText("Invalid Date Entered!");
                    }

                } catch (Exception exception) {
                    Toast.makeText(mContext, "Insert Date First!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Make new Payment class, intent to PaymentConfirmation
        ConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.createPayment(ACC.id, ACC.renter.id, ThisRoom.id,From.getText().toString(),To.getText().toString()).enqueue(new Callback<Payment>() {
                    @Override
                    public void onResponse(Call<Payment> call, Response<Payment> response) {
                        PaymentConfirmation.ThisPayment = response.body();
                        Intent BookingConfirm = new Intent(PaymentDetail.this, PaymentConfirmation.class);
                        BookingConfirm.putExtra("TotalPayment", Double.parseDouble(totalPayment.getText().toString().split(" ")[1]));
                        BookingConfirm.putExtra("From", From.getText().toString());
                        BookingConfirm.putExtra("To", To.getText().toString());
                        startActivity(BookingConfirm);
                    }

                    @Override
                    public void onFailure(Call<Payment> call, Throwable t) {
                        Toast.makeText(mContext, "Payment Registration Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Returns to Main Activity
        CancelPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Returning to Main", Toast.LENGTH_SHORT).show();
                Intent CancelBooking = new Intent(PaymentDetail.this, MainActivity.class);
                startActivity(CancelBooking);
            }
        });

    }
}