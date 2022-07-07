package com.haqueit.mpos.app.cardinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haqueit.mpos.app.MainActivity;
import com.haqueit.mpos.app.cardinfo.utilClass.DeviceDelegate;
import com.haqueit.mpos.app.cardinfo.utils.BlueToothDeviceReceiver;
import com.haqueit.mpos.app.cardinfo.utils.CardInfo;
import com.haqueit.mpos.app.cardinfo.utils.DeviceDialogUtil;
import com.haqueit.mpos.app.cardinfo.utils.PrintMessage;
import com.haqueit.mpos.app.cardinfo.utils.SharedMSG;
import com.haqueit.mpos.app.cardinfo.utils.UIMessage;
import com.haqueit.mpos.app.util.ManifestPermistion;
import com.whty.bluetooth.manage.util.BlueToothUtil;
import com.whty.bluetoothsdk.util.Utils;
import com.whty.comm.inter.ICommunication;
import com.whty.device.utils.GPMethods;
import com.whty.tymposapi.DeviceApi;
import com.whty.tymposapi.ToolVersion;
import com.haqueit.mpos.app.R;

public class ReadCardActivity extends AppCompatActivity {
    private Button initDevice, connDevice, disconnDevice, isConnected, getSN,
            getCSN, getSubApplicationInfo, getVersion, cancel;
    private Button updateWorkingKey, readCard, getPinBlock, ICTradeResponse,
            confirmTransaction, getMac, uploadEmvConfig, getEmvConfig,
            updateAID, updateRID, getcv, showLCD, clearLCD, uploadEmvTime,
            setTime, upgrade, openChannel, transCommand, closeChannel;
    ;
    // private Button printSalesSlip;
    // private Button upgradeDevice;
    // private Button changeLanguage;
    private TextView showstatus, showResult;
    private EditText inputpwd;

    private DeviceApi deviceApi;
    private DeviceDelegate delegate;
    private Handler mHandler;

    private DialogHandler dialogHandler;
    private BroadcastReceiver receiver = null;
    private DeviceDialogUtil devicedialog = null;
    private BluetoothDevice currentDevice;
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private String mDeviceAddress;
    private String mDeviceName;

    private boolean isInited = false;
    private boolean deviceConnected = false;
    // ÓïÑÔÖÖÀà 0Îª¼òÌåÖÐÎÄ£¬1Îª·±ÌåÖÐÎÄ£¬2ÎªÓ¢ÎÄ
    private int languageID = 2;

    // Ë¢¿¨ÐÅÏ¢
    private CardInfo cardInfo = null;
    // ´òÓ¡Í¼Æ¬ÐÅÏ¢
    private String picInfo = null;
    private String tag = ReadCardActivity.class.getSimpleName();

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.BLUETOOTH_ADMIN,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_CONNECT,
            android.Manifest.permission.BLUETOOTH_SCAN,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_LOGS,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Ó¦ÓÃÄÚÅäÖÃÓïÑÔ
        Resources resources = getResources();// »ñµÃres×ÊÔ´¶ÔÏó
        Configuration config = resources.getConfiguration();// »ñµÃÉèÖÃ¶ÔÏó
        DisplayMetrics dm = resources.getDisplayMetrics();// »ñµÃÆÁÄ»²ÎÊý£ºÖ÷ÒªÊÇ·Ö±æÂÊ£¬ÏñËØµÈ¡£
        config.locale = Locale.getDefault();// »ñÈ¡ÊÖ»ú±¾µØµÄÓïÑÔ
        Log.d(tag, "Language is " + config.locale.getLanguage());
        // if ("zh".equals(config.locale.getLanguage())) {
        // Log.d(tag, "Country is " + config.locale.getCountry());
        // if ("TW".equals(config.locale.getCountry())) {
        // languageID = 1;
        // } else {
        // languageID = 0;
        // }
        // }
        // // else if ("en".equals(config.locale.getLanguage())) {
        // // languageID = 2;
        // // }
        // else {
        // languageID = 2;
        // }
        Log.d(tag, "language ID is " + languageID);
        resources.updateConfiguration(config, dm);
        UIMessage.setMessage(languageID);
        PrintMessage.setMessage(languageID);

        setContentView(R.layout.activity_read_card);

