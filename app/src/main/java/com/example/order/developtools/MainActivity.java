package com.example.order.developtools;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.order.developtools.view.ExpandListAdapter;
import com.example.order.developtools.view.GroupData;

/**
 * Created by lh, 2020/12/8
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MyService.class);
        MainActivity.this.startService(intent);
        initListView();
    }

    private void initListView() {
        ExpandableListView expandableListView  = findViewById(R.id.expand_list_view);
        ExpandListAdapter listAdapter = new ExpandListAdapter(GroupData.Companion.getGroups(), this);
        expandableListView.setAdapter(listAdapter);
        // 默认展开第一项
        expandableListView.expandGroup(0);
        MainPresenter presenter = new MainPresenter(this);
        listAdapter.setClickListener(presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
