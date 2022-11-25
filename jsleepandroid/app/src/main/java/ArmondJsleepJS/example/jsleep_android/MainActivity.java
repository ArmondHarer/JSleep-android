package ArmondJsleepJS.example.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.widget.Toast;

import ArmondJsleepJS.example.jsleep_android.model.Account;
import ArmondJsleepJS.example.jsleep_android.model.Room;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import retrofit2.*;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    public static Account ACC;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = null;
        try {
            InputStream inStream = getAssets().open("randomRoomList.json");
            int size = inStream.available();
            byte[] buffer = new byte[size];
            inStream.read(buffer);
            inStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Room[] roomList = gson.fromJson(json, Room[].class);
        ArrayList<String> roomNameList = new ArrayList<>();
        for (Room room : roomList) {
            Log.d("Room", room.name);
            roomNameList.add(room.name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomNameList);
        ListView listView = findViewById(R.id.legacy_listview);
        listView.setAdapter(adapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_aboutMe:
                Intent move = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(move);
        }
        return true;
    }
}