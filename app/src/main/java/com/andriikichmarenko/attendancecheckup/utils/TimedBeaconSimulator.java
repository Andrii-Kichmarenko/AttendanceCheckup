package com.andriikichmarenko.attendancecheckup.utils;

import android.util.Log;

import org.altbeacon.beacon.AltBeacon;
import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TimedBeaconSimulator implements org.altbeacon.beacon.simulator.BeaconSimulator {
	private static final String TAG = "TimedBeaconSimulator";
	private boolean USE_SIMULATED_BEACONS = true;

	private List<Beacon> beacons;

	public TimedBeaconSimulator(){
		beacons = new ArrayList<>();
	}

	public List<Beacon> getBeacons(){
		return beacons;
	}

	public void createBasicSimulatedBeacons(){
		if (USE_SIMULATED_BEACONS) {
			Beacon beacon1 = new AltBeacon.Builder().setBluetoothName("Agnieszka Bednarczyk - s12968")
					.setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997A").setId2("1").setId3("1").setRssi(-55).setTxPower(-55).build();
			Beacon beacon2 = new AltBeacon.Builder().setBluetoothName("Paweł Bochiński - s13681")
					.setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997B").setId2("1").setId3("2").setRssi(-55).setTxPower(-55).build();
			Beacon beacon3 = new AltBeacon.Builder().setBluetoothName("Adrian Bramowicz - s13994")
					.setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997C").setId2("1").setId3("3").setRssi(-55).setTxPower(-55).build();
			Beacon beacon4 = new AltBeacon.Builder().setBluetoothName("Norbert Częścik - s13751")
					.setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997D").setId2("1").setId3("4").setRssi(-55).setTxPower(-55).build();
			Beacon beacon5 = new AltBeacon.Builder().setBluetoothName("Michał Dąbrowski - s14273")
					.setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997E").setId2("1").setId3("3").setRssi(-55).setTxPower(-55).build();
			Beacon beacon6 = new AltBeacon.Builder().setBluetoothName("Adam Filipp - s13771")
					.setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997F").setId2("1").setId3("4").setRssi(-55).setTxPower(-55).build();
			beacons.add(beacon1);
			beacons.add(beacon2);
			beacons.add(beacon3);
			beacons.add(beacon4);
			beacons.add(beacon5);
			beacons.add(beacon6);
			Log.d(TAG, "run: add all beacons");
		}
	}

	private ScheduledExecutorService scheduleTaskExecutor;

//	Simulates a new beacon every second until it runs out of new ones to add.
	public void createTimedSimulatedBeacons(){
		if (USE_SIMULATED_BEACONS){
			beacons = new ArrayList<Beacon>();
            Beacon beacon1 = new AltBeacon.Builder().setBluetoothName("Agnieszka Bednarczyk - s12968")
                    .setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997A").setId2("1").setId3("1").setRssi(-55).setTxPower(-55).build();
            Beacon beacon2 = new AltBeacon.Builder().setBluetoothName("Paweł Bochiński - s13681")
                    .setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997B").setId2("1").setId3("2").setRssi(-55).setTxPower(-55).build();
            Beacon beacon3 = new AltBeacon.Builder().setBluetoothName("Adrian Bramowicz - s13994")
                    .setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997C").setId2("1").setId3("3").setRssi(-55).setTxPower(-55).build();
            Beacon beacon4 = new AltBeacon.Builder().setBluetoothName("Norbert Częścik - s13751")
                    .setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997D").setId2("1").setId3("4").setRssi(-55).setTxPower(-55).build();
			Beacon beacon5 = new AltBeacon.Builder().setBluetoothName("Michał Dąbrowski - s14273")
					.setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997E").setId2("1").setId3("3").setRssi(-55).setTxPower(-55).build();
			Beacon beacon6 = new AltBeacon.Builder().setBluetoothName("Adam Filipp - s13771")
					.setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997F").setId2("1").setId3("4").setRssi(-55).setTxPower(-55).build();
			beacons.add(beacon1);
			beacons.add(beacon2);
			beacons.add(beacon3);
			beacons.add(beacon4);
			beacons.add(beacon5);
			beacons.add(beacon6);
			
			final List<Beacon> finalBeacons = new ArrayList<>(beacons);
			beacons.clear();
			scheduleTaskExecutor= Executors.newScheduledThreadPool(5);

			// This schedules an beacon to appear every 10 seconds:
			scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
				public void run() {
					try{
						//putting a single beacon back into the beacons list.
						if (finalBeacons.size() > beacons.size()) {
							beacons.add(finalBeacons.get(beacons.size()));
							Log.d(TAG, "run: add new beacon");
						}else {
							scheduleTaskExecutor.shutdown();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}, 0, 1, TimeUnit.SECONDS);
		} 
	}

}