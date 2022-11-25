package ArmondJsleepJS.example.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import ArmondJsleepJS.example.jsleep_android.model.*;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import ArmondJsleepJS.example.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoom extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    Facility facility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        EditText Name = findViewById(R.id.roomregname);
        EditText Size = findViewById(R.id.roomregsize);
        EditText Price = findViewById(R.id.roomregprice);
        EditText Address = findViewById(R.id.roomregaddress);
        Spinner SpinCity = (Spinner) findViewById(R.id.spinnerCity);
        Spinner SpinBedType = (Spinner) findViewById(R.id.spinnerBedType);
        SpinCity.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));
        SpinBedType.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        Button create = findViewById(R.id.createbutton);
        Button cancel = findViewById(R.id.cancelbutton);

        final City[] city = new City[1];
        SpinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city[0] = (City) SpinCity.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Room room = requestRoom(MainActivity.ACC.id, Name.getText().toString(),
                        Integer.parseInt(Size.getText().toString()), Double.parseDouble(Price.getText().toString()),
                        facility, (City) city[0], Address.getText().toString());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(CreateRoom.this, MainActivity.class);
                startActivity(move);
            }
        });

    }


    protected Room requestRoom(int id, String name, int size, double price, Facility facilities, City city, String address){
        mApiService.createRoom(id, name, size,price, facilities, city, address).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                Room room = response.body();
                Toast.makeText(mContext, "Room Created!", Toast.LENGTH_SHORT).show();
                Intent move = new Intent(CreateRoom.this, MainActivity.class);
                startActivity(move);
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Fail to Create Room!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}