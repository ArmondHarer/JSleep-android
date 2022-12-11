package ArmondJsleepJS.example.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import ArmondJsleepJS.example.jsleep_android.model.Account;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import ArmondJsleepJS.example.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Login screen. Users can login via email and password
 * @Author Armond Harer
 */
public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username, password;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        TextView register = findViewById(R.id.textView3);
        Button main = findViewById(R.id.buttonRegister);
        username = findViewById(R.id.Username);
        password = findViewById(R.id.password);

        //when text is clicked, leads to register activity where new users can register account
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        // When button is clicked, checks for account credentials and leads to main activity
        main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                   requestLogin();
            }
        });

    }

    /**
     * Function to request an account to login by comparing username and password
     * @Param username | Account email address
     * @Param password | Account password
     * @return Account ACC which will be used during the session
     */
    protected Account requestLogin(){
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        mApiService.login(username, password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    MainActivity.ACC = response.body();
                    System.out.println("Account yang digunakan : " + MainActivity.ACC.toString());
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(getApplicationContext(),"WELCOME TO JSLEEP, " + MainActivity.ACC.name, Toast.LENGTH_SHORT).show();
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "LOGIN FAILED, PLEASE TRY AGAIN", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }
}
