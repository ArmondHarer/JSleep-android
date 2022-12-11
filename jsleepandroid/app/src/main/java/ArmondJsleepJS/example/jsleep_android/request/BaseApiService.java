package ArmondJsleepJS.example.jsleep_android.request;

import java.util.ArrayList;
import java.util.List;

import ArmondJsleepJS.example.jsleep_android.model.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> register  (@Query("name") String name,
                             @Query("email") String email,
                             @Query("password") String password);

    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id,
                                @Query("username") String username,
                                @Query("address") String address,
                                @Query("phoneNumber") String phoneNumber);

    @POST("account/{id}/topUp")
    Call<Boolean> topUp(@Path("id") int id, @Query("balance") double balance);

    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int id);

    @GET("room/{id}/renter")
    Call<List<Room>> getRoomByRenter (@Path("id") int id,
                                      @Query("page") int page,
                                      @Query("pageSize") int pageSize);

    @POST("room/create")
    Call<Room> createRoom (@Query("accountId") int accountId,
                           @Query("name") String name,
                           @Query("size") int size,
                           @Query("price") double price,
                           @Query("facility") ArrayList<Facility> facility,
                           @Query("city") City city,
                           @Query("bedtype") BedType bedtype,
                           @Query("address") String address);

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom (@Query("page") int page,
                             @Query("pageSize") int pagesize);

    @GET("payment/{id}")
    Call<Payment> getPayment(@Path("id") int id);

    @POST("payment/create")
    Call<Payment> createPayment ( @Query("buyerId") int buyerId,
                                  @Query("renterId") int renterId,
                                  @Query("roomId") int roomId,
                                  @Query("from") String from,
                                  @Query("to") String to);

    @POST("payment/{id}/accept")
    Call<Boolean> accept (@Path("id") int id);

    @POST("payment/{id}/cancel")
    Call<Boolean> cancel (@Path("id") int id);
}
