package com.andriikichmarenko.attendancecheckup.broadcast;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.andriikichmarenko.attendancecheckup.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout mFullnameWrapper;
    private TextInputLayout mAddInfoWrapper;
    private Button mSwitchBeaconOnBtn;
    private Button mSwitchBeaconOffBtn;
    private BeaconTransmitter mBeaconTransmitter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_layout);
        mFullnameWrapper = findViewById(R.id.et_fullname_wrapper);
        mAddInfoWrapper = findViewById(R.id.input_addinfo_wrapper);

        mSwitchBeaconOffBtn = findViewById(R.id.bt_switch_beacon_off);
        mSwitchBeaconOffBtn.setOnClickListener(this);
        mSwitchBeaconOnBtn = findViewById(R.id.bt_switch_beacon_on);
        mSwitchBeaconOnBtn.setOnClickListener(this);

        if(BeaconTransmitter.checkTransmissionSupported(this) != BeaconTransmitter.SUPPORTED){
            mSwitchBeaconOnBtn.setEnabled(false);
            Toast.makeText(this, "This device doesn't support beacon advertising", Toast.LENGTH_LONG).show();
        }
    }

    public void checkFieldsBeforeTransmitting(){
        String fullname = mFullnameWrapper.getEditText().getText().toString();
        String addInfo = mAddInfoWrapper.getEditText().getText().toString();
        if (fullname.equals("")) {
            mFullnameWrapper.setError("Please insert your name!");
        } else if (addInfo.equals("")) {
            mAddInfoWrapper.setError("Please insert additional info!");
        } else {
            mFullnameWrapper.setErrorEnabled(false);
            mAddInfoWrapper.setErrorEnabled(false);
            startTransmitting(fullname, addInfo);
        }
    }

    public void startTransmitting(String name, String addInfo){
        Beacon beacon = new Beacon.Builder()
                .setBluetoothName(name + " - " + addInfo)
                .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
                .setId2("1")
                .setId3("2")
                .setManufacturer(0x0118)
                .setTxPower(-59)
                .setDataFields(Arrays.asList(new Long[] {0l}))
                .build();
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
        mBeaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);
        mBeaconTransmitter.startAdvertising(beacon);

        Toast.makeText(this, "Advertising has been started", Toast.LENGTH_LONG).show();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_switch_beacon_on:
                hideKeyboard();
                checkFieldsBeforeTransmitting();
                mSwitchBeaconOffBtn.setEnabled(true);
                mSwitchBeaconOnBtn.setEnabled(false);
                break;
            case R.id.bt_switch_beacon_off:
                if(mBeaconTransmitter.isStarted()) {
                    mBeaconTransmitter.stopAdvertising();
                    mSwitchBeaconOffBtn.setEnabled(false);
                    mSwitchBeaconOnBtn.setEnabled(true);
                    Toast.makeText(this, "Advertising has been stopped", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
