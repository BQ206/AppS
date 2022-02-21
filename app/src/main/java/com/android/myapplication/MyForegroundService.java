package com.android.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyForegroundService extends Service {

    Python py;

    PyObject pyobj;

    String m_uid = "MDrZF4QPbChpH66svIGB";

    OkHttpClient okHttpClient;

    int whi_lis = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("goo");
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if(whi_lis == 0) {
                                listen_server_pr();
                            }
                            if(whi_lis == 1) {
                                listen_server_bk();
                            }
                            if(whi_lis == 2) {
                                listen_server_prsl();
                            }
                            if(whi_lis == 3) {
                                listen_server_pgr();
                                whi_lis = -1;
                            }
                            System.out.println(whi_lis);
                            try {
                                Thread.sleep(300000);
                                whi_lis += 1;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();

        final String CHANNELID = "Foreground Service ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNELID,
                CHANNELID,
                NotificationManager.IMPORTANCE_LOW
        );
        System.out.println("goo");
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                .setContentText("Service is running")
                .setContentTitle("Service enabled")
                .setSmallIcon(R.drawable.ic_launcher_background);

        startForeground(1001, notification.build());





        return super.onStartCommand(intent, flags, startId);
    }

    void Notify(String stckn, ArrayList<Object> arrl){
        Map<String, Object> user = new HashMap<String, Object>();
        String rt;
        rt = "583";
        user.put(stckn, arrl);
        m_uid = "U44u6sNAyAq7kfgNGd4a";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference stocks_m = db.collection("Notif").document(m_uid);
        stocks_m.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        stocks_m.set(user);
                    } else {
                        db.collection("Notif").document(m_uid).set(user);
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

    }
    void listen_server_pr(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        m_uid = "U44u6sNAyAq7kfgNGd4a";
        RequestBody formbody
                = new FormBody.Builder()
                .add("muid", m_uid)
                .build();
        Request request = new Request.Builder().url("http://bq206q.pythonanywhere.com").post(formbody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("hh");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(":");
                try {
                    String kf = response.body().string();
                    if(kf.length() != 3 ) {
                        JSONObject jsObj = new JSONObject(kf);
                        JSONArray keys = jsObj.names();
                        for (int i = 0; i < keys.length(); i++) {
                            String key = keys.getString(i); // Here's your key
                            ArrayList<Object> list = ArrayUtil.convert(jsObj.getJSONArray(key));
                            System.out.println(list.get(0));
                            System.out.println(list);
                        }
                    }
                }
                catch (org.json.JSONException exception) {
                    System.out.println(exception);
                }
            }
        });
    }

    void listen_server_prsl(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        m_uid = "U44u6sNAyAq7kfgNGd4a";
        RequestBody formbody
                = new FormBody.Builder()
                .add("muid", m_uid)
                .build();
        Request request = new Request.Builder().url("http://bq206q.pythonanywhere.com/prsl").post(formbody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("hh");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(":");
                try {
                    System.out.println(":");
                    String kf = response.body().string();
                    if (kf.length() != 3) {
                        JSONObject jsObj = new JSONObject(kf);
                        JSONArray keys = jsObj.names();
                        for (int i = 0; i < keys.length(); i++) {
                            String key = keys.getString(i); // Here's your key
                            String value = jsObj.getString(key); // Here's your value
                            System.out.println(key);
                            System.out.println(value);
                        }
                    }
                }
                catch (org.json.JSONException exception) {
                    System.out.println(exception);
                }
            }
        });
    }

    void listen_server_bk(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        m_uid = "U44u6sNAyAq7kfgNGd4a";
        RequestBody formbody
                = new FormBody.Builder()
                .add("muid", m_uid)
                .build();
        Request request = new Request.Builder().url("http://bq206q.pythonanywhere.com/book").post(formbody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("hh");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(":");
                try {
                    System.out.println(":");
                    String kf = response.body().string();
                    if (kf.length() != 3) {
                        JSONObject jsObj = new JSONObject(kf);
                        JSONArray keys = jsObj.names();
                        for (int i = 0; i < keys.length(); i++) {
                            String key = keys.getString(i); // Here's your key
                            String value = jsObj.getString(key); // Here's your value
                            System.out.println(key);
                            System.out.println(value);
                        }
                    }
                }
                catch (org.json.JSONException exception) {
                    System.out.println(exception);
                }
            }
        });
    }

    void listen_server_pgr(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        m_uid = "U44u6sNAyAq7kfgNGd4a";
        RequestBody formbody
                = new FormBody.Builder()
                .add("muid", m_uid)
                .build();
        Request request = new Request.Builder().url("http://bq206q.pythonanywhere.com/pgr").post(formbody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("hh");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(":");
                try {
                    String kf = response.body().string();
                    if (kf.length() != 3) {
                        System.out.println(":");
                        JSONObject jsObj = new JSONObject(kf);
                        JSONArray keys = jsObj.names();
                        for (int i = 0; i < keys.length(); i++) {
                            String key = keys.getString(i); // Here's your key
                            String value = jsObj.getString(key); // Here's your value
                            System.out.println(key);
                            System.out.println(value);
                        }
                    }
                }
                catch (org.json.JSONException exception) {
                    System.out.println(exception);
                }
            }
        });
    }

    void get_pegr(String stocktad, float pegr){
        Map<String, Object> user = new HashMap<String, Object>();
        String rt;
        rt = "583";
        System.out.println("pegr");
        System.out.println(pegr);
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
                        System.out.println("fk");
                    } else {
                        db.collection("peg_rat").document(m_uid).set(user);
                        System.out.println("sp");
                    }
                } else {
                    System.out.println("fdc");
                }
            }
        });

        System.out.println(stocks_m);
        System.out.println(m_uid);



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

