package ArmondJsleepJS.example.jsleep_android.model;

public class Account{
    public String name;
    public String email;
    public Renter renter;
    public String password;
    public Double balance;

    public Account(String name, String email, String password)
    {

        this.name = name;
        this.email = email;
        this.password = password;
    }
}
