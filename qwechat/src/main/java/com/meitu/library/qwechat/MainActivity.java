package com.meitu.library.qwechat;

import static com.meitu.library.qwechat.utils.SharedPreferencesUtils.KEY_ADD_FRIEND_INFO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.meitu.library.qwechat.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MyService.class);
        MainActivity.this.startService(intent);
        EditText editText = findViewById(R.id.add_friend_info);
        String addInfo = (String) SharedPreferencesUtils.getInstance(this).get(KEY_ADD_FRIEND_INFO, "");
        if (!TextUtils.isEmpty(addInfo)) {
            editText.setText(addInfo);
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtils.getInstance(getApplication()).put(KEY_ADD_FRIEND_INFO, s.toString());
            }
        });
    }

    public void onGoAccessibilitySetting(View v){
        handleAccessibilitySettings();
    }

    private void handleAccessibilitySettings() {
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.no_accessibility_setting), Toast.LENGTH_SHORT).show();
        }
    }
}