        initDevice = (Button) findViewById(R.id.initDevice);
        connDevice = (Button) findViewById(R.id.connDevice);
        disconnDevice = (Button) findViewById(R.id.disconnDevice);
        isConnected = (Button) findViewById(R.id.isConnected);
        getSN = (Button) findViewById(R.id.getSN);
        getCSN = (Button) findViewById(R.id.getCSN);
        getSubApplicationInfo = (Button) findViewById(R.id.getSubApplicationInfo);
        getVersion = (Button) findViewById(R.id.getVersion);
        cancel = (Button) findViewById(R.id.cancel);
        updateWorkingKey = (Button) findViewById(R.id.updateWorkingKey);
        readCard = (Button) findViewById(R.id.readCard);
        getPinBlock = (Button) findViewById(R.id.getPinBlock);
        ICTradeResponse = (Button) findViewById(R.id.ICTradeResponse);
        confirmTransaction = (Button) findViewById(R.id.confirmTransaction);
        getMac = (Button) findViewById(R.id.getMac);
        uploadEmvConfig = (Button) findViewById(R.id.uploadEmvConfig);
        getEmvConfig = (Button) findViewById(R.id.getEmvConfig);
        updateAID = (Button) findViewById(R.id.updateAID);
        updateRID = (Button) findViewById(R.id.updateRID);
        getcv = (Button) findViewById(R.id.getcv);
        showLCD = (Button) findViewById(R.id.showLCD);
        clearLCD = (Button) findViewById(R.id.clearLCD);
        uploadEmvTime = (Button) findViewById(R.id.uploadEmvTime);
        setTime = (Button) findViewById(R.id.setTime);
        upgrade = (Button) findViewById(R.id.upgrade);
        openChannel = (Button) findViewById(R.id.openChannel);
        transCommand = (Button) findViewById(R.id.transCommand);
        closeChannel = (Button) findViewById(R.id.closeChannel);
        // upgradeDevice = (Button) findViewById(R.id.upgradeDevice);
        // changeLanguage = (Button) findViewById(R.id.changeLanguage);
        inputpwd = (EditText) findViewById(R.id.inputpwd);

        showstatus = (TextView) findViewById(R.id.showStatus);
        showResult = (TextView) findViewById(R.id.showResult);

        // this.setTitle(this.getTitle());
        this.setTitle(this.getTitle() + " " + ToolVersion.TOOL_VERSION);
        showResult.setText(UIMessage.show_result_text);

        initUI();

        mHandler = new MyHandler();
        delegate = new DeviceDelegate(mHandler);
        deviceApi = new DeviceApi(getApplicationContext());
        deviceApi.setDelegate(delegate);

        // ±£³ÖÆÁÄ»»½ÐÑ×´Ì¬
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // handlerÓÃÓÚ¸úUIµÄ½»»¥
        dialogHandler = new DialogHandler();
        devicedialog = new DeviceDialogUtil(dialogHandler);

