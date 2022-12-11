package ArmondJsleepJS.example.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import ArmondJsleepJS.example.jsleep_android.model.Facility;
import ArmondJsleepJS.example.jsleep_android.model.Room;

public class RoomDetail extends AppCompatActivity {
    public static Room ThisRoom;
    Button Buy, Cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        Buy = findViewById(R.id.BuyButton);
        Cancel = findViewById(R.id.BuyCancel);

        TextView roomName = findViewById(R.id.tv_roomName);
        TextView roomBedType = findViewById(R.id.tv_roomBedType);
        TextView roomSize = findViewById(R.id.tv_roomSize);
        TextView roomPrice = findViewById(R.id.tv_roomPrice);
        TextView roomAddress = findViewById(R.id.tv_roomAddress);
        roomName.setText(ThisRoom.name);
        roomBedType.setText(String.valueOf(ThisRoom.bedType));
        roomSize.setText(ThisRoom.size + " m^2");
        roomPrice.setText(Double.toString(ThisRoom.price.price));
        roomAddress.setText(ThisRoom.address);

        CheckBox wifi = findViewById(R.id.checkbox_wifi);
        CheckBox AC = findViewById(R.id.checkbox_AC);
        CheckBox bathtub = findViewById(R.id.checkbox_bathtub);
        CheckBox balcony = findViewById(R.id.checkbox_balcony) ;
        CheckBox restaurant = findViewById(R.id.checkbox_restaurant);
        CheckBox refrigerator = findViewById(R.id.checkbox_Refrigerator);
        CheckBox swimmingPool = findViewById(R.id.checkbox_swimmingpool);
        CheckBox fitnessCenter = findViewById(R.id.checkbox_fitness);


        for(int i = 0; i < ThisRoom.facility.size(); i++) {
            if (ThisRoom.facility.get(i) == Facility.AC) {
                AC.setChecked(true);
            } else if (ThisRoom.facility.get(i) == Facility.Refrigerator) {
                refrigerator.setChecked(true);
            } else if (ThisRoom.facility.get(i) == Facility.WiFi) {
                wifi.setChecked(true);
            } else if (ThisRoom.facility.get(i) == Facility.Bathtub) {
                bathtub.setChecked(true);
            } else if (ThisRoom.facility.get(i) == Facility.Balcony) {
                balcony.setChecked(true);
            } else if (ThisRoom.facility.get(i) == Facility.Restaurant) {
                restaurant.setChecked(true);
            } else if (ThisRoom.facility.get(i) == Facility.SwimmingPool) {
                swimmingPool.setChecked(true);
            } else if (ThisRoom.facility.get(i) == Facility.FitnessCenter) {
                fitnessCenter.setChecked(true);
            }
        }

        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Pay = new Intent(RoomDetail.this, PaymentDetail.class);
                startActivity(Pay);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackToMain = new Intent(RoomDetail.this, MainActivity.class);
                startActivity(BackToMain);
            }
        });
    }
}