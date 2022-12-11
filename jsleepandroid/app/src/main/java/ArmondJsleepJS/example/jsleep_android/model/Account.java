package ArmondJsleepJS.example.jsleep_android.model;

public class Account extends Serializable{
    public String name;
    public String email;
    public Renter renter;
    public String password;
    public Double balance;
    public Account(String name, String email, String password, Double balance)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    @Override
    public String toString(){
        return "Account{Balance: " + balance +
                "; Email: " + email + '\'' +
                "; Name: " + name + '\'' +
                "; Password: " + password + '\'' +
                "; Renter: " + renter + '}';
    }
}
