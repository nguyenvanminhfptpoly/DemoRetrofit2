package com.example.demoretrofit2.view.signup;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoretrofit2.R;
import com.example.demoretrofit2.Service.APIUltil;
import com.example.demoretrofit2.Service.DataClient;
import com.example.demoretrofit2.Service.RetrofitClient;
import com.example.demoretrofit2.presenter.signup.SignUpPresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements SignUpView, View.OnClickListener {
    private TextView tvUser;
    private EditText edUser;
    private TextView tvPass;
    private EditText edPass;
    private ImageView imgUser;
    private Button btnSignUp;
    int Image = 123;

    String realpath = "";
    String user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Khaibao();
    }

    public void Khaibao(){
        tvUser = (TextView) findViewById(R.id.tvUser);
        edUser = (EditText) findViewById(R.id.edUser);
        tvPass = (TextView) findViewById(R.id.tvPass);
        edPass = (EditText) findViewById(R.id.edPass);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

       final SignUpPresenter signUpPresenter = new SignUpPresenter(this);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = edUser.getText().toString();
                pass = edPass.getText().toString();

                signUpPresenter.SignUp(user,pass);
            }
        });
        imgUser.setOnClickListener(this);
    }
    @Override
    public void SignUpFailed() {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SignUpSuccess() {
       Signup();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgUser:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Image);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Image && resultCode == RESULT_OK && data != null ){
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgUser.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
    public void Signup(){
        if(user.length() > 0 && pass.length() > 0) {
            File file = new File(realpath);
            String file_path = file.getAbsolutePath();

            String[] mangtenfile = file_path.split("\\.");
            file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

            DataClient dataClient = APIUltil.getData();

            Call<String> call = dataClient.UploadPhot(body);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null) {
                        String message = response.body();
                        if (message.length() > 0) {
                            DataClient insertData = APIUltil.getData();
                            retrofit2.Call<String> callb = insertData.insertData(user,pass,APIUltil.baseUrl + "image/" +message);
                            callb.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String result = response.body();
                                    if(result.equals("Success")){
                                        Toast.makeText(SignUpActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("failed", t.getMessage());
                }

            });
        }else {
            Toast.makeText(this, "Nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}
