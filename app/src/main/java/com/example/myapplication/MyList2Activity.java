package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MyList2Activity extends ListActivity {

    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;
    private int msgWhat = 7;
    private ArrayList<HashMap<String, String>> ListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list2);

    }
    private void initListView(){
        ListItem = new ArrayList<HashMap<String,String>>();
        for (int i = 0;i<10;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ItemTitle","Rate :" + i);
            map.put("ItemDetail","detail" + i);
            listItems.add(map);

        }

        listItemAdapter = new SimpleAdapter(this,listItems,R.layout.activity_my_list2,
                new String[]{"ItemTile","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetile
        });
    }
}
