package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyList22Activity extends ListActivity implements Runnable, AdapterView.OnItemClickListener {

    private  String TAG = "mylist2";
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        MyAdapter myAdapter = new MyAdapter(this,R.layout.list_item,listItems);
        this.setListAdapter(myAdapter);

        Thread  t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==7){
                    List<HashMap<String,String>> list2 = (List<HashMap<String,String>>)msg.obj;
                    listItemAdapter = new SimpleAdapter(MyList22Activity.this,listItems,//listItems 数据源
                            R.layout.list_item,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail}
                    );
                   setListAdapter(listItemAdapter);

                }
                super.handleMessage(msg);
            }

        };
        getListView().setOnItemClickListener(this);
    }


    private void initListView(){
        listItems = new ArrayList<HashMap<String, String>>();
        for(int i  = 0;i<10;i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("ItemTitle","Rate:" + i);//标题文字
            map.put("ItemDetail","detail:" + i);//详情描述
        }


        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this,listItems,//listItems 数据源
                R.layout.list_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
                );

    }

    @Override
    public void run() {
        //获取网络数据，放入list带回主线程中
        List<HashMap<String,String>> retList = new ArrayList<HashMap<String, String>>();
        Document doc = null;
        try {
            Thread.sleep(3000);
            HashMap<String, String> map = new HashMap<String, String>();
            String str1 = null;
            String str2 = null;
            map.put("ItemTitle", str1);
            map.put("ItemDetail", str2);
            retList.add(map);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"onItemClick:parent="+parent);
        Log.i(TAG,"onItemClick:view="+view);
        Log.i(TAG,"onItemClick:position="+position);
        Log.i(TAG,"onItemClick:id="+id);
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titlestr = map.get("ItemTitle");
        String detailstr = map.get("ItemDetail");
        Log.i(TAG,"onItemClick:titlestr="+titlestr);
        Log.i(TAG,"onItemClick:detailstr="+detailstr);


        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG,"onItemClick:title2="+title2);
        Log.i(TAG,"onItemClick:detail2="+detail2);

        //打开新的页面，传入参数
        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titlestr);
        rateCalc.putExtra("rate",Float.parseFloat(detailstr));
        startActivity(rateCalc);
    }
}
