package com.moa.plugindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.moa.plugin_messenger.Messenger;
import com.moa.plugin_messenger.bean.User;

public class PluginMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_activity_main);

        TextView textView = findViewById(R.id.tv_text);

        // 接收宿主传递的数据
        User user = Messenger.getUser();
        textView.setText("收到数据："+user.getName());
    }

    public void back(View view){
        User user = new User();
        user.setName("我是插件来的数据");
        // 传递数据到宿主app
        Messenger.init(user);
        finish();
    }
}
