package ArmondJsleepJS.example.jsleep_android.model;

import java.util.Date;

public class Payment extends Invoice {
    private int roomId;
    public Date to;
    public Date from;

    public Payment(int buyerId, int renterId, int roomId, Date from, Date to)
        {
            super(buyerId, renterId);
            this.roomId = roomId;
            this.from = from;
            this.to = to;
        }
}
