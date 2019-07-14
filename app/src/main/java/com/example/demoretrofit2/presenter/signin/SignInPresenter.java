package com.example.demoretrofit2.presenter.signin;

import com.example.demoretrofit2.model.signin.SigninModel;
import com.example.demoretrofit2.view.signin.SignInView;

public class SignInPresenter implements SignInPresenterInterface {
    SignInView signInView;

    public SignInPresenter(SignInView signInView){
        this.signInView = signInView;
    }
    @Override
    public void SignIn(String email, String password) {
        if(email.equals("") && password.equals("")){
            signInView.SignInFailed();
        }else {
            signInView.SignInSuccess();
        }
    }
}
