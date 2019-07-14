package com.example.demoretrofit2.presenter.signup;

import com.example.demoretrofit2.model.signup.SignUpModel;
import com.example.demoretrofit2.view.signup.SignUpView;

public class SignUpPresenter implements SignUpPresenterInterface {
    SignUpView signUpView;
    SignUpModel signUpModel;
    public SignUpPresenter(SignUpView signUpView){
        this.signUpView = signUpView;
    }
    @Override
    public void SignUp(String email, String password) {
            if(email.equals("") && password.equals("")){
                signUpView.SignUpFailed();
            }else {
                signUpView.SignUpSuccess();
            }
    }
}
