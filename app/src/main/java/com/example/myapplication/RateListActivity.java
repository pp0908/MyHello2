package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
    String date[] = {"one","two","three"};
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //因为父类中已经包含布局 setContentView(R.layout.activity_rate_list2);
        //可以用循环添加数据for(int i = 0;i<date.length;i++)
        List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            list1.add("item" + i);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        //把当前界面用adapter管理
        setListAdapter(adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==7){
                    List<String> list2 = (List<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter);

                    Thread t = new Thread(String.valueOf(this));
                    t.start();
                }
                super.handleMessage(msg);
            }

        };
    }

    @Override
    public void run() {
        //获取网络数据，放入list带回到主线程中
        List<String> reList = new ArrayList<String>();

        Message msg = handler.obtainMessage(5);
        msg.obj =reList;
        handler.sendMessage(msg);

    }
}
