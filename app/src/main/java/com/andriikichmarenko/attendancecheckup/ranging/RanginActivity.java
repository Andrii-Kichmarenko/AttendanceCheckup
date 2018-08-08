package com.andriikichmarenko.attendancecheckup.ranging;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.andriikichmarenko.attendancecheckup.R;
import com.andriikichmarenko.attendancecheckup.model.Member;
import com.andriikichmarenko.attendancecheckup.ranging.adapter.ListAdapter;
import com.andriikichmarenko.attendancecheckup.utils.TimedBeaconSimulator;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;


public class RanginActivity extends AppCompatActivity implements BeaconConsumer {
    private static final String TAG = RanginActivity.class.getSimpleName();

    private BeaconManager mBeaconManager = BeaconManager.getInstanceForApplication(this);
    private ArrayList<Beacon> mBeaconsArr;
    private RecyclerView mRecyclerView;
    private LinkedHashMap<Identifier,Member> mMembersMap;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searcher_layout);

        mMembersMap = new LinkedHashMap<>();
        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ListAdapter(getBaseContext(), mMembersMap);
        mRecyclerView.setAdapter(mAdapter);
        mBeaconManager.bind(this);
        loadList();

        BeaconManager.setBeaconSimulator(new TimedBeaconSimulator() );
        ((TimedBeaconSimulator) BeaconManager.getBeaconSimulator()).createTimedSimulatedBeacons();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBeaconManager.unbind(this);
    }

    public void loadList(){
        mAdapter.notifyItemInserted(mMembersMap.size() - 1);
    }

    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.addRangeNotifier(createRangeNotifier());

        try {
            mBeaconManager.startRangingBeaconsInRegion(new Region("pjatk", null, null, null));
        } catch (RemoteException e) {
            Log.i("Exception: ",e.getLocalizedMessage());
        }
    }

    public RangeNotifier createRangeNotifier(){
        RangeNotifier newRangeNotifier = new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > mMembersMap.size()) {
                    Log.i(TAG, "The beacons collection size: " + beacons.size());
                    mBeaconsArr = new ArrayList<>(beacons);
                    for (Beacon beacon : mBeaconsArr) {
                        if (!mMembersMap.containsKey(beacon.getIdentifier(0))) {
                            Identifier identifier = beacon.getIdentifier(0);
                            String[] bluetoothName = beacon.getBluetoothName().split(" - ");
                            Member newMember = new Member(bluetoothName[0], bluetoothName[1]);
                            mMembersMap.put(identifier, newMember);
                            mAdapter.notifyItemInserted(mMembersMap.size() - 1);
                        }
                    }
                }
            }
        };
        return newRangeNotifier;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_refresh:
               clearList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clearList(){
        final int size = mMembersMap.size();
        mMembersMap.clear();
        mBeaconManager.removeAllRangeNotifiers();
        mBeaconManager.addRangeNotifier(createRangeNotifier());
        mAdapter.notifyItemRangeRemoved(0, size);
    }
}