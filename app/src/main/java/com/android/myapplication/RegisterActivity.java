package com.android.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    Button RegBut;

    EditText emailB;
    EditText passB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        int rzID = getResources().getIdentifier("cirRegisterButton", "id", getPackageName());
        RegBut = (Button)findViewById(rzID);

        int emID = getResources().getIdentifier("editTextEmail", "id", getPackageName());
        emailB = (EditText)findViewById(emID);

        int pasID = getResources().getIdentifier("editTextPassword", "id", getPackageName());
        passB = (EditText)findViewById(pasID);


        RegBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailB.getText().toString().length() == 0 || passB.getText().toString().length() == 0)
                {
                    AlrtDlg();
                }
                else
                {
                    String pass = passB.getText().toString();
                    boolean isuppr = false;
                    boolean isdig = false;
                    int i = 0;
                    while(i< pass.length()) {
                        char ch = pass.charAt(i);
                        if( Character.isDigit(ch)) {
                            isdig = true;
                        }
                        if (Character.isUpperCase(ch)) {
                            isuppr = true;
                        }
                        i += 1;
                    }
                    if(isdig == true && isuppr == true){
                        if(pass.length() > 7) {
                            create_acc(emailB.getText().toString(),passB.getText().toString());
                            go_logn();
                        }
                        else{
                            alrtlng();
                        }
                    }
                    else {
                        alrtdgp();
                    }
                }
            }
        });

        changeStatusBarColor();
    }

    void go_news(){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    public void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    void create_acc(String email, String password){
        System.out.println(email);
        System.out.println(password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("intv");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            System.out.println("ikn");
                            AlrtDlg();
                            // If sign in fails, display a message to the user.
                        }

                    }
                });
    }
    void go_logn()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void AlrtDlg(){
        System.out.println("ffk");
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Error, wrong user input");
        alertDialog.setMessage("Email address incorrect");

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    public void alrtdgp(){
        System.out.println("ffk");
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Error, wrong user input");
        alertDialog.setMessage("Password must have a number and a capital letter");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void alrtlng(){
        System.out.println("ffk");
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Error, wrong user input");
        alertDialog.setMessage("Password must have 8 or more characters");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}