package com.haqueit.mpos.app.cardinfo.utils;

import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.whty.bluetooth.manage.util.BluetoothStruct;

public class BlueToothDeviceReceiver extends BroadcastReceiver {
	public static ArrayList<BluetoothStruct> items;
	public static ArrayList<String> itemsNames;
	private Handler handler;
	private String tag = BlueToothDeviceReceiver.class.getSimpleName();

	public BlueToothDeviceReceiver(Handler mHandler) {
		this.handler = mHandler;
		items = new ArrayList<BluetoothStruct>();
		itemsNames = new ArrayList<String>();
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		Bundle b = intent.getExtras();
		Object[] lstName = b.keySet().toArray();

		// ��ʾ�����յ�����Ϣ����ϸ��
		for (int i = 0; i < lstName.length; i++) {
			String keyName = lstName[i].toString();
			Log.d(keyName, String.valueOf(b.get(keyName)));
		}

		if (BluetoothDevice.ACTION_FOUND.equals(action)) {

			BluetoothDevice bluetoothDevice = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			int index = findBluetoothDevice(bluetoothDevice.getAddress(), items);
			if (index < 0 && bluetoothDevice.getName() != null) {
				items.add(new BluetoothStruct(bluetoothDevice.getName(),
						bluetoothDevice.getAddress(), bluetoothDevice));
				itemsNames.add(bluetoothDevice.getName()+"\n"+bluetoothDevice.getAddress());
				System.out.println(bluetoothDevice.getName()+"  "+bluetoothDevice.getAddress());
				handler.obtainMessage(SharedMSG.Device_Found, bluetoothDevice)
						.sendToTarget();
			}

		}



		if (BluetoothDevice.ACTION_NAME_CHANGED.equals(action)) {

			BluetoothDevice bluetoothDevice = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			int index = findBluetoothDevice(bluetoothDevice.getAddress(), items);
			if (index >= 0) {
				items.remove(index);
				items.add(new BluetoothStruct(bluetoothDevice.getName(),
						bluetoothDevice.getAddress(), bluetoothDevice));
				itemsNames.remove(index);
				itemsNames.add(bluetoothDevice.getName()+"\n"+bluetoothDevice.getAddress());
				handler.obtainMessage(SharedMSG.Device_Found, bluetoothDevice)
						.sendToTarget();
			}

		}

		if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
			Log.e(tag, "�յ����������Ĺ㲥XXXXX");
		}

		if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
			Log.e(tag, "�յ��������ӶϿ��Ĺ㲥XXXXX");
			handler.obtainMessage(SharedMSG.Device_Disconnected).sendToTarget();
		} else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
			int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
			Log.e(tag, "����������״̬�ı�XXXXX,state:" + state);
			if (state == BluetoothAdapter.STATE_OFF) {
				Log.e(tag, "�����������ر�");
			} else if (state == BluetoothAdapter.STATE_ON) {
				Log.e(tag, "��������������");
				// BluetoothAdapter.getDefaultAdapter().startDiscovery();
			}
		} else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED
				.equals(action)) {
			int state = intent.getIntExtra(
					BluetoothAdapter.EXTRA_CONNECTION_STATE, -1);
			Log.e(tag, "��������������״̬�ı�YYYYYYYYY,state:" + state);
			BluetoothAdapter.getDefaultAdapter().enable();
		}
	}

	private int findBluetoothDevice(String mac,
			ArrayList<BluetoothStruct> deviceList) {
		for (int i = 0; i < deviceList.size(); i++) {
			if (((BluetoothStruct) deviceList.get(i)).getMac().equals(mac))
				return i;
		}
		return -1;
	}

}
