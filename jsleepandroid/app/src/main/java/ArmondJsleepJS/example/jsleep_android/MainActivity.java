package ArmondJsleepJS.example.jsleep_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.util.Log;
import android.widget.Toast;

import ArmondJsleepJS.example.jsleep_android.model.Account;
import ArmondJsleepJS.example.jsleep_android.model.Room;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import ArmondJsleepJS.example.jsleep_android.request.UtilsApi;
import retrofit2.*;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.*;
import java.io.InputStream;

/**
 * Main Activity for JSleep-Android. Users can scroll through the list of rooms,
 * register their own rooms with a renter's help, book rooms, and look at their
 * account information via this app
 *
 * @Author Armond Harer
 */
public class MainActivity extends AppCompatActivity {
    public static Account ACC;
    BaseApiService mApiService;
    Context mContext;
    Button Next, Previous, Go;
    int CurrentPage;
    List<Room> currentList;
    ListView roomviewer;
    public static int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CurrentPage = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        Next = findViewById(R.id.btnNext);
        Previous = findViewById(R.id.btnPrev);
        Go = findViewById(R.id.btnGo);
        roomviewer = findViewById(R.id.legacy_listview);
        List<Room> room = getRoomList(CurrentPage);
        roomviewer.setOnItemClickListener(this::onItemClick);
        EditText viewpage = findViewById(R.id.et_page);

        //Next button function. Skips to the next page on the room list
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentList.size()>CurrentPage){
                    CurrentPage=0;
                    Toast.makeText(mContext, "Turning back to start of list", Toast.LENGTH_SHORT).show();
                    return;
                }
                CurrentPage++;
                try {
                    List<Room> UpdatedRoomList = getRoomList(CurrentPage-1);  //return null
                    Toast.makeText(mContext, "page "+CurrentPage, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //Previous button function. Skips to the previous page on the room list
        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentPage<=0){
                    CurrentPage=0;
                    Toast.makeText(mContext, "this is the first page", Toast.LENGTH_SHORT).show();
                    return;
                }
                CurrentPage--;
                try {
                    List<Room> UpdatedRoomList = getRoomList(CurrentPage-1);
                    Toast.makeText(mContext, "page "+CurrentPage, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //Go button function. Skips to designated page upon click
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<Room> UpdatedRoomList = getRoomList(Integer.parseInt(viewpage.getText().toString()));  //return null
                    Toast.makeText(mContext, "page "+CurrentPage, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Gets a list of rooms based on which page it is located n
     * @param page  | page of list of rooms
     * @return a list of rooms based on the page order and page size (static in this case)
     */
    protected List<Room> getRoomList(int page) {
        //Error handling for negative page numbers
        if(page < 0){
            Toast.makeText(mContext, "ERROR: Negative Page Number", Toast.LENGTH_SHORT).show();
            return null;
        }
        mApiService.getAllRoom(page, 10).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    currentList = response.body();
                    assert currentList != null;
                    ListDisplayer adapter = new ListDisplayer(mContext, (ArrayList<Room>) currentList);
                    roomviewer.setAdapter(adapter);

                    //Upon clicking a room name written on the list, intents towards said room's Room Detail activity
                    roomviewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            RoomDetail.ThisRoom = (Room) roomviewer.getAdapter().getItem(position);
                            if(RoomDetail.ThisRoom.facility == null){
                                Log.d("TEST", "null");
                            }
                            Intent RoomInfo = new Intent(MainActivity.this, RoomDetail.class);
                            startActivity(RoomInfo);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "get room failed", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }

    /**
     * Adapter for when the room name is clicked
     * @param l         | AdapterView
     * @param v         | View
     * @param position  | The room's position order
     * @param id        | The ID of the room's order
     */
    public void onItemClick (AdapterView<?> l, View v, int position, long id){
        Intent intent = new Intent();
        index = position;
        System.out.println("clicked");

        intent.setClass(mContext, RoomDetail.class);
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    /**
     * Function to set up top menu containing room registration, search, account information and logout button
     * @param menu | The menu element
     * @return true boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    /**
     * Function for whenever a menu element is clicked
     * @param item  | Which menu element is clicked
     * @return true boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //About Me button, intents to account information
            case R.id.menu_aboutMe:
            {Intent about = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(about);
                break;}
            //Add Room button, intents to Room registration
            case R.id.menu_add:
            {Intent room = new Intent(MainActivity.this, CreateRoom.class);
                startActivity(room);
                break;}
            //Logout button, sends user back to login screen and logs out account
            case R.id.menu_Logout: {
                AlertDialog.Builder Logout = new AlertDialog.Builder(mContext);
                Logout.setMessage("Are you sure you would like to log out?").setTitle("Confirm Logout");
                Logout.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent Logout = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(Logout);
                        ACC = null;
                    }
                });
                Logout.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                Logout.show();
                break;
            }
        }
        return true;
    }
}