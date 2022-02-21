package com.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    static String m_uid;

    FirebaseAuth mAuth;

    CheckBox savedets;

    Button LogBut;

    EditText EmailB;
    EditText passB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //change status bar icon colour
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences settings = getSharedPreferences("my_usr_pr", Context.MODE_PRIVATE);
        String emailsd = settings.getString("email", "0");
        String passd = settings.getString("password", "0");
        Boolean issd = settings.getBoolean("savedets", false);

        int rzID = getResources().getIdentifier("cirLoginButton", "id", getPackageName());
        LogBut = (Button)findViewById(rzID);

        int emID = getResources().getIdentifier("username_input", "id", getPackageName());
        EmailB = (EditText) findViewById(emID);

        int PID = getResources().getIdentifier("pass", "id", getPackageName());
        passB = (EditText)findViewById(PID);

        if(issd == true)
        {
            passB.setText(passd);
            EmailB.setText(emailsd);
        }


        int cbid = getResources().getIdentifier("savdets", "id", getPackageName());
        savedets = (CheckBox) findViewById(cbid);

        LogBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EmailB.getText().toString().length() == 0 || passB.getText().toString().length() == 0)
                {
                    AlrtDlg();
                }
                else {
                    sign_in_acc(EmailB.getText().toString(), passB.getText().toString());
                }
            }
        });

    }
    public void onLoginClick(View view){
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public static String get_muid(){
        return m_uid;
    }

    public static void set_muid(String setit){
        m_uid = setit;
    }

    void sign_in_acc(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("izn");
                            if(savedets.isChecked() == true){
                                SharedPreferences settings = getSharedPreferences("my_usr_pr", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("savedets", true);
                                editor.putString("email", email );
                                editor.putString("password", password );
                                editor.apply();
                                System.out.println("isit");
                            }
                            else{
                                SharedPreferences settings = getSharedPreferences("my_usr_pr", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("savedets", false);
                                editor.apply();
                            }
                            FirebaseUser user = mAuth.getCurrentUser();
                            m_uid = user.getUid();
                            go_mn();
                        } else {
                            // If sign in fails, display a message to the user.
                            AlrtDlg();
                            System.out.println("ffkz");
                        }

                        // ...
                    }
                });
    }

    void go_mn(){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void AlrtDlg(){
        System.out.println("ffk");
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Error, wrong user input");
        alertDialog.setMessage("Email address or password incorrect");

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}