        // ¹ã²¥½ÓÊÕÕß½ÓÊÕ¼àÌýÀ¶ÑÀ×´Ì¬£¬È»ºó½«ÐèÒªµÄÐÅÏ¢ÓÉHanlder·Åµ½¶ÓÁÐÒÔ±ã¸üÐÂUIÊ¹ÓÃ
        receiver = new BlueToothDeviceReceiver(dialogHandler);
        IntentFilter intent = new IntentFilter();
        intent.addAction(BluetoothDevice.ACTION_FOUND);
        intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intent.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intent.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intent.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
        intent.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);

        intent.setPriority(-1000);
        getApplicationContext().registerReceiver(receiver, intent);
        mayRequestLocation();

        if (!ManifestPermistion.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


    }

    private void mayRequestLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(
                    getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat
                        .requestPermissions(
                                this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (deviceConnected) {
            new Thread() {
                public void run() {
                    deviceApi.disconnectDevice();

                }
            }.start();
        }
        Log.d(tag, "ReadCardActivity is destroied.");
    }

    @Override
    public void finish() {
        super.finish();
        if (deviceConnected) {
            new Thread() {
                public void run() {
                    deviceApi.disconnectDevice();
                }
            }.start();
        }
        Log.e(tag, "finish() function is involked");
    }

    public void initUI() {
        // ¼àÌý¸÷¸ö°´Å¥µã»÷ÊÂ¼þ
        initDevice.setOnClickListener(new MyOnclickListener());
        connDevice.setOnClickListener(new MyOnclickListener());
        disconnDevice.setOnClickListener(new MyOnclickListener());
        isConnected.setOnClickListener(new MyOnclickListener());
        getSN.setOnClickListener(new MyOnclickListener());
        getCSN.setOnClickListener(new MyOnclickListener());
        getSubApplicationInfo.setOnClickListener(new MyOnclickListener());
        getVersion.setOnClickListener(new MyOnclickListener());
        cancel.setOnClickListener(new MyOnclickListener());
        updateWorkingKey.setOnClickListener(new MyOnclickListener());
        readCard.setOnClickListener(new MyOnclickListener());
        getPinBlock.setOnClickListener(new MyOnclickListener());
        ICTradeResponse.setOnClickListener(new MyOnclickListener());
        confirmTransaction.setOnClickListener(new MyOnclickListener());
        getMac.setOnClickListener(new MyOnclickListener());
        uploadEmvConfig.setOnClickListener(new MyOnclickListener());
        getEmvConfig.setOnClickListener(new MyOnclickListener());
        updateAID.setOnClickListener(new MyOnclickListener());
        updateRID.setOnClickListener(new MyOnclickListener());
        getcv.setOnClickListener(new MyOnclickListener());
        showLCD.setOnClickListener(new MyOnclickListener());
        clearLCD.setOnClickListener(new MyOnclickListener());
        uploadEmvTime.setOnClickListener(new MyOnclickListener());
        setTime.setOnClickListener(new MyOnclickListener());
        upgrade.setOnClickListener(new MyOnclickListener());
        openChannel.setOnClickListener(new MyOnclickListener());
        transCommand.setOnClickListener(new MyOnclickListener());
        closeChannel.setOnClickListener(new MyOnclickListener());
        // upgradeDevice.setOnClickListener(new MyOnclickListener());
        // changeLanguage.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // languageID = ++languageID % 3;
        // switch (languageID) {
        // case 0:
        // initDevice.setText("É¨ÃèÉè±¸");
        // connDevice.setText("Á¬½ÓÉè±¸");
        // disconnDevice.setText("¶Ï¿ªÉè±¸");
        // isConnected.setText("ÊÇ·ñÁ¬½Ó");
        // getSN.setText("È¡SNºÅ");
        // getCSN.setText("È¡PSAM");
        // getSubApplicationInfo.setText("Éè±¸ÐÅÏ¢");
        // getVersion.setText("API°æ±¾");
        // cancel.setText("È¡Ïû½»Ò×");
        // updateWorkingKey.setText("Ç©µ½");
        // readCard.setText("¸´ºÏË¢¿¨");
        // getPinBlock.setText("¼ÓÃÜÃÜÂë");
        // ICTradeResponse.setText("IC¿¨»ØÐ´");
        // confirmTransaction.setText("È·ÈÏ½»Ò×");
        // getMac.setText("¼ÆËãMac");
        // uploadEmvConfig.setText("¼ÓÔØemvÅäÖÃ");
        // getEmvConfig.setText("¶ÁÈ¡emvÅäÖÃ");
        // // upgradeDevice.setText("¹Ì¼þÉý¼¶");
        // changeLanguage.setText("ÇÐ»»ÓïÑÔ");
        // break;
        //
        // case 1:
        // initDevice.setText("’ßÃèÔO‚ä");
        // connDevice.setText("ßB½ÓÔO‚ä");
        // disconnDevice.setText("”àé_ÔO‚ä");
        // isConnected.setText("ÊÇ·ñßB½Ó");
        // getSN.setText("È¡SNÌ–");
        // getCSN.setText("È¡PSAM");
        // getSubApplicationInfo.setText("ÔO‚äÐÅÏ¢");
        // getVersion.setText("API°æ±¾");
        // cancel.setText("È¡Ïû½»Ò×");
        // updateWorkingKey.setText("ºžµ½");
        // readCard.setText("Ñ}ºÏË¢¿¨");
        // getPinBlock.setText("¼ÓÃÜÃÜ´a");
        // ICTradeResponse.setText("IC¿¨»ØŒ‘");
        // confirmTransaction.setText("´_ÕJ½»Ò×");
        // getMac.setText("Ó‹ËãMac");
        // uploadEmvConfig.setText("¼ÓÝdemvÅäÖÃ");
        // getEmvConfig.setText("×xÈ¡emvÅäÖÃ");
        // // upgradeDevice.setText("¹Ì¼þÉý¼‰");
        // changeLanguage.setText("ÇÐ“QÕZÑÔ");
        // break;
        //
        // case 2:
        // initDevice.setText("ScanDevice");
        // connDevice.setText("ConnectDevice");
        // disconnDevice.setText("DisconnectDevice");
        // isConnected.setText("IsConnected");
        // getSN.setText("GetSN");
        // getCSN.setText("GetPSAM");
        // getSubApplicationInfo.setText("DeviceInformation");
        // getVersion.setText("ApiVersion");
        // cancel.setText("CancelTransaction");
        // updateWorkingKey.setText("SignIn");
        // readCard.setText("ReadCard");
        // getPinBlock.setText("EncryptPassword");
        // ICTradeResponse.setText("ICReauthorization");
        // confirmTransaction.setText("ConfirmTransaction");
        // getMac.setText("CalculateMac");
        // uploadEmvConfig.setText("UploadEmvConfig");
        // getEmvConfig.setText("GetEmvConfig");
        //
        // // upgradeDevice.setText("UpgradeDevice");
        // changeLanguage.setText("ChangeLanguage");
        // break;
        // }
        // UIMessage.setMessage(languageID);
        // PrintMessage.setMessage(languageID);
        // showstatus.setText("");
        // showResult.setText(UIMessage.show_result_text);
        // inputpwd.setHint(UIMessage.please_enter_password);
        // }
        // });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(ReadCardActivity.this)
                    .setTitle(UIMessage.exit_dialog_title)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(UIMessage.exit_dialog_message)
                    .setPositiveButton(UIMessage.exit_dialog_positive,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                    System.exit(0);
                                }
                            })
                    .setNegativeButton(UIMessage.exit_dialog_negative, null)
                    .create().show();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    // handler»ñÈ¡¶ÓÁÐÖÐµÄÐÅÏ¢,¸üÐÂUI
    @SuppressLint("HandlerLeak")
    class DialogHandler extends Handler {

        @Override
        public void dispatchMessage(Message msg) {

            super.dispatchMessage(msg);

            switch (msg.what) {
                // ÊÕµ½ÏµÍ³·¢ÏÖÉè±¸µÄ¹ã²¥£¬´«¸øhandler´¦Àí£¬µ¯³ödialog
                case SharedMSG.No_Device_Selected:
                    Toast.makeText(ReadCardActivity.this,
                                    UIMessage.donot_select_device, Toast.LENGTH_SHORT)
                            .show();
                    break;

                // ËÑË÷µ½À¶ÑÀÉè±¸
                case SharedMSG.Device_Found:
                    if (mDeviceAddress == null || mDeviceAddress.length() <= 0) {
                        devicedialog.listDevice(ReadCardActivity.this);
                    }
                    break;

                case SharedMSG.No_Device:
                    Toast.makeText(ReadCardActivity.this,
                                    UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                            .show();
                    break;

                // Ñ¡ÖÐÀ¶ÑÀÉè±¸
                case SharedMSG.Device_Ensured:
                    currentDevice = (BluetoothDevice) msg.obj;


                    if (Build.VERSION.SDK_INT >= 23) {
                        int checkPermission = ContextCompat.checkSelfPermission(
                                getApplicationContext(),
                                Manifest.permission.BLUETOOTH_CONNECT);
                        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat
                                    .requestPermissions(
                                            ReadCardActivity.this,
                                            new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                                            0);
                        }
                    }
                    mDeviceName = currentDevice.getName();
                    mDeviceAddress = currentDevice.getAddress();
                    showResult.setText(UIMessage.selected_device + " "
                            + mDeviceName);
                    break;

                // À¶ÑÀ¶Ï¿ªÁ¬½Ó
                case SharedMSG.Device_Disconnected:
                    deviceConnected = false;
                    showResult.setText(UIMessage.disconnected_device_success);
                    break;
            }
        }
    }

    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler {

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            String show_msg = "";
            switch (msg.what) {

                case SharedMSG.SHOW_MSG:
                    show_msg = (String) msg.obj;
                    showResult.setText(show_msg);
                    if (show_msg.equals(UIMessage.connected_device_success)) {
                        deviceConnected = true;
                    }
                    break;

                case SharedMSG.SHOW_STATUS:
                    show_msg = (String) msg.obj;
                    showstatus.setText(show_msg);
                    break;

                default:
                    break;
            }
        }

    }

    class MyOnclickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            showstatus.setText(UIMessage.click_button + " "
                    + ((Button) v).getText());
            showResult.setText("");
            switch (v.getId()) {
                // É¨ÃèÉè±¸£¨³õÊ¼»¯Éè±¸£©
                case R.id.initDevice:
                    if (!isInited) {
                        if (deviceApi.initDevice(ICommunication.BLUETOOTH_DEVICE)) {
                            showResult.setText(UIMessage.init_device_success);
                            isInited = true;
                        } else {
                            showResult.setText(UIMessage.init_device_fail);
                        }
                    }
                    if (!deviceConnected) {
                        mDeviceAddress = null;


                        if (Build.VERSION.SDK_INT >= 23) {
                            int checkPermission = ContextCompat.checkSelfPermission(
                                    getApplicationContext(),
                                    Manifest.permission.BLUETOOTH_SCAN);
                            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat
                                        .requestPermissions(
                                                ReadCardActivity.this,
                                                new String[] { Manifest.permission.BLUETOOTH_SCAN },
                                                0);
                            }
                        }
                        btAdapter.cancelDiscovery();
                        btAdapter.startDiscovery();
                        if (BlueToothUtil.mDialog != null) {
                            BlueToothUtil.mDialog = null;
                        }
                        // BlueToothUtil.items.clear();
                        BlueToothDeviceReceiver.items.clear();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.connected_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                // Á¬½ÓÉè±¸
                case R.id.connDevice:
                    if (showResult.getText().toString()
                            .equals(UIMessage.connecting_device)) {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.connecting_device, Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        if (deviceConnected) {
                            Toast.makeText(ReadCardActivity.this,
                                    UIMessage.connected_device + " " + mDeviceName,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            showResult.setText(UIMessage.connecting_device);
                            btAdapter.cancelDiscovery();
                            if (mDeviceAddress != null
                                    && mDeviceAddress.length() > 0) {
                                new Thread() {
                                    public void run() {
                                        Looper.prepare();
                                        deviceApi.connectDevice(mDeviceAddress);
                                        // System.out.println("´òÓ¡»ú²ÎÊý:"
                                        // + deviceApi.getPrinterParams());
                                        // // ¼ÓÈÈÊ±¼ä
                                        // deviceApi.setPrinterParams((byte) 0x01,
                                        // 2);
                                        // // Á½Áª¼ä¸ôÊ±¼ä
                                        // deviceApi.setPrinterParams((byte) 0x02,
                                        // 5);
                                        // // ÐÐ¾à
                                        // deviceApi.setPrinterParams((byte) 0x03,
                                        // 3);
                                    }
                                }.start();
                            } else {
                                showResult.setText(UIMessage.donot_select_device);
                            }
                        }
                    }
                    break;

                // ¶Ï¿ªÁ¬½Ó
                case R.id.disconnDevice:
                    if (deviceConnected) {
                        new Thread() {
                            public void run() {
                                deviceApi.disconnectDevice();
                            }
                        }.start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                case R.id.isConnected:
                    if (deviceApi.isConnected()) {
                        showResult.setText(UIMessage.connected_device + " "
                                + mDeviceName);
                    } else {
                        showResult.setText(UIMessage.donot_connect_device);
                    }
                    break;

                case R.id.getSN:
                    if (deviceConnected) {
                        // showResult.setText("SN:" + deviceApi.getDeviceSN());
                        showResult.setText(deviceApi.getPosInfo().toString());

                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                case R.id.getCSN:
                    if (deviceConnected) {
                        showResult.setText("PSAM:" + deviceApi.getDeviceCSN());
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                // case R.id.getSubApplicationInfo:
                // if (deviceConnected) {
                // String subApplicationInfo = deviceApi
                // .getDeviceSubApplicationParams();
                // if (subApplicationInfo != null) {
                // showResult.setText(subApplicationInfo);
                // }
                // } else {
                // Toast.makeText(ReadCardActivity.this,
                // UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                // .show();
                // }
                // break;

                case R.id.getVersion:
                    showResult.setText(deviceApi.getVersion());
                    break;

                case R.id.cancel:
                    if (deviceConnected) {
                        new Thread() {
                            public void run() {
                                deviceApi.cancel();
                            }
                        }.start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                // ¸üÐÂ¹¤×÷ÃØÔ¿
                case R.id.updateWorkingKey:
                    if (deviceConnected) {
                        new Thread() {
                            public void run() {
                                String tdk = "FA727B2F08101273A17712674D8CF21CAB3F69";
                                String pik = "4F0C5B17E48E20D69A2A284394F729DF46E3EF";
                                String mak = "4baa0a2ce07ba7f63ce732";
                                deviceApi.updateWorkingKey(tdk, pik, mak);
                            }
                        }.start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                // ¸´ºÏË¢¿¨
                case R.id.readCard:
                    if (deviceConnected) {
                        new Thread() {
                            public void run() {
                                // The parameter is£ºline1£»line2£»
                                // inputType£¨0x00:have".",0x01:no"."£©£» time-out
                                HashMap<String, String> amountData = deviceApi
                                        .deviceCustomInput("hello",
                                                "please input amount:",
                                                (byte) 0x01, 20);
                                if (amountData != null
                                        && amountData.get("errorCode").equals(
                                        "9000")) { // success
                                    String amount = amountData.get("amount");
                                    if (Integer.parseInt(amount) == 0) {
                                        // enter is null
                                        return;
                                    }
                                    setName("swipeCardThread");
                                    SimpleDateFormat format = new SimpleDateFormat(
                                            "yyyyMMddHHmmss", Locale.getDefault());
                                    String terminalTime = format.format(new Date());
                                    Log.e(tag, "terminalTime:" + terminalTime);
                                    // ÀýÈç2014-12-03 16:20:55
                                    // ÔòterminalTime´«Èë"141203162055";
                                    // ´«Èë½ð¶îµÄÊ±ºò×¢Òâ²»Òª´«Ð¡Êýµã£¬Èç¹ûÏëÒª´«1.50ÔòÐ´Èë"150";
                                    // ´«Èë½»Ò×ÀàÐÍ (byte)0x00´ú±íÏû·Ñ£¬(byte)0x31´ú±í²éÑ¯Óà¶î
                                    // deviceApi.readCard("150",
                                    // terminalTime.substring(2), (byte) 0x00,
                                    // (byte) 0x64, (byte) 0x00);
                                    Map<String, String> result = deviceApi
                                            .readCard(amount,
                                                    terminalTime.substring(2),
                                                    (byte) 0x00, (byte) 0x64,
                                                    (byte) 0x07);
                                    if (result != null
                                            && "9000".equals(result
                                            .get("errorCode"))) {
                                        // Ë¢¿¨³É¹¦£¬±£´æË¢¿¨Êý¾Ý
                                        cardInfo = new CardInfo();
                                        cardInfo.setCardNo(result.get("cardNumber"));
                                        cardInfo.setAmount("150");
                                        cardInfo.setSwipeCardDate(terminalTime
                                                .substring(0, 8));
                                        cardInfo.setSwipeCardTime(terminalTime
                                                .substring(8));
                                        cardInfo.setValidThru(result
                                                .get("expiryDate"));
                                        cardInfo.setIcData55(result.get("icData"));
                                        Log.d(tag, "Ë¢¿¨ÐÅÏ¢ÒÑ±£´æ");
                                    }
                                }
                            }
                        }.start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                // À¶ÑÀË¢¿¨Í·»ñÈ¡pinBlock
                case R.id.getPinBlock:
                    if (deviceConnected) {
                        String pin = inputpwd.getText().toString();
                        String encPin = deviceApi.getEncPinblock(pin);
                        if (encPin != null) {
                            showResult.setText("pin:" + encPin);
                        }
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                case R.id.ICTradeResponse:
                    if (deviceConnected) {
                        HashMap<String, String> res = deviceApi.ICTradeResponse(
                                "3030", "910ae4280663aea43a313030");
                        if (res.get("errorCode").equals("9000")) {
                            showResult.setText("IC55" + res.get("IC55"));
                        } else {
                            showResult.setText("errorCode" + res.get("errorCode"));
                        }
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                case R.id.confirmTransaction:
                    if (deviceConnected) {
                        deviceApi.confirmTransaction("Transaction Approved");
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                // ÕªÒª¼ÓÃÜ
                case R.id.getMac:
                    if (deviceConnected) {
                        new Thread() {
                            public void run() {
                                String data = "0200702404c030c098111962122632020070732980000000000000000001000002230902100012376212263202007073298d23092205739991617f0094996212263202007073298d1561560000000000001003573999010000023090d000000000000d00000000d00000000f31313034303332363830363030313030303031303534383135367d109d8d4ad0a72a2600000000000000001322000006000000";
                                if ((data.length() % 2) != 0) {
                                    data += "0";
                                }
                                deviceApi.getMacWithMKIndex(0,
                                        Utils.hexString2Bytes(data));
                            }
                        }.start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                // ¼ÓÔØemvÅäÖÃ
                case R.id.uploadEmvConfig:
                    if (deviceConnected) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                InputStream is = null;
                                File newFile = null;
                                try {
                                    // µÃµ½ÎÄ¼þÁ÷
                                    is = getAssets().open("config.txt");
                                    // ½«assetsÀïÃæµÄÄÚÈÝcopyµ½ÄÚÈÝÖÐ,È¡ÄÚ´æÖÐ±£´æµÄfile½øÐÐÉý¼¶
                                    newFile = new File(Environment
                                            .getExternalStorageDirectory(),
                                            "config.txt");
                                    OutputStream outputStream = new FileOutputStream(
                                            newFile);
                                    int len = is.available();
                                    if (len > 0) {
                                        byte[] content = new byte[len];
                                        is.read(content);
                                        Boolean result = deviceApi
                                                .uploadEmvConfig(content);
                                        if (result) {
                                            Log.d("uploadEmvConfig",
                                                    "uploadEmvConfig Successful!");
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }).start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                case R.id.getEmvConfig:
                    if (deviceConnected) {
                        byte[] result = deviceApi.getEmvConfig();
                        if (result != null) {
                            StringBuffer sb = new StringBuffer();
                            for (byte b : result) {
                                sb.append((char) b);
                            }
                            showResult.setText(sb.toString());
                        }
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                case R.id.updateAID:
                    if (deviceConnected) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                InputStream is = null;
                                File newFile = null;
                                try {
                                    // µÃµ½ÎÄ¼þÁ÷
                                    is = getAssets().open("AID.txt");
                                    // ½«assetsÀïÃæµÄÄÚÈÝcopyµ½ÄÚÈÝÖÐ,È¡ÄÚ´æÖÐ±£´æµÄfile½øÐÐÉý¼¶
                                    newFile = new File(Environment
                                            .getExternalStorageDirectory(),
                                            "AID.txt");
                                    OutputStream outputStream = new FileOutputStream(
                                            newFile);
                                    int len = is.available();
                                    if (len > 0) {
                                        byte[] content = new byte[len];
                                        is.read(content);
                                        deviceApi.updateAID(content);
                                        // deviceApi.updateAID(content);
                                        // deviceApi.updateAID(content);
                                        // deviceApi.updateAID(content);
                                        // deviceApi.updateAID(content);
                                        // deviceApi.updateAIDOver();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }).start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case R.id.updateRID:
                    if (deviceConnected) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                InputStream is = null;
                                File newFile = null;
                                try {
                                    // deviceApi.clearRID();
                                    // deviceApi
                                    // .updateRID("RID=A000000003\n9F01=000000000000\n9F09=008C\n9F1B=00000000\n9F49=9F3704\nFF0D=DC4000A800\nFF0E=0010000000\nFF0F=DC4004F800\nDFDF10=000000000000\nDFDF11=00\nDFDF12=00\nDFDF06=00"
                                    // .getBytes());
                                    deviceApi
                                            .updateCA("RID=A000000003\n9F01=000000000000\n9F09=008C\n9F22=000000000000\nDF02=000000000000\nDF04=000000000000\n9F1B=00000000\n9F49=9F3704\nFF0D=DC4000A800\nFF0E=0010000000\nFF0F=DC4004F800\nDFDF10=000000000000\nDFDF11=00\nDFDF12=00\nDFDF06=00"
                                                    .getBytes());
                                    // µÃµ½ÎÄ¼þÁ÷
                                    is = getAssets().open("RID.txt");
                                    // ½«assetsÀïÃæµÄÄÚÈÝcopyµ½ÄÚÈÝÖÐ,È¡ÄÚ´æÖÐ±£´æµÄfile½øÐÐÉý¼¶
                                    newFile = new File(Environment
                                            .getExternalStorageDirectory(),
                                            "RID.txt");
                                    OutputStream outputStream = new FileOutputStream(
                                            newFile);
                                    int len = is.available();
                                    if (len > 0) {
                                        byte[] content = new byte[len];
                                        is.read(content);
                                        // deviceApi.updateRID(content);
                                        // // deviceApi.updateRID(content, 2);
                                        // // deviceApi.updateRID(content, 3);
                                        // // deviceApi.updateRID(content, 4);
                                        // // deviceApi.updateRID(content, 5);
                                        // // deviceApi.updateRIDOver();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }).start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case R.id.showLCD:
                    if (deviceConnected) {
                        deviceApi.displayTextOnScreen("Hello World", 10);
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }

                    break;
                case R.id.clearLCD:
                    if (deviceConnected) {
                        deviceApi.clearScreen();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }

                    break;
                case R.id.getcv:
                    if (deviceConnected) {
                        List<String> list = deviceApi.getTWKCheckValues();
                        StringBuffer sb = new StringBuffer();
                        for (String s : list) {
                            sb.append(s + "\n");
                        }
                        showResult.setText(sb.toString());
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case R.id.uploadEmvTime:
                    if (deviceConnected) {
                        showResult.setText(deviceApi.emvConfigLastUploadDateTime()
                                + "");
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case R.id.setTime:
                    if (deviceConnected) {
                        // SimpleDateFormat format = new SimpleDateFormat(
                        // "yyyyMMddHHmmss", Locale.getDefault());
                        // String terminalTime = format.format(new Date());
                        // deviceApi.setTerminalDateTime(Long.parseLong(terminalTime));
                        showResult.setText(deviceApi.queryPower() + "dl");
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                case R.id.upgrade:
                    if (deviceConnected) {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                InputStream is = null;
                                File newFile = null;
                                try {
                                    is = getAssets().open(
                                            "pkg_12481_V02.01.01.R6792_0_0.dat");
                                    // ½«assetsÀïÃæµÄÄÚÈÝcopyµ½ÄÚÈÝÖÐ,È¡ÄÚ´æÖÐ±£´æµÄfile½øÐÐÉý¼¶
                                    newFile = new File(Environment
                                            .getExternalStorageDirectory(),
                                            "pkg_12481_V02.01.01.R6792_0_0.dat");
                                    OutputStream outputStream = new FileOutputStream(
                                            newFile);
                                    int len = is.available();
                                    if (len > 0) {
                                        byte[] content = new byte[len];
                                        is.read(content);
                                        deviceApi.updateTerminalSoftware(content);

                                    }
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }

                case R.id.openChannel:
                    if (deviceConnected) {
                        new Thread() {
                            public void run() {
                                // 00 represents the magnetic strip; 01
                                // representative IC; 02 represents not pick up;
                                deviceApi.openChannel((byte) 0x02);
                            }
                        }.start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case R.id.transCommand:
                    if (deviceConnected) {
                        new Thread() {
                            public void run() {
                                byte[] response = new byte[256];
                                byte[] qpbocCmd = new byte[] { 0x00, (byte) 0xA4,
                                        0x04, 0x00, 0x0E, 0x31, 0x50, 0x41, 0x59,
                                        0x2E, 0x53, 0x59, 0x53, 0x2E, 0x44, 0x44,
                                        0x46, 0x30, 0x31 };
                                int ret = deviceApi.transCommand(qpbocCmd,
                                        qpbocCmd.length, response, 10);
                                Log.e("ret", String.valueOf(ret));
                                if (ret > 0) {
                                    byte[] res = new byte[ret];
                                    System.arraycopy(response, 0, res, 0, ret);
                                    if (res[ret - 2] == (byte) 0x90
                                            && res[ret - 1] == 0x00) {
                                        String result = Utils.bytesToHexString(res,
                                                ret - 2);
                                        Log.e(tag, result);
                                    } else {
                                        Log.e(tag,
                                                "operation failed error code is "
                                                        + GPMethods
                                                        .bytesToHexString(new byte[] {
                                                                res[ret - 2],
                                                                res[ret - 1] }));
                                    }
                                }
                            }
                        }.start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                case R.id.closeChannel:
                    if (deviceConnected) {
                        new Thread() {
                            public void run() {
                                deviceApi.closeChannel((byte) 0x02);
                            }
                        }.start();
                    } else {
                        Toast.makeText(ReadCardActivity.this,
                                        UIMessage.donot_connect_device, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

                // case R.id.upgradeDevice:
                // if (deviceConnected) {
                // new Thread(new Runnable() {
                //
                // @Override
                // public void run() {
                // // String fileName = "pkg_71249_V02.16.00.R1486_0_0.dat";
                // // String fileName = "pkg_63250_V02.16.00.R1486_0_0.dat";
                // String fileName = "pkg_71251_V02.99.00.R0303_0_0.dat";
                // InputStream is = null;
                // File newFile = null;
                // try {
                // // µÃµ½ÎÄ¼þÁ÷
                // is = getAssets().open(fileName);
                // // ½«assetsÀïÃæµÄÄÚÈÝcopyµ½ÄÚÈÝÖÐ,È¡ÄÚ´æÖÐ±£´æµÄfile½øÐÐÉý¼¶
                // newFile = new File(Environment.getExternalStorageDirectory(),
                // "upgradeDevice.dat");
                // // InputStream inputStream = new FileInputStream(newFile);
                // OutputStream outputStream = new FileOutputStream(newFile);
                // int len = is.available();
                // if (len > 0) {
                // byte[] content = new byte[len];
                // is.read(content);
                // System.out.println("Éý¼¶ÎÄ¼þÊý¾ÝµÄ³¤¶È£º " + content.length);
                // deviceApi.upgrade(content, new UpgradeListener() {
                //
                // @Override
                // public void upgradeFail(int code) {
                // mHandler.obtainMessage(
                // SharedMSG.SHOW_MSG, "´íÎóÂëÎª£º" + code).sendToTarget();
                // }
                //
                // @Override
                // public void upgradeDeviceSuccess() {
                // mHandler.obtainMessage(
                // SharedMSG.SHOW_MSG, "¹Ì¼þÉý¼¶³É¹¦£¡").sendToTarget();
                // }
                //
                // @Override
                // public void showProgress(int value) {
                // mHandler.obtainMessage(
                // SharedMSG.SHOW_STATUS, "Éý¼¶½ø¶È£º" + value + "%").sendToTarget();
                // }
                // });
                // } else{
                // System.out.println("ÎÄ¼þÄÚÈÝÎÞÐ§£¡");
                // }
                // outputStream.close();
                // is.close();
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
                // }
                // }).start();
                // } else {
                // Toast.makeText(ReadCardActivity.this, "ÇëÏÈÁ¬½ÓÉè±¸£¡",
                // Toast.LENGTH_SHORT).show();
                // }
                // break;

                default:
                    break;
            }
        }
    }

}