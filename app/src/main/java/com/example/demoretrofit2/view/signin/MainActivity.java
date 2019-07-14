package com.example.demoretrofit2.view.signin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoretrofit2.R;
import com.example.demoretrofit2.Service.APIUltil;
import com.example.demoretrofit2.Service.DataClient;
import com.example.demoretrofit2.databinding.ActivityMainBinding;
import com.example.demoretrofit2.model.signin.SigninModel;
import com.example.demoretrofit2.presenter.signin.SignInPresenter;
import com.example.demoretrofit2.view.home.HomeActivity;
import com.example.demoretrofit2.view.signup.SignUpActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SignInView {
    private EditText edUsername;
    private TextView tvUsername;
    private TextView tvPasswird;
    private EditText editText2;
    private Button btnSignIn;
    private Button btnSignUp;

    private SignInPresenter signInPresenter;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Khaibao();
    }
    public void Khaibao(){
        edUsername = (EditText) findViewById(R.id.edUsername);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvPasswird = (TextView) findViewById(R.id.tvPasswird);
        editText2 = (EditText) findViewById(R.id.editText2);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        signInPresenter = new SignInPresenter(this);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edUsername.getText().toString();
                password = editText2.getText().toString();
                signInPresenter.SignIn(email,password);
            }
        });

    }

    @Override
    public void SignInFailed() {
        Toast.makeText(this, "Dư liệu trống", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SignInSuccess() {
        SignIn();
    }
    public void SignIn(){
        if(email.length() > 0 && password.length() > 0){
            DataClient dataClient = APIUltil.getData();
            Call<List<SigninModel>> callb = dataClient.signinData(email,password);
            callb.enqueue(new Callback<List<SigninModel>>() {
                @Override
                public void onResponse(Call<List<SigninModel>> call, Response<List<SigninModel>> response) {
                    ArrayList<SigninModel> manguser = (ArrayList<SigninModel>) response.body();
                    if(manguser.size() > 0){
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                       intent.putExtra("manguser", manguser);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<List<SigninModel>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            Toast.makeText(this, "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

}
