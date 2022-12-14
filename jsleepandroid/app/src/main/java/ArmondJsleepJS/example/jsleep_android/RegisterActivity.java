package ArmondJsleepJS.example.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ArmondJsleepJS.example.jsleep_android.model.Account;
import ArmondJsleepJS.example.jsleep_android.request.BaseApiService;
import ArmondJsleepJS.example.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Account registration activity. Here new users can register new accounts
 * @Author Armond Harer
 */
public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    TextView Back2login;
    EditText nama, email, password;
    Button Reg;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        nama = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        Reg = findViewById(R.id.btnRegister);
        Back2login = findViewById(R.id.tv_loginHere);

        //Request account registration
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRegister();
            }
        });

        //If text is clicked, return to login activity
        Back2login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ReturntoLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(ReturntoLogin);
            }
        }));
    }

    /**
     * Function to register a new account
     * @Param nama      | Account name
     * @Param email     | Account email address
     * @Param password  | Account password
     * @return a new account, and instantly log in to main activity
     */
    protected Account requestRegister(){
        mApiService.register(
                nama.getText().toString(),
                email.getText().toString(),
                password.getText().toString()
        ).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    MainActivity.ACC = response.body();
                    Intent regis = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(regis);
                    Toast.makeText(mContext, "REGISTER SUCCESSFUL!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "ACCOUNT ALREADY REGISTERED", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}