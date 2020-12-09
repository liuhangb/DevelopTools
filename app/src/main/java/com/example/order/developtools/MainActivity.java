package com.example.order.developtools;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by lh, 2020/12/8
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyService.addEventProcessor(new TaoBaoEventProcessor(getBaseContext()));
        Intent intent = new Intent(this, MyService.class);
        MainActivity.this.startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyService.removeAllEventProcessor();
    }
}
