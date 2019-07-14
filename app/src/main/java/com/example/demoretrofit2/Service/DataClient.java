package com.example.demoretrofit2.Service;

import com.example.demoretrofit2.model.signin.SigninModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    @Multipart
    @POST("uploadimage.php")
    Call<String> UploadPhot(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("insert.php")
    Call<String> insertData(@Field("username") String username,
                            @Field("password") String password,
                            @Field("image") String image
                            );

    @FormUrlEncoded
    @POST("login.php")
    Call<List<SigninModel>> signinData(@Field("username") String user,
                                       @Field("password") String passwword
                                       );

}
