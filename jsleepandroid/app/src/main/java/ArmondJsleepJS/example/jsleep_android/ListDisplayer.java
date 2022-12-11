package ArmondJsleepJS.example.jsleep_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ArmondJsleepJS.example.jsleep_android.model.Room;

public class ListDisplayer extends ArrayAdapter<Room> {

    public ListDisplayer(@NonNull Context context, ArrayList<Room> rooms) {
        super(context, 0, rooms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View currentItemView = convertView;
        if(currentItemView == null){
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_display,parent,false);
        }

        Room currentRoom = getItem(position);

        TextView name = currentItemView.findViewById(R.id.room_name);
        name.setText(currentRoom.name);

        return currentItemView;
    }
}