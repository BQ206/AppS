package com.android.myapplication;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.common.util.IOUtils;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {

    public static final String Extra_symbol = "com.android.myapplication.Extra_symbol";
    public static final String Extra_name = "com.android.myapplication.Extra_name";
    public static final String Extra_prce = "com.android.myapplication.Extra_prce";
    TextView textView;
    Button Btn;
    Button BtnU;

    ProgressBar pgb;

    ImageButton Act3;
    ImageButton go_ystb;

    TextView Btnsort;

    TextView Btnsort_symb;
    ScrollView scrol;

    TextView symb1;
    TextView pri1;
    TextView rat1;
    TextView nam1;

    TextView symb2;
    TextView pri2;
    TextView rat2;
    TextView nam2;

    TextView symb3;
    TextView pri3;
    TextView rat3;
    TextView nam3;

    TextView symb4;
    TextView pri4;
    TextView rat4;
    TextView nam4;

    TextView symb5;
    TextView pri5;
    TextView rat5;
    TextView nam5;

    TextView symb6;
    TextView pri6;
    TextView rat6;
    TextView nam6;

    TextView symb7;
    TextView pri7;
    TextView rat7;
    TextView nam7;

    TextView symb8;
    TextView pri8;
    TextView rat8;
    TextView nam8;

    TextView symb9;
    TextView pri9;
    TextView rat9;
    TextView nam9;

    TextView symb10;
    TextView pri10;
    TextView rat10;
    TextView nam10;

    Python py;

    PyObject pyobj;

    EditText search;

    Button searchbtn;

    int Stock_C = 0;

    OkHttpClient okHttpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int iii = 1;
        String idd = "textview" + Integer.toString(iii);
        int resID = getResources().getIdentifier(idd, "id", getPackageName());
        textView = (TextView)findViewById(resID);

        System.out.println("doo");
        if(!foregroundServiceRunning()) {
            Intent serviceIntent;
            serviceIntent = new Intent(this, MyForegroundService.class);
            startForegroundService(serviceIntent);
        }

        createNotificationChannel();


        int scrolID = getResources().getIdentifier("scrollView", "id", getPackageName());
        scrol = (ScrollView)findViewById(scrolID);

        int bnresID = getResources().getIdentifier("btn", "id", getPackageName());
        Btn = (Button)findViewById(bnresID);

        int bnUresID = getResources().getIdentifier("btnUp", "id", getPackageName());
        BtnU = (Button)findViewById(bnUresID);

        int bnUresIfD = getResources().getIdentifier("sort", "id", getPackageName());
        Btnsort = (TextView)findViewById(bnUresIfD);

        int bnUrsy = getResources().getIdentifier("sortsymb", "id", getPackageName());
        Btnsort_symb = (TextView)findViewById(bnUrsy);

        int bnfD = getResources().getIdentifier("newsim", "id", getPackageName());
        Act3 = (ImageButton) findViewById(bnfD);

        int bnfDz = getResources().getIdentifier("butzyyon", "id", getPackageName());
        go_ystb = (ImageButton) findViewById(bnfDz);


        Act3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_news();
            }
        });

        go_ystb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_y_stck();
            }
        });

        BtnU.setVisibility(View.GONE);

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        int progID2 = getResources().getIdentifier("progbar", "id", getPackageName());
        pgb = (ProgressBar)findViewById(progID2);
        pgb.setVisibility(View.GONE);

        py = Python.getInstance();
        setStockstring(Stock_C);


        pyobj = py.getModule("myscript");



        int srchID = getResources().getIdentifier("plain_text_input", "id", getPackageName());
        search = (EditText)findViewById(srchID);

        int srchbnID = getResources().getIdentifier("search", "id", getPackageName());
        searchbtn = (Button)findViewById(srchbnID);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgb.setVisibility(View.VISIBLE);
                search_stck();
            }
        });

        PyObject obj = pyobj.callAttr("get_stock_symb", 1);




        Btn.setBackgroundColor(Color.parseColor("#048080"));
        BtnU.setBackgroundColor(Color.parseColor("#048080"));

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgb.setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        Stock_C += 10;
                        setStockstring(Stock_C);
                        scrol.scrollTo(0, 0);
                        if(Stock_C >= 10)
                        {
                            BtnU.setVisibility(View.VISIBLE);
                        }
                        else{
                            BtnU.setVisibility(View.GONE);
                        }
                    }
                }, 5);
            }
        });

        BtnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgb.setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Stock_C -= 10;
                        setStockstring(Stock_C);
                        scrol.scrollTo(0, 0);
                        if(Stock_C >= 10)
                        {
                            BtnU.setVisibility(View.VISIBLE);
                        }
                        else{
                            BtnU.setVisibility(View.GONE);
                        }
                    }
                }, 5);
            }
        });

        Btnsort.setText("▲");

        Btnsort_symb.setText("▲");

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        String m_uid = "U44u6sNAyAq7kfgNGd4a";
        RequestBody formbody
                = new FormBody.Builder().add("muid", m_uid)
                .build();
        System.out.println("gdshjkgdsjkjk");

        Btnsort_symb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Btnsort_symb.getText().toString() == "▲") {
                    Btnsort_symb.setText("▼");
                    pyobj.callAttr("sort_symb");
                }
                else{
                    Btnsort_symb.setText("▲");
                    pyobj.callAttr("sort_symb_r");
                }
                setStockstring(Stock_C);
            }
        });

        Btnsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                build_not("","");
                if(Btnsort.getText().toString() == "▲") {
                    Btnsort.setText("▼");
                    pyobj.callAttr("sort_name");
                }
                else{
                    Btnsort.setText("▲");
                    pyobj.callAttr("sort_name_r");
                }
                setStockstring(Stock_C);
            }
        });


    }

    void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    void build_not(String title, String messg ){
        System.out.println("f");
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_money)
                .setContentTitle("title")
                .setContentText("messg");
        NotificationManager notificationManagerz = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManagerz.notify(0, mbuilder.build());
        System.out.println("f");

    }

    void clicktext(String symbz, String namez,String prcez){
        pgb.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra(Extra_symbol, symbz);
        intent.putExtra(Extra_name, namez);
        intent.putExtra(Extra_prce, prcez);
        startActivity(intent);
    }

    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(MyForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    void search_stck(){
        String st_t_sf = search.getText().toString();
        PyObject search = pyobj.callAttr("search_nm", st_t_sf);
        Stock_C = 0;
        resetStrings();
        setStockstring(Stock_C);
    }

    void go_news(){
        pgb.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    void go_y_stck(){
        Intent intentz = new Intent(this, MainActivity4.class);
        startActivity(intentz);
    }

    void resetStrings()
    {

        int ii = 0;
        PyObject pyobj = py.getModule("myscript");

        String idd = "stockna" + Integer.toString(0);
        int resID = getResources().getIdentifier(idd, "id", getPackageName());
        nam1 = (TextView)findViewById(resID);
        nam1.setText("");

        String idd1 = "stocksymb" + Integer.toString(0);
        int resID1 = getResources().getIdentifier(idd1, "id", getPackageName());
        symb1 = (TextView)findViewById(resID1);
        symb1.setText("");


        String idd2 = "stockpri" + Integer.toString(0);
        int resID2 = getResources().getIdentifier(idd2, "id", getPackageName());
        pri1 = (TextView)findViewById(resID2);
        pri1.setText("");




        ii += 1;


        String id = "stockna" + Integer.toString(1);
        int reID = getResources().getIdentifier(id, "id", getPackageName());
        nam2 = (TextView)findViewById(reID);
        nam2.setText("");

        String id1 = "stocksymb" + Integer.toString(1);
        int reID1 = getResources().getIdentifier(id1, "id", getPackageName());
        symb2 = (TextView)findViewById(reID1);
        symb2.setText("");


        String id2 = "stockpri" + Integer.toString(1);
        int reID2 = getResources().getIdentifier(id2, "id", getPackageName());
        pri2 = (TextView)findViewById(reID2);
        pri2.setText("");


        ii += 1;



        String iddd = "stockna" + Integer.toString(2);
        int rsID = getResources().getIdentifier(iddd, "id", getPackageName());
        nam3 = (TextView)findViewById(rsID);
        nam3.setText("");

        String iddd1 = "stocksymb" + Integer.toString(2);
        int rsID1 = getResources().getIdentifier(iddd1, "id", getPackageName());
        symb3 = (TextView)findViewById(rsID1);
        symb3.setText("");


        String iddd2 = "stockpri" + Integer.toString(2);
        int rsID2 = getResources().getIdentifier(iddd2, "id", getPackageName());
        pri3 = (TextView)findViewById(rsID2);
        pri3.setText("");


        ii += 1;

        String icd = "stockna" + Integer.toString(3);
        int rD = getResources().getIdentifier(icd, "id", getPackageName());
        nam4 = (TextView)findViewById(rD);
        nam4.setText("");

        String icd1 = "stocksymb" + Integer.toString(3);
        int rD1 = getResources().getIdentifier(icd1, "id", getPackageName());
        symb4 = (TextView)findViewById(rD1);
        symb4.setText("");


        String icd2 = "stockpri" + Integer.toString(3);
        int rD2 = getResources().getIdentifier(icd2, "id", getPackageName());
        pri4 = (TextView)findViewById(rD2);
        pri4.setText("");


        ii += 1;


        String iad = "stockna" + Integer.toString(4);
        int rI = getResources().getIdentifier(iad, "id", getPackageName());
        nam1 = (TextView)findViewById(rI);
        nam5.setText("");

        String iad1 = "stocksymb" + Integer.toString(4);
        int rI1 = getResources().getIdentifier(iad1, "id", getPackageName());
        symb5 = (TextView)findViewById(rI1);
        symb5.setText("");


        String iad2 = "stockpri" + Integer.toString(4);
        int rI2 = getResources().getIdentifier(iad2, "id", getPackageName());
        pri5 = (TextView)findViewById(rI2);
        pri5.setText("");


        ii += 1;


        String itd = "stockna" + Integer.toString(5);
        int rID = getResources().getIdentifier(itd, "id", getPackageName());
        nam6 = (TextView)findViewById(rID);
        nam6.setText("");

        String itd1 = "stocksymb" + Integer.toString(5);
        int rID1 = getResources().getIdentifier(itd1, "id", getPackageName());
        symb6 = (TextView)findViewById(rID1);
        symb6.setText("");


        String itd2 = "stockpri" + Integer.toString(5);
        int rID2 = getResources().getIdentifier(itd2, "id", getPackageName());
        pri6 = (TextView)findViewById(rID2);
        pri6.setText("");

        ii += 1;

        String iyd = "stockna" + Integer.toString(6);
        int sID = getResources().getIdentifier(iyd, "id", getPackageName());
        nam7 = (TextView)findViewById(sID);
        nam7.setText("");

        String iyd1 = "stocksymb" + Integer.toString(6);
        int sID1 = getResources().getIdentifier(iyd1, "id", getPackageName());
        symb7 = (TextView)findViewById(sID1);
        symb7.setText("");


        String iyd2 = "stockpri" + Integer.toString(6);
        int sID2 = getResources().getIdentifier(iyd2, "id", getPackageName());
        pri7 = (TextView)findViewById(sID2);
        pri7.setText("");


        ii += 1;



        String ird = "stockna" + Integer.toString(7);
        int ID = getResources().getIdentifier(ird, "id", getPackageName());
        nam8 = (TextView)findViewById(ID);
        nam8.setText("");

        String ird1 = "stocksymb" + Integer.toString(7);
        int ID1 = getResources().getIdentifier(ird1, "id", getPackageName());
        symb8 = (TextView)findViewById(ID1);
        symb8.setText("");


        String ird2 = "stockpri" + Integer.toString(7);
        int ID2 = getResources().getIdentifier(ird2, "id", getPackageName());
        pri8 = (TextView)findViewById(ID2);
        pri8.setText("");


        ii += 1;



        String iud = "stockna" + Integer.toString(8);
        int resIDa = getResources().getIdentifier(iud, "id", getPackageName());
        nam9 = (TextView)findViewById(resIDa);
        nam9.setText("");

        String iud1 = "stocksymb" + Integer.toString(8);
        int resIDa1 = getResources().getIdentifier(iud1, "id", getPackageName());
        symb9 = (TextView)findViewById(resIDa1);
        symb9.setText("");


        String iud2 = "stockpri" + Integer.toString(8);
        int resIDa2 = getResources().getIdentifier(iud2, "id", getPackageName());
        pri9 = (TextView)findViewById(resIDa2);
        pri9.setText("");


        ii += 1;

        String iudt = "stockna" + Integer.toString(9);
        int resIDat = getResources().getIdentifier(iudt, "id", getPackageName());
        nam10 = (TextView)findViewById(resIDat);
        nam10.setText("");

        String iud1t = "stocksymb" + Integer.toString(9);
        int resIDa1t = getResources().getIdentifier(iud1t, "id", getPackageName());
        symb10 = (TextView)findViewById(resIDa1t);
        symb10.setText("");


        String iud2t = "stockpri" + Integer.toString(9);
        int resIDa2t = getResources().getIdentifier(iud2t, "id", getPackageName());
        pri10 = (TextView)findViewById(resIDa2t);
        pri10.setText("");

        ii += 1;


    }

    void setStockstring(int ii)
    {

        PyObject pyobj = py.getModule("myscript");

        PyObject stamnt = pyobj.callAttr("get_stock_amount");

        int stockamnt = stamnt.toInt();

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String idd = "stockna" + Integer.toString(0);
        int resID = getResources().getIdentifier(idd, "id", getPackageName());
        nam1 = (TextView)findViewById(resID);
        PyObject obj = pyobj.callAttr("get_stock_na", ii);
        nam1.setText(obj.toString());

        String idd1 = "stocksymb" + Integer.toString(0);
        int resID1 = getResources().getIdentifier(idd1, "id", getPackageName());
        symb1 = (TextView)findViewById(resID1);
        PyObject symobj = pyobj.callAttr("get_stock_symb", ii);
        symb1.setText(symobj.toString());


        String idd2 = "stockpri" + Integer.toString(0);
        int resID2 = getResources().getIdentifier(idd2, "id", getPackageName());
        pri1 = (TextView)findViewById(resID2);
        PyObject objprice = pyobj.callAttr("price_stock", symobj);
        pri1.setText(objprice.toString());

        nam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj.toString(), obj.toString(), objprice.toString());
            }
        });


        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String id = "stockna" + Integer.toString(1);
        int reID = getResources().getIdentifier(id, "id", getPackageName());
        nam2 = (TextView)findViewById(reID);
        PyObject obj1 = pyobj.callAttr("get_stock_na", ii);
        nam2.setText(obj1.toString());

        String id1 = "stocksymb" + Integer.toString(1);
        int reID1 = getResources().getIdentifier(id1, "id", getPackageName());
        symb2 = (TextView)findViewById(reID1);
        PyObject symobj1 = pyobj.callAttr("get_stock_symb", ii);
        symb2.setText(symobj1.toString());


        String id2 = "stockpri" + Integer.toString(1);
        int reID2 = getResources().getIdentifier(id2, "id", getPackageName());
        pri2 = (TextView)findViewById(reID2);
        PyObject objprice1 = pyobj.callAttr("price_stock", symobj1);
        pri2.setText(objprice1.toString());


        nam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj1.toString(), obj1.toString(), objprice.toString() );
            }
        });


        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String iddd = "stockna" + Integer.toString(2);
        int rsID = getResources().getIdentifier(iddd, "id", getPackageName());
        nam3 = (TextView)findViewById(rsID);
        PyObject obj2 = pyobj.callAttr("get_stock_na", ii);
        nam3.setText(obj2.toString());

        String iddd1 = "stocksymb" + Integer.toString(2);
        int rsID1 = getResources().getIdentifier(iddd1, "id", getPackageName());
        symb3 = (TextView)findViewById(rsID1);
        PyObject symobj2 = pyobj.callAttr("get_stock_symb", ii);
        symb3.setText(symobj2.toString());


        String iddd2 = "stockpri" + Integer.toString(2);
        int rsID2 = getResources().getIdentifier(iddd2, "id", getPackageName());
        pri3 = (TextView)findViewById(rsID2);
        PyObject objprice2 = pyobj.callAttr("price_stock", symobj2);
        pri3.setText(objprice2.toString());

        nam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj2.toString(), obj2.toString(), objprice.toString() );
            }
        });


        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String icd = "stockna" + Integer.toString(3);
        int rD = getResources().getIdentifier(icd, "id", getPackageName());
        nam4 = (TextView)findViewById(rD);
        PyObject obj3 = pyobj.callAttr("get_stock_na", ii);
        nam4.setText(obj3.toString());

        String icd1 = "stocksymb" + Integer.toString(3);
        int rD1 = getResources().getIdentifier(icd1, "id", getPackageName());
        symb4 = (TextView)findViewById(rD1);
        PyObject symobj3 = pyobj.callAttr("get_stock_symb", ii);
        symb4.setText(symobj3.toString());


        String icd2 = "stockpri" + Integer.toString(3);
        int rD2 = getResources().getIdentifier(icd2, "id", getPackageName());
        pri4 = (TextView)findViewById(rD2);
        PyObject objprice3 = pyobj.callAttr("price_stock", symobj3);
        pri4.setText(objprice3.toString());

        nam4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj3.toString(), obj3.toString(), objprice.toString() );
            }
        });

        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String iad = "stockna" + Integer.toString(4);
        int rI = getResources().getIdentifier(iad, "id", getPackageName());
        nam5 = (TextView)findViewById(rI);
        PyObject obj4 = pyobj.callAttr("get_stock_na", ii);
        nam5.setText(obj4.toString());

        String iad1 = "stocksymb" + Integer.toString(4);
        int rI1 = getResources().getIdentifier(iad1, "id", getPackageName());
        symb5 = (TextView)findViewById(rI1);
        PyObject symobj4 = pyobj.callAttr("get_stock_symb", ii);
        symb5.setText(symobj4.toString());


        String iad2 = "stockpri" + Integer.toString(4);
        int rI2 = getResources().getIdentifier(iad2, "id", getPackageName());
        pri5 = (TextView)findViewById(rI2);
        PyObject objprice4 = pyobj.callAttr("price_stock", symobj4);
        pri5.setText(objprice4.toString());

        nam5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj4.toString(), obj4.toString(), objprice.toString() );
            }
        });

        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String itd = "stockna" + Integer.toString(5);
        int rID = getResources().getIdentifier(itd, "id", getPackageName());
        nam6 = (TextView)findViewById(rID);
        PyObject obj5 = pyobj.callAttr("get_stock_na", ii);
        nam6.setText(obj5.toString());

        String itd1 = "stocksymb" + Integer.toString(5);
        int rID1 = getResources().getIdentifier(itd1, "id", getPackageName());
        symb6 = (TextView)findViewById(rID1);
        PyObject symobj5 = pyobj.callAttr("get_stock_symb", ii);
        symb6.setText(symobj5.toString());


        String itd2 = "stockpri" + Integer.toString(5);
        int rID2 = getResources().getIdentifier(itd2, "id", getPackageName());
        pri6 = (TextView)findViewById(rID2);
        PyObject objprice5 = pyobj.callAttr("price_stock", symobj5);
        pri6.setText(objprice5.toString());

        nam6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj5.toString(), obj5.toString(), objprice.toString() );
            }
        });

        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String iyd = "stockna" + Integer.toString(6);
        int sID = getResources().getIdentifier(iyd, "id", getPackageName());
        nam7 = (TextView)findViewById(sID);
        PyObject obj6 = pyobj.callAttr("get_stock_na", ii);
        nam7.setText(obj6.toString());

        String iyd1 = "stocksymb" + Integer.toString(6);
        int sID1 = getResources().getIdentifier(iyd1, "id", getPackageName());
        symb7 = (TextView)findViewById(sID1);
        PyObject symobj6 = pyobj.callAttr("get_stock_symb", ii);
        symb7.setText(symobj6.toString());


        String iyd2 = "stockpri" + Integer.toString(6);
        int sID2 = getResources().getIdentifier(iyd2, "id", getPackageName());
        pri7 = (TextView)findViewById(sID2);
        PyObject objprice6 = pyobj.callAttr("price_stock", symobj6);
        pri7.setText(objprice6.toString());

        nam7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj6.toString(), obj6.toString(), objprice.toString() );
            }
        });

        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String ird = "stockna" + Integer.toString(7);
        int ID = getResources().getIdentifier(ird, "id", getPackageName());
        nam8 = (TextView)findViewById(ID);
        PyObject obj7 = pyobj.callAttr("get_stock_na", ii);
        nam8.setText(obj7.toString());

        String ird1 = "stocksymb" + Integer.toString(7);
        int ID1 = getResources().getIdentifier(ird1, "id", getPackageName());
        symb8 = (TextView)findViewById(ID1);
        PyObject symobj7 = pyobj.callAttr("get_stock_symb", ii);
        symb8.setText(symobj7.toString());


        String ird2 = "stockpri" + Integer.toString(7);
        int ID2 = getResources().getIdentifier(ird2, "id", getPackageName());
        pri8 = (TextView)findViewById(ID2);
        PyObject objprice7 = pyobj.callAttr("price_stock", symobj7);
        pri8.setText(objprice7.toString());

        nam8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj7.toString(), obj7.toString(), objprice.toString() );
            }
        });

        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String iud = "stockna" + Integer.toString(8);
        int resIDa = getResources().getIdentifier(iud, "id", getPackageName());
        nam9 = (TextView)findViewById(resIDa);
        PyObject obj8 = pyobj.callAttr("get_stock_na", ii);
        nam9.setText(obj8.toString());

        String iud1 = "stocksymb" + Integer.toString(8);
        int resIDa1 = getResources().getIdentifier(iud1, "id", getPackageName());
        symb9 = (TextView)findViewById(resIDa1);
        PyObject symobj8 = pyobj.callAttr("get_stock_symb", ii);
        symb9.setText(symobj8.toString());


        String iud2 = "stockpri" + Integer.toString(8);
        int resIDa2 = getResources().getIdentifier(iud2, "id", getPackageName());
        pri9 = (TextView)findViewById(resIDa2);
        PyObject objprice8 = pyobj.callAttr("price_stock", symobj8);
        pri9.setText(objprice8.toString());

        nam9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj8.toString(), obj8.toString(), objprice.toString() );
            }
        });

        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        String iudr = "stockna" + Integer.toString(9);
        int resIDar = getResources().getIdentifier(iudr, "id", getPackageName());
        nam10 = (TextView)findViewById(resIDar);
        PyObject obj9 = pyobj.callAttr("get_stock_na", ii);
        nam10.setText(obj9.toString());

        String iud1r = "stocksymb" + Integer.toString(9);
        int resIDa1r = getResources().getIdentifier(iud1r, "id", getPackageName());
        symb10 = (TextView)findViewById(resIDa1r);
        PyObject symobj9 = pyobj.callAttr("get_stock_symb", ii);
        symb10.setText(symobj9.toString());


        String iud2r = "stockpri" + Integer.toString(9);
        int resIDa2r = getResources().getIdentifier(iud2r, "id", getPackageName());
        pri10 = (TextView)findViewById(resIDa2r);
        PyObject objprice9 = pyobj.callAttr("price_stock", symobj9);
        pri10.setText(objprice9.toString());

        nam10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktext(symobj9.toString(), obj9.toString(), objprice.toString() );
            }
        });

        ii += 1;

        if(ii + Stock_C >= stockamnt){
            return;
        }

        pgb.setVisibility(View.GONE);

    }
    
}