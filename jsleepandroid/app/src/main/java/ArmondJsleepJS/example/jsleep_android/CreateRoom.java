package ArmondJsleepJS.example.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

import ArmondJsleepJS.example.jsleep_android.model.*;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import ArmondJsleepJS.example.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Room registration activity. Here users with the help of a renter can register rooms for rent
 * @Author Armond Harer
 */
public class CreateRoom extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    ArrayList<Facility> Facilities = new ArrayList<>();

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
        Spinner SpinCity = findViewById(R.id.spinnerCity);
        Spinner SpinBedType = findViewById(R.id.spinnerBedType);
        SpinCity.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));
        SpinBedType.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        Button create = findViewById(R.id.createbutton);
        Button cancel = findViewById(R.id.cancelbutton);

        //Segment of code for selecting City enum from spinner
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

        //Ditto as above, but for BedType enum
        final BedType[] bedtype = new BedType[1];
        SpinBedType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bedtype[0] = (BedType) SpinBedType.getAdapter().getItem(i);
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
                        Facilities, (City) city[0], (BedType) bedtype[0], Address.getText().toString());
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

    //Segment of code for adding Facility enum from checkboxes to Facility arraylist
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkbox_AC:
                if (checked)
                    Facilities.add(Facility.AC);
                else
                    Facilities.remove(Facility.AC);
                break;
            case R.id.checkbox_Refrigerator:
                if (checked)
                    Facilities.add(Facility.Refrigerator);
                else
                    Facilities.remove(Facility.Refrigerator);
                break;
            case R.id.checkbox_wifi:
                if (checked)
                    Facilities.add(Facility.WiFi);
                else
                    Facilities.remove(Facility.WiFi);
                break;
            case R.id.checkbox_bathtub:
                if (checked)
                    Facilities.add(Facility.Bathtub);
                else
                    Facilities.remove(Facility.Bathtub);
                break;
            case R.id.checkbox_balcony:
                if (checked)
                    Facilities.add(Facility.Balcony);
                else
                    Facilities.remove(Facility.Balcony);
                break;
            case R.id.checkbox_restaurant:
                if (checked)
                    Facilities.add(Facility.Restaurant);
                else
                    Facilities.remove(Facility.Restaurant);
                break;
            case R.id.checkbox_swimmingpool:
                if (checked)
                    Facilities.add(Facility.SwimmingPool);
                else
                    Facilities.remove(Facility.SwimmingPool);
                break;
            case R.id.checkbox_fitness:
                if (checked)
                    Facilities.add(Facility.FitnessCenter);
                else
                    Facilities.remove(Facility.FitnessCenter);
                break;
        }
    }

    /**
     * Room registration function
     * @param id            | ID of account linked to registration
     * @param name          | Room name
     * @param size          | Room size (in meters squared)
     * @param price         | Room price (per night)
     * @param Facilities    | Room facilities
     * @param city          | City where room is located
     * @param bedtype       | Room bed size
     * @param address       | Room location address
     * @return new room
     */
    protected Room requestRoom(int id, String name, int size, double price, ArrayList<Facility> Facilities, City city, BedType bedtype, String address){
        mApiService.createRoom(id, name, size,price, Facilities, city, bedtype, address).enqueue(new Callback<Room>() {
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