package ArmondJsleepJS.example.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable{
    public int size;
    public String name;
    public Price price;
    public ArrayList<Facility> facility;
    public String address;
    public BedType bedType;
    public City city;
    public ArrayList <Date> booked ;
    public int accountId;

    public Room(String name, int size, Price price, ArrayList<Facility> facility,City city, BedType BedType, String address, int accountID){
        super();
        this.name = name;
        this.size = size;
        this.price = price;
        this.facility = facility;
        this.city = city;
        this.bedType = BedType;
        this.address = address;
        this.accountId = accountId;
    }

    public Object write(){
        return null;
    }

    public boolean read(String content){
        return false;
    }
}
