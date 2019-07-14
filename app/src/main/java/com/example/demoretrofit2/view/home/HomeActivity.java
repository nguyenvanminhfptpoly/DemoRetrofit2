package com.example.demoretrofit2.view.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.demoretrofit2.R;
import com.example.demoretrofit2.model.signin.SigninModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        ArrayList<SigninModel> signinModels = intent.getParcelableArrayListExtra("manguser");
        Log.d("sinhvien",""+ signinModels.get(0).getEmail());
        Log.d("sinhvien", ""+signinModels.get(0).getPassword());
        Log.d("sinhvien", ""+signinModels.get(0).getImage());
        Log.d("sinhvien",""+ signinModels.get(0).getId());
    }
}
