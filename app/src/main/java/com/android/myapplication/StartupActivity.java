package com.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

public class StartupActivity extends AppCompatActivity {
    ProgressBar pgb;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        int progID2 = getResources().getIdentifier("progbar", "id", getPackageName());
        pgb = (ProgressBar)findViewById(progID2);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences settings = getSharedPreferences("my_usr_pr", Context.MODE_PRIVATE);
        String emailsd = settings.getString("email", "0");
        String passd = settings.getString("password", "0");
        Boolean issd = settings.getBoolean("savedets", false);


        if(issd == false) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go_lgn();
                }
            }, 1500);
        }
        else{
            sign_in_acc(emailsd, passd);
        }
    }

    void sign_in_acc(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("izn");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginActivity.set_muid(user.getUid());
                            go_mn();
                        } else {
                            // If sign in fails, display a message to the user.
                            go_lgn();
                            System.out.println("ffkz");
                        }
                        // ...
                    }
                });
    }

    void go_mn(){
        startActivity(new Intent(this, MainActivity.class));
    }

    void go_lgn(){
        startActivity(new Intent(this, LoginActivity.class));
    }

}