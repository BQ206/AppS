package com.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import java.net.URI;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;


public class MainActivity3 extends AppCompatActivity {

    ProgressBar pgb;

    Python py;

    PyObject pyobj;

    TextView[] title0 = new TextView[10];
    TextView[] media0 = new TextView[10];
    TextView[] date0 = new TextView[10];

    TextView ser;

    Button btyn;
    Button BtnU;
    Button Btnss;
    Button Btndow;
    Button BtnUp;
    Button GenBtn;

    ImageButton homeb;

    String[] sel_stocks = new String[10];

    String[] news_titl = new String[10];

    String[] news_link = new String[10];

    String[] news_date = new String[10];

    String[] news_media = new String[10];

    String[] news_descr = new String[10];

    String[] selcd_s;

    int ie = 0;

    int iy = 0;

    ScrollView scrol;

    int scroll_val = 0;

    int scroll_val_n = 0;

    int page = 0;
    int page_myst = 0;


    int ig = 0;
    int cig = 0;
    int lir = 0;



        @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);



        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        py = Python.getInstance();

        pyobj = py.getModule("news");


        PyObject obj = pyobj.callAttr("callnews");

        int progID2 = getResources().getIdentifier("progbar", "id", getPackageName());
        pgb = (ProgressBar)findViewById(progID2);
        pgb.setVisibility(View.GONE);

        pyobj.callAttr("callnews");

        sel_stocks = pyobj.callAttr("get_s_sto").toJava(String[].class);

        int scrolID = getResources().getIdentifier("scrollView", "id", getPackageName());
        scrol = (ScrollView)findViewById(scrolID);



        int bID = getResources().getIdentifier("Stock_desc", "id", getPackageName());
        btyn = (Button)findViewById(bID);

        int gnbID = getResources().getIdentifier("btssgn", "id", getPackageName());
        GenBtn = (Button)findViewById(gnbID);

        GenBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                news_new();
            }
        });

        btyn.setVisibility(View.GONE);

        news_new();

        int dwnbn = getResources().getIdentifier("btnDo", "id", getPackageName());
        Btndow = (Button)findViewById(dwnbn);

        Btndow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                news_go_down();
            }
        });

        int upbn = getResources().getIdentifier("btnUp", "id", getPackageName());
        BtnUp = (Button)findViewById(upbn);

        BtnUp.setVisibility(View.GONE);


        BtnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                news_go_up();
            }
        });






        int reD = getResources().getIdentifier("medianes0", "id", getPackageName());
        ser = (TextView)findViewById(reD);











        int bnUDf = getResources().getIdentifier("btnssz", "id", getPackageName());
        Btnss = (Button)findViewById(bnUDf);


        Btnss.callOnClick();

        Btnss.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                set_progbar();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        stock_new(iy, 10);
                        Btndow.setVisibility(View.VISIBLE);
                        Btndow.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                stock_go_down();
                            }
                        });
                    }
                }, 5);
            }

        });

        int homidd = getResources().getIdentifier("butzon", "id", getPackageName());
        homeb = (ImageButton) findViewById(homidd);
        homeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                go_home();
            }
        });
    }

    public class MyClickListener implements View.OnClickListener {
        public void onClick(View v){
            go_home();
        }
    }

    void go_home(){
        set_progbar();
        Intent intenty = new Intent(this, MainActivity.class);
        startActivity(intenty);
    }

    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
    }

    void set_progbar(){
        pgb.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
    }



    void stock_go_down(){
        scrol.scrollTo(0, 0);
        page_myst += 1;
        BtnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stock_go_up();
            }
        });
        if(page_myst >= 1)
        {
            BtnUp.setVisibility(View.VISIBLE);
        }
        else{
            BtnUp.setVisibility(View.GONE);
        }
        System.out.println(cig);
        System.out.println(lir);
        PyObject obj = pyobj.callAttr("ch_isr_f");
        stock_new(scroll_val,10);
    }

    void stock_go_up(){
        scrol.scrollTo(0, 0);
        page_myst -= 1;
        if(page_myst >= 1)
        {
            BtnUp.setVisibility(View.VISIBLE);
        }
        else{
            BtnUp.setVisibility(View.GONE);
        }
        PyObject obj = pyobj.callAttr("ch_isr");
        stock_new(scroll_val,10);
    }

    void news_go_down(){
        page += 1;
        scrol.scrollTo(0, 0);
        if(page >= 1)
        {
            BtnUp.setVisibility(View.VISIBLE);
        }
        else{
            BtnUp.setVisibility(View.GONE);
        }
        PyObject obj = pyobj.callAttr("nxt_pge");
        news_new();
    }

    void news_go_up(){
        scrol.scrollTo(0, 0);
        page -= 1;
        if(page >= 1)
        {
            BtnUp.setVisibility(View.VISIBLE);
        }
        else{
            BtnUp.setVisibility(View.GONE);
        }
        PyObject objz = pyobj.callAttr("pre_pge",page);
        news_new();
    }

    void news_new()
    {
        int i = 0;
        while(i < 9)
        {
            PyObject obj2 = pyobj.callAttr("news_r_media",scroll_val_n + i );
            PyObject obj3 = pyobj.callAttr("news_r_date",scroll_val_n + i );
            PyObject obj4 = pyobj.callAttr("news_r_title",scroll_val_n + i);
            PyObject obj5 = pyobj.callAttr("news_r_descr",scroll_val_n + i );
            PyObject obj6;
            obj6 = pyobj.callAttr("news_r_link", scroll_val_n + i);


            System.out.println(obj6.toString());
            String id1 = "titlenews" + i;
            int resID = getResources().getIdentifier(id1, "id", getPackageName());
            title0[i] = (TextView)findViewById(resID);

            String id2 = "medianews" + i;
            int resID2 = getResources().getIdentifier(id2, "id", getPackageName());
            media0[i] = (TextView)findViewById(resID2);

            String id3 = "datenews" + i;
            int resID3 = getResources().getIdentifier(id3, "id", getPackageName());
            date0[i] = (TextView)findViewById(resID3);

            media0[i].setText(obj2.toString());
            date0[i].setText(obj3.toString());
            title0[i].setText(obj4.toString());

            int bIiD = getResources().getIdentifier("Stock_desc", "id", getPackageName());
            btyn = (Button)findViewById(bIiD);
            PyObject obj7 = obj6;
            title0[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(obj7.toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });




            i += 1;
        }
    }

    class MultithreadingDemo extends Thread {
        int incr = 0;

        int intv = 0;

        int intx = 0;

        public MultithreadingDemo(int par1,int par2,int par3) {
            incr = par1;

            intv = par2;

            intx = par3;
            // store parameter for later user
        }

        public void run() {
            if (intv > sel_stocks.length) {
                intv = 0;
                intx += 1;
            }
            System.out.println(incr);
            PyObject stobj5 = pyobj.callAttr("myst_title_t", sel_stocks[intv], intx, intv);
            System.out.println("ff");
            PyObject stobj2 = pyobj.callAttr("ret_date", incr);
            System.out.println("f2");
            PyObject stobj3 = pyobj.callAttr("ret_descr", incr);
            System.out.println("f3");
            PyObject stobj4 = pyobj.callAttr("ret_link", incr);
            System.out.println("f1");
            PyObject stobj = pyobj.callAttr("ret_media", incr);
            news_titl[incr] = stobj5.toString();
            news_media[incr] = stobj.toString();
            news_date[incr] = stobj2.toString();
            news_descr[incr] = stobj3.toString();
            news_link[incr] = stobj4.toString();
            System.out.println('f');
            System.out.println(stobj5.toString());
            System.out.println(stobj4.toString());
        }
    }

    void stock_new(int ii,int amount)
    {
        Instant start = Instant.now();
        Thread myThreads[] = new Thread[10];
        ig = 0;
        while(ig < amount)
        {
            if(cig > sel_stocks.length - 1){
                cig = 0;
                lir += 1;
            }
            myThreads[ig] = new Thread(new MultithreadingDemo(ig,cig,lir));
            myThreads[ig].start();
            try
            {
                Thread.sleep(100);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            if(lir > 9){
                lir = 0;
            }
            ig += 1;
            cig += 1;
        }
        int ur = 0;
        while (ur < myThreads.length){
            try {
                myThreads[ur].join();
            }
            catch(Exception ex)
            {
                System.out.println("Exception has been" +
                        " caught" + ex);
            }
            ur += 1;
        }
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
        int ix = 0;
        while(ix < amount)
        {
            if(ii > 10){
                break;
            }



            String id1 = "titlenews" + ix;
            int resID = getResources().getIdentifier(id1, "id", getPackageName());
            title0[ix] = (TextView)findViewById(resID);

            String id2 = "medianews" + ix;
            int resID2 = getResources().getIdentifier(id2, "id", getPackageName());
            media0[ix] = (TextView)findViewById(resID2);

            String id3 = "datenews" + ix;
            int resID3 = getResources().getIdentifier(id3, "id", getPackageName());
            date0[ix] = (TextView)findViewById(resID3);

            title0[ix].setText(news_titl[ix]);
            media0[ix].setText(news_media[ix]);
            date0[ix].setText(news_date[ix]);

            int bIiD = getResources().getIdentifier("Stock_desc", "id", getPackageName());
            btyn = (Button)findViewById(bIiD);

            String newdes = news_descr[ix];

            String newlink = news_link[ix];

            title0[ix].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(newlink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });



            ix += 1;
            ie += 1;
            if(ie >= sel_stocks.length)
            {
                ie = 0;
                ii += 1;
                iy += 1;
            }
        }
        pgb.setVisibility(View.GONE);
    }
}