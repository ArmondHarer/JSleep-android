package ArmondJsleepJS.example.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;

public class Room{
    public int size;
    public String name;
    public Price price;
    public Facility facility;
    public String address;
    public BedType bedType;
    public City city;
    public ArrayList <Date> booked = new ArrayList<Date>();
    public int accountId;

    public Room(String name, int size, Price price, Facility facility,City city, String address, int accountID){
        super();
        this.name = name;
        this.size = size;
        this.price = price;
        this.facility = facility;
        this.city = city;
        this.address = address;
        this.accountId = accountId;
    }

    public String toString(){
        return "Room Information : \nName: " + name + "\nSize: " + size + "m^2\nPrice: $" + price + "\nFacility: " + facility + "\nCity: " + city + "\nAddress: " + address + "\nBed Type: " + bedType;
    }

    public Object write(){
        return null;
    }

    public boolean read(String content){
        return false;
    }
}
