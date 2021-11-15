package com.example.midterm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.midterm.api.RequestPlaceholder;
import com.example.midterm.api.RetrofitBuilder;
import com.example.midterm.pojos.Login;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public EditText username, password;
    public MaterialButton loginBtn;
    public TextView ForgotPassword;

    public RetrofitBuilder retrofitBuilder;
    public RequestPlaceholder requestPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginbtn);
        ForgotPassword = findViewById(R.id.forgotpassword);

        retrofitBuilder = new RetrofitBuilder();
        requestPlaceholder = retrofitBuilder.getRetrofit().create(RequestPlaceholder.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText() != null && password.getText() != null){
                    Call<Login> loginCall = requestPlaceholder.login(new Login(null, username.getText().toString(), null, null, password.getText().toString()));

                    loginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if(!response.isSuccessful()){
                                if (response.code() == 404){
                                    Toast.makeText(LoginActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                                    Log.e("LOGIN_ERR", response.message());
                                }else{
                                    Toast.makeText(LoginActivity.this, "You have an error upon logging in.", Toast.LENGTH_SHORT).show();
                                    Log.e("LOGIN_ERR", response.message());
                                }
                                Toast.makeText(LoginActivity.this, "You have an error upon logging in.", Toast.LENGTH_SHORT).show();
                                Log.e("LOGIN_ERR", response.message());
                            }else{
                                if (response.code() == 200){
                                    Toast.makeText(LoginActivity.this, "You logged in successfully.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "You have an error upon logging in.", Toast.LENGTH_SHORT).show();
                            Log.e("LOGIN_ERR", t.getMessage());
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Supply all the lacking field.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}