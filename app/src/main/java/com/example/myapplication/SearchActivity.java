package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchActivity<RateListActivity> extends AppCompatActivity implements Runnable {

    private final String TAG = "Result";
    TextView result;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        View input = findViewById(R.id.inpText);
        View show = findViewById(R.id.showText);

        //获取sp里保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                    String str = (String) msg.obj;
                    result.setText(str);
                }
                super.handleMessage(msg);
            }
        };

    }

    private void showResult(int inc) {
        Log.i("show", "result=" + result);
        String oldScore = (String) result.getText();
        String newResult = null;
        result.setText("" + newResult);

    }

    @Override
    public void run() {
        Log.i(TAG, "run:run()...");
        for (int i = 1; i < 6; i++) {
            Log.i(TAG, "run: i = " + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //获取Msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        msg.obj = "Hello from run()";
        handler.sendMessage(msg);

        //获取网络数据
        URL url = null;
        try {
            url = new URL("http://swufe.edu.cn");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStreamString(in);
            Log.i(TAG,"run: html=" + html);

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String inputStreamString(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuffer out = new StringBuffer();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        for(; ;){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz < 0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("result");
        Log.i(TAG, "onRestoreInstanceState: ");
        ((TextView)findViewById(R.id.showText)).setText((CharSequence) result);
    }


    public void btn(View view) {
        //获取用户输入
        String str = result.getText().toString();
        float r = 0;
        if(str.length()>0){
            r = Float.parseFloat(str);
        }else{
            //提示用户输入内容
            Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
        }
    }
    SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    editor.putString("result"+ result);
    editor.apply();


}