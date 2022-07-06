package com.haqueit.mpos.app.cardinfo.utilClass;

import java.util.HashMap;

import android.os.Handler;

import com.haqueit.mpos.app.cardinfo.utils.SharedMSG;
import com.haqueit.mpos.app.cardinfo.utils.UIMessage;
import com.whty.tymposapi.IDeviceDelegate;

public class DeviceDelegate implements IDeviceDelegate {

	private Handler handler;

	public DeviceDelegate(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void onConnectedDevice(boolean isSuccess) {
		if (isSuccess) {
			handler.obtainMessage(SharedMSG.SHOW_MSG,
					UIMessage.connected_device_success).sendToTarget();
		} else {
			handler.obtainMessage(SharedMSG.SHOW_MSG,
					UIMessage.connected_device_fail).sendToTarget();
		}
	}

	@Override
	public void onDisconnectedDevice(boolean isSuccess) {
		if (isSuccess) {
			handler.obtainMessage(SharedMSG.SHOW_MSG,
					UIMessage.disconnected_device_success).sendToTarget();
		} else {
			handler.obtainMessage(SharedMSG.SHOW_MSG,
					UIMessage.disconnected_device_fail).sendToTarget();
		}
	}

	@Override
	public void onUpdateWorkingKey(boolean[] isSuccess) {
		if (isSuccess != null) {
			handler.obtainMessage(
					SharedMSG.SHOW_MSG,
					UIMessage.update_TDK + " " + String.valueOf(isSuccess[0])
							+ "\n" + UIMessage.update_PIK + " "
							+ String.valueOf(isSuccess[1]) + "\n"
							+ UIMessage.update_MAK + " "
							+ String.valueOf(isSuccess[2])).sendToTarget();
		} else {
			handler.obtainMessage(SharedMSG.SHOW_MSG,
					UIMessage.update_working_key_fail).sendToTarget();
		}
	}

	@Override
	public void onReadCard(HashMap data) {
		if (data != null) {
			handler.obtainMessage(SharedMSG.SHOW_MSG, data.toString())
					.sendToTarget();
		} else {
			handler.obtainMessage(SharedMSG.SHOW_MSG, UIMessage.read_card_fail)
					.sendToTarget();
		}
	}

	@Override
	public void onReadCardData(HashMap data) {
		handler.obtainMessage(SharedMSG.SHOW_MSG,
				UIMessage.please_enter_password).sendToTarget();
	}

	@Override
	public void onDownGradeTransaction(HashMap data) {
		handler.obtainMessage(SharedMSG.SHOW_MSG,
				UIMessage.downGrade_transaction).sendToTarget();
	}

	@Override
	public void onWaitingcard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetMacWithMKIndex(HashMap arg0) {
		// TODO �Զ����ɵķ������
		if (arg0 != null) {
			handler.obtainMessage(SharedMSG.SHOW_MSG, arg0.toString())
					.sendToTarget();
		} else {
			handler.obtainMessage(SharedMSG.SHOW_MSG,
					UIMessage.calculate_mac_fail).sendToTarget();
		}

	}

	// @Override
	// public void onWaitingcard() {
	// handler.obtainMessage(SharedMSG.SHOW_MSG, "��ˢ����忨").sendToTarget();
	// }

}
