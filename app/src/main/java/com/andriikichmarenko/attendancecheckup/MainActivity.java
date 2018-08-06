package com.andriikichmarenko.attendancecheckup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mBtCheckup = findViewById(R.id.bt_checkup);
        Button mBtNoteMyself = findViewById(R.id.bt_notemyself);
        mBtCheckup.setOnClickListener(this);
        mBtNoteMyself.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.bt_checkup:
                intent = new Intent(this, MonitoringActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_notemyself:
                intent = new Intent(this, BroadcastActivity.class);
                startActivity(intent);
                break;
        }
    }
}
