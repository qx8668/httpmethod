package com.app.code.utils;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewClick(View v) {
        Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String v = HttpAction.getInstance().doGetString("http://192.168.56.1:8080/shinit/test/text.do");
                    L.e("http",v);
                } catch (Exception e) {
                    e.printStackTrace();
                    L.e("http",e.getMessage());
                }
            }
        }).start();
        //////////

        HttpAction.getInstance().doGetClassHandler("http://192.168.56.1:8080/shinit/test/json2.do",
                User.class, new ClassListener() {
                    @Override
                    public <T> void onResult(T t) {
                        User u = (User)t;
                        L.e("http",u.id + "---"+u.name);
                    }

                    @Override
                    public void hasException(Exception e) {
                        L.e("http",e.getMessage());
                    }
                });

        Map<String,String> map = new HashMap<String,String>();
        map.put("id","100");
        map.put("name","gg");
        HttpAction.getInstance().doPostClassHandler("http://192.168.56.1:8080/shinit/test/json3.do", map,
                User.class, new ClassListener() {
                    @Override
                    public <T> void onResult(T t) {
                        User u = (User)t;
                        L.e("http",u.id + "---"+u.name);
                    }

                    @Override
                    public void hasException(Exception e) {
                        L.e("http",e.getMessage());
                    }
                });

        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("name","test");
        Map<String,File> map2 = new HashMap<String,File>();
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + "Download" +  File.separator + "bd_logo1.png");
        map2.put("photo",file);
        HttpAction.getInstance().doPostStringHandler("http://192.168.56.1:8080/shinit/test/upload.do", map1, map2, new StringListener() {
            @Override
            public void onResult(String string) {
                L.e("http",string + "-upload");
            }

            @Override
            public void hasException(Exception e) {
                L.e("http",e.getMessage());
            }
        });
    }
}
