package ArmondJsleepJS.example.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import ArmondJsleepJS.example.jsleep_android.model.Room;

public class RoomDetail extends AppCompatActivity {
    public static Room ThisRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        TextView roomName = findViewById(R.id.tv_roomName);
        TextView roomBedType = findViewById(R.id.tv_roomBedType);
        TextView roomSize = findViewById(R.id.tv_roomSize);
        TextView roomPrice = findViewById(R.id.tv_roomPrice);
        TextView roomAddress = findViewById(R.id.tv_roomAddress);

        CheckBox wifi = (CheckBox) findViewById(R.id.checkbox_wifi);
        CheckBox AC = (CheckBox) findViewById(R.id.checkbox_AC);
        CheckBox bathtub = (CheckBox) findViewById(R.id.checkbox_bathtub);
        CheckBox balcony = (CheckBox) findViewById(R.id.checkbox_balcony) ;
        CheckBox restaurant = (CheckBox) findViewById(R.id.checkbox_restaurant);
        CheckBox refrigerator = (CheckBox) findViewById(R.id.checkbox_Refrigerator);
        CheckBox swimmingPool = (CheckBox) findViewById(R.id.checkbox_swimmingpool);
        CheckBox fitnessCenter = (CheckBox) findViewById(R.id.checkbox_fitness);

        roomName.setText(ThisRoom.name);
        roomBedType.setText(String.valueOf(ThisRoom.bedType));
        roomSize.setText(ThisRoom.size + " m^2");
        roomPrice.setText("Rp " + ThisRoom.price.price);
        roomAddress.setText(ThisRoom.address);

        switch (ThisRoom.facility){
            case AC: AC.setChecked(true);
            case WiFi: wifi.setChecked(true);
            case Bathtub: bathtub.setChecked(true);
            case Balcony: balcony.setChecked(true);
            case Restaurant: restaurant.setChecked(true);
            case Refrigerator: refrigerator.setChecked(true);
            case SwimmingPool: swimmingPool.setChecked(true);
            case FitnessCenter: fitnessCenter.setChecked(true);
        }
    }
}