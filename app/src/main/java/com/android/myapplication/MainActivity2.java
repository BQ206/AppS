package com.android.myapplication;


import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.WHITE;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.navigation.fragment.NavHostFragment;

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

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;


public class MainActivity2 extends AppCompatActivity {
    TextView textView;

    ImageView plot;

    TextView descr;
    TextView namez;
    TextView price;
    Python py;

    PyObject pyobj;

    Button Btn1wk;
    Button Btn3mo;
    Button Btn6mo;

    ImageButton BtnHome;
    ImageButton BtNewsv;

    Button Btn1yr;
    Button Btn5yr;

    Button sl_stk;

    Button back;



    FirebaseAuth mAuth;

    TextView stck;

    Button Login;

    String m_uid;

    ProgressBar pgb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();


        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String symb = intent.getStringExtra(MainActivity.Extra_symbol);
        String name = intent.getStringExtra(MainActivity.Extra_name);
        String prcez = intent.getStringExtra(MainActivity.Extra_prce);
        py = Python.getInstance();

        int progID2 = getResources().getIdentifier("progbar", "id", getPackageName());
        pgb = (ProgressBar)findViewById(progID2);
        pgb.setVisibility(View.GONE);

        pyobj = py.getModule("myscript");

        PyObject obj2 = pyobj.callAttr("stock_descr", symb);

        int dscID = getResources().getIdentifier("appdescr", "id", getPackageName());
        descr = (TextView)findViewById(dscID);
        descr.setText(obj2.toString());


        int rfID = getResources().getIdentifier("apppri", "id", getPackageName());
        price = (TextView)findViewById(rfID);
        price.setText(prcez);

        int rzID = getResources().getIdentifier("text1", "id", getPackageName());
        namez = (TextView)findViewById(rzID);
        namez.setText(name);

        int bnUres = getResources().getIdentifier("wk1", "id", getPackageName());
        Btn1wk = (Button)findViewById(bnUres);

        int drnl = getResources().getIdentifier("butzon", "id", getPackageName());
        BtnHome = (ImageButton)findViewById(drnl);

        int nenl = getResources().getIdentifier("newsim", "id", getPackageName());
        BtNewsv = (ImageButton)findViewById(nenl);

        BtNewsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_news();
            }
        });


        BtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back();
            }
        });

        Btn1wk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_1wk(symb);
            }
        });

        int bnUre2 = getResources().getIdentifier("mo3", "id", getPackageName());
        Btn3mo = (Button)findViewById(bnUre2);

        Btn3mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_3mo(symb);
            }
        });

        int bnUre3 = getResources().getIdentifier("mo6", "id", getPackageName());
        Btn6mo = (Button)findViewById(bnUre3);

        Btn6mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_6mo(symb);
            }
        });

        int bnUre4 = getResources().getIdentifier("yr1", "id", getPackageName());
        Btn1yr = (Button)findViewById(bnUre4);

        Btn1yr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_1yr(symb);
            }
        });

        int bnUre5 = getResources().getIdentifier("yr5", "id", getPackageName());
        Btn5yr = (Button)findViewById(bnUre5);

        Btn5yr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_5yr(symb);
            }
        });







        int sbnzUre = getResources().getIdentifier("sel_stck", "id", getPackageName());
        sl_stk = (Button)findViewById(sbnzUre);



        sl_stk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obju2 = pyobj.callAttr("peg_rat", symb);
                PyObject obju3 = pyobj.callAttr("book_v", symb);
                slct_stck(name);
                set_prc(symb, prcez );
                set_pegr(symb, obju2.toInt() );
                set_bk(symb, obju3.toInt());

            }
        });





    }



    void create_acc(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("intv");
                            FirebaseUser user = mAuth.getCurrentUser();
                            m_uid = user.getUid();
                        } else {
                            System.out.println("ikn");
                            // If sign in fails, display a message to the user.
                        }

                    }
                });
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
                            m_uid = user.getUid();
                        } else {
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });
    }

    void slct_stck(String stocktad){
        Map<String, Object> user = new HashMap<String, Object>();
        String rt;
        rt = "583";
        user.put("stocks", rt);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        m_uid = LoginActivity.get_muid();
        DocumentReference stocks_m = db.collection("user_stocks").document(m_uid);
        stocks_m.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        stocks_m.update("stocks", FieldValue.arrayUnion(stocktad));
                    } else {
                        db.collection("user_stocks").document(m_uid).set(user);
                        stocks_m.update("stocks", FieldValue.arrayUnion(stocktad));
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

        System.out.println(stocks_m);
        System.out.println(m_uid);



    }

    void set_pegr(String stocktad, int pegr){
        Map<String, Object> user = new HashMap<String, Object>();
        String rt;
        rt = "583";
        user.put(stocktad, pegr);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference stocks_m = db.collection("peg_rat").document(m_uid);
        stocks_m.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        stocks_m.set(user);
                    } else {
                        db.collection("peg_rat").document(m_uid).set(user);
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

        System.out.println(stocks_m);
        System.out.println(m_uid);



    }



    void set_bk(String stocktad, int book_v){
        Map<String, Object> user_bv = new HashMap<String, Object>();
        String rt;
        rt = "583";
        user_bv.put(stocktad, book_v);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference stocks_m = db.collection("book_v").document(m_uid);
        stocks_m.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        stocks_m.set(user_bv);
                    } else {
                        db.collection("book_v").document(m_uid).set(user_bv);
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

        System.out.println(stocks_m);
        System.out.println(m_uid);



    }

    void set_prsl(String stocktad, int book_v){
        Map<String, Object> user_bv = new HashMap<String, Object>();
        String rt;
        rt = "583";
        user_bv.put(stocktad, book_v);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference stocks_m = db.collection("pri_sl").document(m_uid);
        stocks_m.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        stocks_m.set(user_bv);
                    } else {
                        db.collection("pri_sl").document(m_uid).set(user_bv);
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

        System.out.println(stocks_m);
        System.out.println(m_uid);



    }


    void set_prc(String stocktad, String prc){
        Map<String, Object> user = new HashMap<String, Object>();
        String rt;
        rt = "583";
        user.put(stocktad, prc);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference stocks_m = db.collection("price").document(m_uid);
        stocks_m.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        stocks_m.set(user);
                    } else {
                        db.collection("price").document(m_uid).set(user);
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

        System.out.println(stocks_m);
        System.out.println(m_uid);



    }

    void slct_stck_rm(String stocktad){
        Map<String, Object> user = new HashMap<String, Object>();
        String rt;
        rt = "583";
        user.put("stocks", rt);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference stocks_m = db.collection("user_stocks").document(m_uid);
        stocks_m.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        stocks_m.update("stocks", FieldValue.arrayRemove(stocktad));
                    } else {
                        db.collection("user_stocks").document(m_uid).set(user);
                        stocks_m.update("stocks", FieldValue.arrayRemove(stocktad));
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

        System.out.println(stocks_m);
        System.out.println(m_uid);



    }

    void get_stck(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sel_stck");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    void go_back(){
        pgb.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void go_news(){
        pgb.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    void go_y_stck(){
        pgb.setVisibility(View.VISIBLE);
        Intent intentz = new Intent(this, MainActivity4.class);
        startActivity(intentz);
    }

    void cl_1wk(String symbz){
        Btn5yr.setTextColor(BLACK);
        Btn1yr.setTextColor(BLACK);
        Btn6mo.setTextColor(BLACK);
        Btn3mo.setTextColor(BLACK);
        Btn1wk.setTextColor(WHITE);
        PyObject frame = pyobj.callAttr("pr_stck_1wk",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        ((ImageView) findViewById(R.id.cameraImage)).setImageBitmap(bitmap);
    }
    void cl_3mo(String symbz){
        Btn5yr.setTextColor(BLACK);
        Btn1yr.setTextColor(BLACK);
        Btn6mo.setTextColor(BLACK);
        Btn3mo.setTextColor(WHITE);
        Btn1wk.setTextColor(BLACK);
        PyObject frame = pyobj.callAttr("pr_stck_3m",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        plot = ((ImageView) findViewById(R.id.cameraImage));
        plot.setImageBitmap(bitmap);
        plot.setScaleType(ImageView.ScaleType.FIT_XY);
        plot.setAdjustViewBounds(true);
    }
    void cl_6mo(String symbz){
        Btn5yr.setTextColor(BLACK);
        Btn1yr.setTextColor(BLACK);
        Btn6mo.setTextColor(WHITE);
        Btn3mo.setTextColor(BLACK);
        Btn1wk.setTextColor(BLACK);
        PyObject frame = pyobj.callAttr("pr_stck_6m",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        ((ImageView) findViewById(R.id.cameraImage)).setImageBitmap(bitmap);
    }
    void cl_1yr(String symbz){
        Btn5yr.setTextColor(BLACK);
        Btn1yr.setTextColor(WHITE);
        Btn6mo.setTextColor(BLACK);
        Btn3mo.setTextColor(BLACK);
        Btn1wk.setTextColor(BLACK);
        PyObject frame = pyobj.callAttr("pr_stck_1y",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        ((ImageView) findViewById(R.id.cameraImage)).setImageBitmap(bitmap);
    }
    void cl_5yr(String symbz){
        Btn5yr.setTextColor(WHITE);
        Btn1yr.setTextColor(BLACK);
        Btn6mo.setTextColor(BLACK);
        Btn3mo.setTextColor(BLACK);
        Btn1wk.setTextColor(BLACK);
        PyObject frame = pyobj.callAttr("pr_stck_5y",symbz);
        byte[] frameData = py.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        ((ImageView) findViewById(R.id.cameraImage)).setImageBitmap(bitmap);
    }
}