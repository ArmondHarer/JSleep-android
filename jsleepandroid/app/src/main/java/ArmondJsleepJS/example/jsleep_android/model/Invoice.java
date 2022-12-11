package ArmondJsleepJS.example.jsleep_android.model;

public class Invoice extends Serializable {
    public int buyerId;
    public int renterId;
    public PaymentStatus status;
    public RoomRating rating;

    protected Invoice(int buyerId, int renterId)
    {
        super();

        this.buyerId = buyerId;
        this.renterId = renterId;
        this.status = PaymentStatus.WAITING;
        this.rating = RoomRating.NONE;
    }

    public enum RoomRating{
        NONE,BAD,NEUTRAL,GOOD
    }

    public enum PaymentStatus{
        FAILED,WAITING,SUCCESS
    }
}
