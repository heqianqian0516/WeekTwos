package com.bwei.weektwos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_time)
    TextView mTextTime;
    private int time=3;
     private Handler handler=new Handler(){
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             if (time==-1){
                Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
             }else{
                 mTextTime.setText("剩余"+time+"s");
                 time--;
                 handler.sendEmptyMessageDelayed(0,1000);
             }
         }
     };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //延迟发送
       handler.sendEmptyMessageDelayed(0,1000);
    }
}
