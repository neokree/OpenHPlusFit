package fit.hplus.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import fit.hplus.bluetooth.bean.MapData;
import fit.hplus.bluetooth.bean.RealSingleHealthBean;
import fit.hplus.bluetooth.callback.HPlusFitBluetoothListener;
import fit.hplus.bluetooth.callback.HPlusReadCallback;
import fit.hplus.bluetooth.callback.HPlusReadUICallback;
import fit.hplus.bluetooth.callback.HPlusWriteCallback;
import fit.hplus.bluetooth.callback.HPlusWriteUICallback;
import fit.hplus.bluetooth.command.ReceiveCommand;
import fit.hplus.bluetooth.command.SendCommand;
import fit.hplus.bluetooth.dfu.DfuController;
import fit.hplus.bluetooth.dial.DialController;
import fit.hplus.bluetooth.dial.DialFileCallback;
import fit.hplus.bluetooth.scanner.ScannerController;
import fit.hplus.bluetooth.util.HexUtil;
import fit.hplus.bluetooth.util.LogUtil;
import fit.hplus.bluetooth.util.SPUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import no.nordicsemi.android.ble.BleManager;
import no.nordicsemi.android.ble.BleManagerCallbacks;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.callback.DataSentCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;
import no.nordicsemi.android.ble.data.Data;

public class HPlusBleManager extends BleManager<BleManagerCallbacks> implements BleManagerCallbacks {
    public static final int HANDLER_NEXT_SEND_DATA = 2;
    public static final int HANDLER_REMOVE_TIME_OUT = 3;
    public static final int HANDLER_STATUS_CALLBACK = 1;
    public static final int HANDLER_UPDATE_TIME_OUT = 4;
    private static final int MAX_PACKET_SIZE = 20;
    /* access modifiers changed from: private */
    public static volatile String TAG = HPlusBleManager.class.getSimpleName();
    /* access modifiers changed from: private */
    public static final UUID UART_RX_CHARACTERISTIC_UUID = UUID.fromString("14702856-620a-3973-7c78-9cfff0876abd");
    /* access modifiers changed from: private */
    public static final UUID UART_RX_UI_CHARACTERISTIC_UUID = UUID.fromString("14701831-620a-3973-7c78-9cfff0876abd");
    public static final UUID UART_SERVICE_UUID = UUID.fromString("14701820-620a-3973-7c78-9cfff0876abd");
    /* access modifiers changed from: private */
    public static final UUID UART_TX_CHARACTERISTIC_UUID = UUID.fromString("14702853-620a-3973-7c78-9cfff0876abd");
    /* access modifiers changed from: private */
    public static final UUID UART_TX_UI_CHARACTERISTIC_UUID = UUID.fromString("14701830-620a-3973-7c78-9cfff0876abd");
    private static Context mContext;
    private static volatile HPlusBleManager mInstance;
    private byte[] allDayTenMinuteByte;
    /* access modifiers changed from: private */
    public int byteDialSize;
    private byte[] ecgByte;
    private byte[] hrvByte;
    private boolean isSynDataing;
    /* access modifiers changed from: private */
    public String layoutCurrentCrc;
    /* access modifiers changed from: private */
    public int layoutFileCurrentIndex;
    private List<byte[]> layoutList;
    private BleSend mBleSend;
    /* access modifiers changed from: private */
    public CopyOnWriteArrayList<HPlusFitBluetoothListener> mBluetoothList = new CopyOnWriteArrayList<>();
    private int mCurrentAllDatTenMinuteIndex = 0;
    /* access modifiers changed from: private */
    public int mCurrentDialSize;
    private int mCurrentECGIndex;
    private int mCurrentECGPackIndex;
    private int mCurrentHRVIndex;
    private int mCurrentHRVPackIndex;
    private MapData mCurrentMapData;
    private int mCurrentSingleIndex;
    private int mCurrentSingleWorkOutIndex;
    private int mCurrentSleepChartIndex;
    private DfuController mDfuController;
    private DialController mDialController;
    /* access modifiers changed from: private */
    public DialFileCallback mDialFileCallback;
    private Runnable mDiscoverServicesRunnable = new Runnable() {
        public void run() {
            HPlusBleManager.this.disconnect().enqueue();
            HPlusBleManager.this.close();
        }
    };
    private final BleManager<BleManagerCallbacks>.BleManagerGattCallback mGattCallback = new BleManager<BleManagerCallbacks>.BleManagerGattCallback() {
        /* access modifiers changed from: protected */
        public void initialize() {
            HPlusBleManager hPlusBleManager = HPlusBleManager.this;
            hPlusBleManager.setNotificationCallback(hPlusBleManager.mTXCharacteristic).with(HPlusBleManager.this.mReadCallback);
            HPlusBleManager hPlusBleManager2 = HPlusBleManager.this;
            hPlusBleManager2.setNotificationCallback(hPlusBleManager2.mTXUICharacteristic).with(HPlusBleManager.this.mReadUICallback);
            HPlusBleManager hPlusBleManager3 = HPlusBleManager.this;
            hPlusBleManager3.readCharacteristic(hPlusBleManager3.mRXCharacteristic).with((DataReceivedCallback) HPlusBleManager.this.mWriteCallback).enqueue();
            HPlusBleManager hPlusBleManager4 = HPlusBleManager.this;
            hPlusBleManager4.readCharacteristic(hPlusBleManager4.mRXUICharacteristic).with((DataReceivedCallback) HPlusBleManager.this.mWriteUICallback).enqueue();
            HPlusBleManager hPlusBleManager5 = HPlusBleManager.this;
            hPlusBleManager5.enableNotifications(hPlusBleManager5.mTXCharacteristic).enqueue();
            HPlusBleManager hPlusBleManager6 = HPlusBleManager.this;
            hPlusBleManager6.enableNotifications(hPlusBleManager6.mTXUICharacteristic).enqueue();
        }

        /* access modifiers changed from: protected */
        public boolean isRequiredServiceSupported(BluetoothGatt bluetoothGatt) {
            BluetoothGattService service = bluetoothGatt.getService(HPlusBleManager.UART_SERVICE_UUID);
            if (service != null) {
                BluetoothGattCharacteristic unused = HPlusBleManager.this.mRXCharacteristic = service.getCharacteristic(HPlusBleManager.UART_RX_CHARACTERISTIC_UUID);
                BluetoothGattCharacteristic unused2 = HPlusBleManager.this.mTXCharacteristic = service.getCharacteristic(HPlusBleManager.UART_TX_CHARACTERISTIC_UUID);
                HPlusBleManager.this.mTXUICharacteristic = service.getCharacteristic(HPlusBleManager.UART_TX_UI_CHARACTERISTIC_UUID);
                HPlusBleManager.this.mRXUICharacteristic = service.getCharacteristic(HPlusBleManager.UART_RX_UI_CHARACTERISTIC_UUID);
            }
            boolean z = HPlusBleManager.this.mRXCharacteristic != null && (HPlusBleManager.this.mRXCharacteristic.getProperties() & 8) > 0;
            boolean z2 = HPlusBleManager.this.mRXUICharacteristic != null && (HPlusBleManager.this.mRXUICharacteristic.getProperties() & 8) > 0;
            if (z) {
                HPlusBleManager.this.mRXCharacteristic.setWriteType(2);
            }
            if (z2) {
                HPlusBleManager.this.mRXUICharacteristic.setWriteType(1);
            }
            if (HPlusBleManager.this.mRXCharacteristic == null || HPlusBleManager.this.mTXCharacteristic == null || (!z && !z2)) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: protected */
        public void onDeviceDisconnected() {
            BluetoothGattCharacteristic unused = HPlusBleManager.this.mRXCharacteristic = null;
            BluetoothGattCharacteristic unused2 = HPlusBleManager.this.mTXCharacteristic = null;
            HPlusBleManager hPlusBleManager = HPlusBleManager.this;
            hPlusBleManager.mRXUICharacteristic = null;
            hPlusBleManager.mTXUICharacteristic = null;
        }
    };
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 1) {
                String str = (String) message.obj;
                if (HPlusBleManager.this.mBluetoothList != null && HPlusBleManager.this.mBluetoothList.size() > 0) {
                    Iterator it = HPlusBleManager.this.mBluetoothList.iterator();
                    while (it.hasNext()) {
                        ((HPlusFitBluetoothListener) it.next()).onDataWrite(str);
                    }
                }
            } else if (i != 2) {
                if (i != 3) {
                    if (i == 4 && HPlusBleManager.this.mHandler != null) {
                        HPlusBleManager.this.mHandler.postDelayed(HPlusBleManager.this.mSendDataRunnable, 10000);
                    }
                } else if (HPlusBleManager.this.mHandler != null) {
                    HPlusBleManager.this.mHandler.removeCallbacks(HPlusBleManager.this.mSendDataRunnable);
                }
            } else if (HPlusBleManager.this.mSendCommand != null) {
                HPlusBleManager.this.mSendCommand.reCancel();
            }
        }
    };
    private List<RealSingleHealthBean> mHealthDataList = new ArrayList();
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic mRXCharacteristic;
    public BluetoothGattCharacteristic mRXUICharacteristic;
    /* access modifiers changed from: private */
    public final HPlusReadCallback mReadCallback = new HPlusReadCallback() {
        public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
            String access$400 = HPlusBleManager.TAG;
            LogUtil.i(access$400, "onDataReceived = " + HexUtil.encodeHexStr(data.getValue()));
            if (data.getValue() != null) {
                HPlusBleManager.this.commandResolve(data.getValue());
            }
        }
    };
    /* access modifiers changed from: private */
    public final HPlusReadUICallback mReadUICallback = new HPlusReadUICallback() {
        public void onDataReceived(BluetoothDevice bluetoothDevice, Data data) {
            String access$400 = HPlusBleManager.TAG;
            LogUtil.i(access$400, "onDataUIReceived = " + HexUtil.encodeHexStr(data.getValue()));
            if (data.getValue() != null && data.getValue().length > 0) {
                byte[] value = data.getValue();
                byte b = value[0] & 255;
                if (b == 241) {
                    String encodeHexStr = HexUtil.encodeHexStr(new byte[]{value[2], value[3]});
                    byte b2 = value[4];
                    if (encodeHexStr.equals(HPlusBleManager.this.layoutCurrentCrc) && b2 == 1) {
                        HPlusBleManager.access$708(HPlusBleManager.this);
                        String unused = HPlusBleManager.this.resCurrentCrc = "";
                        HPlusBleManager.access$908(HPlusBleManager.this);
                        int access$900 = (int) (((((float) HPlusBleManager.this.mCurrentDialSize) * 1.0f) / ((float) HPlusBleManager.this.byteDialSize)) * 100.0f);
                        if (HPlusBleManager.this.mDialFileCallback != null) {
                            HPlusBleManager.this.mDialFileCallback.pushDialProgress(access$900);
                        }
                        HPlusBleManager.this.dealDialLayoutData();
                        if (HPlusBleManager.this.mSendCommand != null) {
                            HPlusBleManager.this.mSendCommand.reCancel();
                        }
                    } else if (b2 == 3) {
                        int unused2 = HPlusBleManager.this.layoutFileCurrentIndex = 0;
                        String unused3 = HPlusBleManager.this.resCurrentCrc = "";
                        HPlusBleManager.this.dealDialLayoutData();
                        if (HPlusBleManager.this.mSendCommand != null) {
                            HPlusBleManager.this.mSendCommand.reCancel();
                        }
                    }
                } else if (b == 245 || b == 246) {
                    HPlusBleManager.this.commandResolve(data.getValue());
                } else if (b == 247) {
                    if (HPlusBleManager.this.mBluetoothList != null && HPlusBleManager.this.mBluetoothList.size() > 0) {
                        Iterator it = HPlusBleManager.this.mBluetoothList.iterator();
                        while (it.hasNext()) {
                            ((HPlusFitBluetoothListener) it.next()).onDataUIWrite(HexUtil.encodeHexStr(data.getValue()));
                        }
                    }
                } else if (b == 248) {
                    if (HPlusBleManager.this.mBluetoothList != null && HPlusBleManager.this.mBluetoothList.size() > 0) {
                        Iterator it2 = HPlusBleManager.this.mBluetoothList.iterator();
                        while (it2.hasNext()) {
                            ((HPlusFitBluetoothListener) it2.next()).onDataUIWrite(HexUtil.encodeHexStr(data.getValue()));
                        }
                    }
                } else if (b != 249) {
                    String encodeHexStr2 = HexUtil.encodeHexStr(new byte[]{value[2], value[3]});
                    byte b3 = value[4] & 255;
                    if (encodeHexStr2.equals(HPlusBleManager.this.resCurrentCrc) && b3 == 1) {
                        HPlusBleManager.access$1308(HPlusBleManager.this);
                        HPlusBleManager.access$908(HPlusBleManager.this);
                        int access$9002 = (int) (((((float) HPlusBleManager.this.mCurrentDialSize) * 1.0f) / ((float) HPlusBleManager.this.byteDialSize)) * 100.0f);
                        if (HPlusBleManager.this.mDialFileCallback != null) {
                            HPlusBleManager.this.mDialFileCallback.pushDialProgress(access$9002);
                        }
                        String unused4 = HPlusBleManager.this.resCurrentCrc = "";
                        HPlusBleManager.this.dealDialResData();
                        if (HPlusBleManager.this.mSendCommand != null) {
                            HPlusBleManager.this.mSendCommand.reCancel();
                        }
                    } else if (b3 == 3) {
                        int unused5 = HPlusBleManager.this.resFileCurrentIndex = 0;
                        String unused6 = HPlusBleManager.this.resCurrentCrc = "";
                        HPlusBleManager.this.dealDialResData();
                        if (HPlusBleManager.this.mSendCommand != null) {
                            HPlusBleManager.this.mSendCommand.reCancel();
                        }
                    }
                } else if (HPlusBleManager.this.mSendCommand != null) {
                    HPlusBleManager.this.mSendCommand.reCancel();
                }
            }
        }
    };
    private ReceiveCommand mReceiveCommand;
    private Runnable mReconnectRunnable = new Runnable() {
        public void run() {
            HPlusBleManager.this.disconnect().enqueue();
            HPlusBleManager.this.close();
        }
    };
    private ScannerController mScanController;
    /* access modifiers changed from: private */
    public SendCommand mSendCommand;
    /* access modifiers changed from: private */
    public Runnable mSendDataRunnable = new Runnable() {
        public void run() {
            LogUtil.d(getClass().getSimpleName(), "sendData Time out");
            if (HPlusBleManager.this.mSendCommand != null) {
                HPlusBleManager.this.mSendCommand.reCancel();
            }
        }
    };
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic mTXCharacteristic;
    public BluetoothGattCharacteristic mTXUICharacteristic;
    /* access modifiers changed from: private */
    public final HPlusWriteCallback mWriteCallback = new HPlusWriteCallback() {
        public void onDataSent(BluetoothDevice bluetoothDevice, Data data) {
            String access$400 = HPlusBleManager.TAG;
            LogUtil.d(access$400, "onDataSent = " + HexUtil.encodeHexStr(data.getValue()));
            byte[] value = data.getValue();
            if (value == null || value.length <= 0) {
                if (HPlusBleManager.this.mSendCommand != null) {
                    HPlusBleManager.this.mSendCommand.reCancel();
                }
            } else if (value[0] == 21 || value[0] == 25 || value[0] == 89 || value[0] == 64 || value[0] == 77) {
                HPlusBleManager.this.mHandler.postDelayed(HPlusBleManager.this.mSendDataRunnable, 10000);
            } else if (HPlusBleManager.this.mSendCommand != null) {
                HPlusBleManager.this.mSendCommand.reCancel();
            }
        }
    };
    /* access modifiers changed from: private */
    public final HPlusWriteUICallback mWriteUICallback = new HPlusWriteUICallback() {
        public void onDataSent(BluetoothDevice bluetoothDevice, Data data) {
            String access$400 = HPlusBleManager.TAG;
            LogUtil.d(access$400, "onDataUISent = " + HexUtil.encodeHexStr(data.getValue()));
            if (data.getValue() != null && data.getValue().length > 0) {
                byte b = data.getValue()[0] & 255;
                if ((b == 245 || b == 246) && HPlusBleManager.this.mSendCommand != null) {
                    HPlusBleManager.this.mSendCommand.reCancel();
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public String resCurrentCrc;
    private int resFileAll;
    private int resFileAllIndex;
    /* access modifiers changed from: private */
    public int resFileCurrentIndex;
    private List<List<byte[]>> resList;
    private byte[] singleCircleByte;
    private byte[] singleWorkOutByte;
    private byte[] sleepChartByte;

    @Deprecated
    public /* synthetic */ void onBatteryValueReceived(BluetoothDevice bluetoothDevice, int i) {
        BleManagerCallbacks.CC.$default$onBatteryValueReceived(this, bluetoothDevice, i);
    }

    @Deprecated
    public /* synthetic */ boolean shouldEnableBatteryLevelNotifications(BluetoothDevice bluetoothDevice) {
        return BleManagerCallbacks.CC.$default$shouldEnableBatteryLevelNotifications(this, bluetoothDevice);
    }

    static /* synthetic */ int access$1308(HPlusBleManager hPlusBleManager) {
        int i = hPlusBleManager.resFileCurrentIndex;
        hPlusBleManager.resFileCurrentIndex = i + 1;
        return i;
    }

    static /* synthetic */ int access$708(HPlusBleManager hPlusBleManager) {
        int i = hPlusBleManager.layoutFileCurrentIndex;
        hPlusBleManager.layoutFileCurrentIndex = i + 1;
        return i;
    }

    static /* synthetic */ int access$908(HPlusBleManager hPlusBleManager) {
        int i = hPlusBleManager.mCurrentDialSize;
        hPlusBleManager.mCurrentDialSize = i + 1;
        return i;
    }

    public static HPlusBleManager get() {
        if (mInstance == null) {
            synchronized (HPlusBleManager.class) {
                if (mInstance == null) {
                    mInstance = new HPlusBleManager();
                }
            }
        }
        return mInstance;
    }

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    private HPlusBleManager() {
        super(mContext);
        setGattCallbacks(this);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBleSend = new BleSend(this);
        this.mDfuController = new DfuController(this, mContext);
        this.mDialController = new DialController(this, mContext);
        this.mScanController = new ScannerController();
        this.mScanController.init(defaultAdapter);
    }

    public BleSend getSendCommand() {
        return this.mBleSend;
    }

    public DfuController getDfuController() {
        return this.mDfuController;
    }

    public DialController getDialController() {
        return this.mDialController;
    }

    public ScannerController getScannerController() {
        return this.mScanController;
    }

    public boolean isBleEnabled() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter != null && defaultAdapter.isEnabled();
    }

    public void setSynData(boolean z) {
        this.isSynDataing = z;
    }

    public boolean getSynData() {
        return this.isSynDataing;
    }

    /* access modifiers changed from: protected */
    public BleManager<BleManagerCallbacks>.BleManagerGattCallback getGattCallback() {
        return this.mGattCallback;
    }

    public void log(int i, String str) {
        super.log(i, str);
        LogUtil.d(TAG, str);
    }

    public void connectDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null && !TextUtils.isEmpty(bluetoothDevice.getAddress()) && !TextUtils.isEmpty(bluetoothDevice.getName())) {
            connect(bluetoothDevice).useAutoConnect(false).timeout(10000).retry(3).before((BeforeCallback) new BeforeCallback() {
                public void onRequestStarted(BluetoothDevice bluetoothDevice) {
                    String access$400 = HPlusBleManager.TAG;
                    LogUtil.d(access$400, "connect before = " + bluetoothDevice.getName());
                }
            }).done((SuccessCallback) new SuccessCallback() {
                public void onRequestCompleted(BluetoothDevice bluetoothDevice) {
                    String access$400 = HPlusBleManager.TAG;
                    LogUtil.d(access$400, "connect done = " + bluetoothDevice.getName());
                }
            }).fail((FailCallback) new FailCallback() {
                public void onRequestFailed(BluetoothDevice bluetoothDevice, int i) {
                    String access$400 = HPlusBleManager.TAG;
                    LogUtil.d(access$400, "connect fail = " + bluetoothDevice.getName());
                    HPlusBleManager.this.cancelCommand();
                    HPlusBleManager.this.resetDialData();
                    if (HPlusBleManager.this.mBluetoothList != null && HPlusBleManager.this.mBluetoothList.size() > 0) {
                        Iterator it = HPlusBleManager.this.mBluetoothList.iterator();
                        while (it.hasNext()) {
                            ((HPlusFitBluetoothListener) it.next()).onDisConnect(bluetoothDevice);
                        }
                    }
                }
            }).enqueue();
        }
    }

    public void disConnectDevice() {
        disconnect().before((BeforeCallback) new BeforeCallback() {
            public void onRequestStarted(BluetoothDevice bluetoothDevice) {
                String access$400 = HPlusBleManager.TAG;
                LogUtil.d(access$400, "disconnect before = " + bluetoothDevice.getName());
            }
        }).done((SuccessCallback) new SuccessCallback() {
            public void onRequestCompleted(BluetoothDevice bluetoothDevice) {
                String access$400 = HPlusBleManager.TAG;
                LogUtil.d(access$400, "disconnect done = " + bluetoothDevice.getName());
            }
        }).fail((FailCallback) new FailCallback() {
            public void onRequestFailed(BluetoothDevice bluetoothDevice, int i) {
                String access$400 = HPlusBleManager.TAG;
                LogUtil.d(access$400, "disconnect fail = " + bluetoothDevice.getName());
            }
        }).enqueue();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mReconnectRunnable);
        }
        Handler handler2 = this.mHandler;
        if (handler2 != null) {
            handler2.removeCallbacks(this.mDiscoverServicesRunnable);
        }
    }

    public void registerBluetoothListener(HPlusFitBluetoothListener hPlusFitBluetoothListener) {
        if (!this.mBluetoothList.contains(hPlusFitBluetoothListener)) {
            this.mBluetoothList.add(hPlusFitBluetoothListener);
        }
    }

    public void unregisterBluetoothListener(HPlusFitBluetoothListener hPlusFitBluetoothListener) {
        this.mBluetoothList.remove(hPlusFitBluetoothListener);
    }

    public void onDeviceConnecting(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onDeviceConnecting");
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onConnecting(bluetoothDevice);
            }
        }
    }

    public void onDeviceConnected(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onDeviceConnected");
        startCommand();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.postDelayed(this.mDiscoverServicesRunnable, 15000);
        }
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onConnectSuccess(bluetoothDevice);
            }
        }
    }

    public void onDeviceDisconnecting(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onDeviceDisconnecting");
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onDisConnecting(bluetoothDevice);
            }
        }
    }

    public void onDeviceDisconnected(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onDeviceDisconnected");
        cancelCommand();
        resetDialData();
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onDisConnect(bluetoothDevice);
            }
        }
    }

    public void onLinkLossOccurred(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onLinkLossOccurred");
        cancelCommand();
        resetDialData();
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onDisConnect(bluetoothDevice);
            }
        }
    }

    public void onServicesDiscovered(BluetoothDevice bluetoothDevice, boolean z) {
        LogUtil.d(TAG, "onServicesDiscovered");
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mReconnectRunnable);
        }
        Handler handler2 = this.mHandler;
        if (handler2 != null) {
            handler2.removeCallbacks(this.mDiscoverServicesRunnable);
        }
        SPUtil.getInstance(mContext).save(SPUtil.PRE_ADDRESS, bluetoothDevice.getAddress());
        SPUtil.getInstance(mContext).save(SPUtil.PRE_NAME, bluetoothDevice.getName());
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onServicesDiscovered(bluetoothDevice);
            }
        }
    }

    public void onDeviceReady(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onDeviceReady");
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onDeviceReady(bluetoothDevice);
            }
        }
    }

    public void onBondingRequired(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onBondingRequired");
    }

    public void onBonded(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onBonded");
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onBonded(bluetoothDevice);
            }
        }
    }

    public void onBondingFailed(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onBondingFailed");
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onBondingFailed(bluetoothDevice);
            }
        }
    }

    public void onError(BluetoothDevice bluetoothDevice, String str, int i) {
        LogUtil.d(TAG, "onError");
    }

    public void onDeviceNotSupported(BluetoothDevice bluetoothDevice) {
        LogUtil.d(TAG, "onDeviceNotSupported");
        CopyOnWriteArrayList<HPlusFitBluetoothListener> copyOnWriteArrayList = this.mBluetoothList;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<HPlusFitBluetoothListener> it = this.mBluetoothList.iterator();
            while (it.hasNext()) {
                it.next().onDeviceNoSupport(bluetoothDevice);
            }
        }
    }

    private void startCommand() {
        if (this.mSendCommand == null) {
            this.mSendCommand = new SendCommand(this);
            this.mSendCommand.start();
        }
    }

    /* access modifiers changed from: private */
    public void cancelCommand() {
        SendCommand sendCommand = this.mSendCommand;
        if (sendCommand != null) {
            sendCommand.cancel();
            this.mSendCommand = null;
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void sendDataWrite(byte[] bArr) {
        if (this.mRXCharacteristic == null) {
            LogUtil.d(TAG, "the bluetooth is not connected");
            return;
        }
        if (this.mSendCommand == null) {
            this.mSendCommand = new SendCommand(this);
        }
        this.mSendCommand.addCommand(bArr, 0);
    }

    public synchronized void sendUIData(byte[] bArr) {
        if (this.mRXUICharacteristic == null) {
            LogUtil.d(TAG, "the bluetooth is not connected");
            return;
        }
        if (this.mSendCommand == null) {
            this.mSendCommand = new SendCommand(this);
        }
        this.mSendCommand.addCommand(bArr, 1);
    }

    public void sendDataCommand(byte[] bArr) {
        if (this.mRXCharacteristic == null) {
            LogUtil.d(TAG, "the bluetooth is not connected");
            return;
        }
        String str = TAG;
        Log.e(str, "sendDataCommand = " + HexUtil.encodeHexStr(bArr));
        this.mRXCharacteristic.setWriteType(2);
        writeCharacteristic(this.mRXCharacteristic, bArr).with((DataSentCallback) this.mWriteCallback).split().enqueue();
    }

    public void sendUIDataWrite(byte[] bArr) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.mRXUICharacteristic;
        if (bluetoothGattCharacteristic == null) {
            LogUtil.d(TAG, "the bluetooth is not connected");
            return;
        }
        bluetoothGattCharacteristic.setWriteType(1);
        writeCharacteristic(this.mRXUICharacteristic, bArr).with((DataSentCallback) this.mWriteUICallback).split().enqueue();
    }

    public void sendDialLayoutCommand(List<byte[]> list, int i, DialFileCallback dialFileCallback) {
        this.mDialFileCallback = dialFileCallback;
        this.layoutList = list;
        this.layoutFileCurrentIndex = 0;
        this.layoutCurrentCrc = "";
        this.byteDialSize += i;
    }

    public void sendDialResCommand(List<List<byte[]>> list, int i, DialFileCallback dialFileCallback) {
        this.mDialFileCallback = dialFileCallback;
        this.resList = list;
        this.resFileAllIndex = 0;
        this.resFileCurrentIndex = 0;
        this.resFileAll = 0;
        this.resCurrentCrc = "";
        this.byteDialSize += i;
        SendCommand sendCommand = this.mSendCommand;
        if (sendCommand != null) {
            sendCommand.reCancel();
        }
        dealDialResData();
        if (dialFileCallback != null) {
            dialFileCallback.pushDialLoading();
        }
    }

    /* access modifiers changed from: private */
    public void dealDialResData() {
        List<List<byte[]>> list = this.resList;
        if (list == null || list.size() <= 0 || this.resFileAllIndex > this.resList.size() - 1) {
            List<List<byte[]>> list2 = this.resList;
            if (list2 != null) {
                list2.clear();
            }
            this.resFileAllIndex = 0;
            this.resFileCurrentIndex = 0;
            this.resFileAll = 0;
            this.resCurrentCrc = "";
            return;
        }
        this.resFileAll = this.resList.size();
        List list3 = this.resList.get(this.resFileAllIndex);
        if (list3 != null && list3.size() > 0 && this.resFileCurrentIndex <= list3.size() - 1) {
            byte[] bArr = (byte[]) list3.get(this.resFileCurrentIndex);
            int i = 0;
            for (byte b : bArr) {
                i += b & 255;
            }
            this.resCurrentCrc = HexUtil.encodeHexStr(new byte[]{(byte) ((i >> 8) & 255), (byte) (i & 255)});
            sendUIData(bArr);
            if (this.resFileCurrentIndex == list3.size() - 1) {
                this.resFileAllIndex++;
                this.resFileCurrentIndex = -1;
            }
        }
        if (this.resFileAll == this.resFileAllIndex) {
            dealDialLayoutData();
        }
    }

    /* access modifiers changed from: private */
    public void dealDialLayoutData() {
        List<byte[]> list = this.layoutList;
        if (list != null && list.size() > 0 && this.layoutFileCurrentIndex <= this.layoutList.size() - 1) {
            byte[] bArr = this.layoutList.get(this.layoutFileCurrentIndex);
            int i = 0;
            for (byte b : bArr) {
                i += b & 255;
            }
            this.layoutCurrentCrc = HexUtil.encodeHexStr(new byte[]{(byte) ((i >> 8) & 255), (byte) (i & 255)});
            sendUIData(bArr);
        }
        List<byte[]> list2 = this.layoutList;
        if (list2 != null && this.layoutFileCurrentIndex == list2.size() - 1) {
            DialFileCallback dialFileCallback = this.mDialFileCallback;
            if (dialFileCallback != null) {
                dialFileCallback.pushDialProgress(100);
                this.mDialFileCallback.pushDialSuccess();
                SendCommand sendCommand = this.mSendCommand;
                if (sendCommand != null) {
                    sendCommand.reCancel();
                }
            }
            int i2 = 0;
            for (byte b2 : this.layoutList.get(this.layoutFileCurrentIndex)) {
                i2 += b2 & 255;
            }
            byte[] bArr2 = {(byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
            this.layoutCurrentCrc = HexUtil.encodeHexStr(bArr2);
            sendUIDataWrite(bArr2);
            this.mCurrentDialSize = 0;
            this.byteDialSize = 0;
        }
    }

    /* access modifiers changed from: private */
    public void resetDialData() {
        this.resFileAllIndex = 0;
        this.resFileCurrentIndex = 0;
        this.resFileAll = 0;
        this.resCurrentCrc = "";
        this.mCurrentDialSize = 0;
        this.byteDialSize = 0;
        this.resFileAllIndex = 0;
        this.resFileCurrentIndex = 0;
        this.resFileAll = 0;
        this.resCurrentCrc = "";
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:232:0x0513  */
    /* JADX WARNING: Removed duplicated region for block: B:237:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void commandResolve(byte[] r14) {
        /*
            r13 = this;
            r0 = 0
            byte r1 = r14[r0]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = 26
            r3 = 0
            r4 = 2
            r5 = 1
            if (r1 == r2) goto L_0x04de
            r2 = 46
            r6 = 8
            if (r1 == r2) goto L_0x04cc
            r2 = 51
            if (r1 == r2) goto L_0x04ba
            r2 = 54
            r7 = 3
            if (r1 == r2) goto L_0x048d
            r2 = 59
            if (r1 == r2) goto L_0x0486
            r2 = 77
            r8 = 4
            if (r1 == r2) goto L_0x0458
            r2 = 86
            r9 = 9
            if (r1 == r2) goto L_0x0404
            r2 = 92
            if (r1 == r2) goto L_0x03a6
            r2 = 97
            java.lang.String r10 = ""
            if (r1 == r2) goto L_0x039d
            r2 = 241(0xf1, float:3.38E-43)
            if (r1 == r2) goto L_0x0396
            r2 = 56
            if (r1 == r2) goto L_0x0373
            r2 = 57
            if (r1 == r2) goto L_0x0373
            r2 = 82
            r11 = 6
            if (r1 == r2) goto L_0x0364
            r2 = 83
            if (r1 == r2) goto L_0x0320
            r2 = 89
            if (r1 == r2) goto L_0x02ae
            r2 = 90
            r12 = 17
            if (r1 == r2) goto L_0x0254
            r2 = 245(0xf5, float:3.43E-43)
            if (r1 == r2) goto L_0x01ce
            r2 = 246(0xf6, float:3.45E-43)
            if (r1 == r2) goto L_0x0148
            switch(r1) {
                case 99: goto L_0x0135;
                case 100: goto L_0x00ef;
                case 101: goto L_0x00d3;
                case 102: goto L_0x048d;
                case 103: goto L_0x04de;
                case 104: goto L_0x02ae;
                case 105: goto L_0x0458;
                case 106: goto L_0x0254;
                case 107: goto L_0x0404;
                case 108: goto L_0x0320;
                case 109: goto L_0x00c0;
                case 110: goto L_0x0060;
                default: goto L_0x005e;
            }
        L_0x005e:
            goto L_0x050d
        L_0x0060:
            fit.hplus.bluetooth.util.HexUtil.encodeHexStr(r14)
            int r1 = r14.length
            if (r1 < r8) goto L_0x050d
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            if (r2 != r5) goto L_0x0075
            r4 = 629(0x275, float:8.81E-43)
            byte[] r4 = new byte[r4]
            r13.allDayTenMinuteByte = r4
            r13.removeSendDataTimeOut()
        L_0x0075:
            r4 = 37
            if (r2 > r1) goto L_0x00b7
            byte[] r4 = r13.allDayTenMinuteByte
            if (r4 == 0) goto L_0x00b7
            int r6 = r13.mCurrentAllDatTenMinuteIndex
            int r8 = r14.length
            int r8 = r8 + r6
            int r8 = r8 - r7
            int r9 = r4.length
            if (r8 > r9) goto L_0x0091
            int r8 = r14.length
            int r8 = r8 - r7
            java.lang.System.arraycopy(r14, r7, r4, r6, r8)
            int r4 = r13.mCurrentAllDatTenMinuteIndex
            int r14 = r14.length
            int r14 = r14 - r7
            int r4 = r4 + r14
            r13.mCurrentAllDatTenMinuteIndex = r4
        L_0x0091:
            if (r2 != r1) goto L_0x050d
            fit.hplus.bluetooth.bean.AllDayTenMinuteThermometer r14 = new fit.hplus.bluetooth.bean.AllDayTenMinuteThermometer
            r14.<init>()
            byte[] r1 = r13.allDayTenMinuteByte
            fit.hplus.bluetooth.util.HexUtil.encodeHexStr(r1)
            byte[] r1 = r13.allDayTenMinuteByte
            r14.unPack(r1)
            fit.hplus.bluetooth.bean.BaseBean r1 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r14.toJson()
            r2 = 20
            r1.<init>(r5, r2, r14)
            r13.mCurrentAllDatTenMinuteIndex = r0
            r13.allDayTenMinuteByte = r3
            r13.requestSendDataTimeOut()
        L_0x00b4:
            r3 = r1
            goto L_0x050d
        L_0x00b7:
            if (r2 != 0) goto L_0x050d
            if (r1 != 0) goto L_0x050d
            r13.updateSendData()
            goto L_0x050d
        L_0x00c0:
            fit.hplus.bluetooth.bean.RealSportData r0 = new fit.hplus.bluetooth.bean.RealSportData
            r0.<init>()
            r0.unPackNew(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r5, r14)
            goto L_0x050d
        L_0x00d3:
            byte r14 = r14[r5]
            r14 = r14 & 255(0xff, float:3.57E-43)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            r0 = 19
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r14)
            r1.append(r10)
            java.lang.String r14 = r1.toString()
            r3.<init>(r5, r0, r14)
            goto L_0x050d
        L_0x00ef:
            fit.hplus.bluetooth.bean.RealSingleHealthBean r0 = new fit.hplus.bluetooth.bean.RealSingleHealthBean
            r0.<init>()
            r0.unPack(r14)
            int r14 = r0.getYear()
            if (r14 != 0) goto L_0x012e
            int r14 = r0.getMonth()
            if (r14 != 0) goto L_0x012e
            int r14 = r0.getDay()
            if (r14 != 0) goto L_0x012e
            java.util.List<fit.hplus.bluetooth.bean.RealSingleHealthBean> r14 = r13.mHealthDataList
            int r14 = r14.size()
            if (r14 <= 0) goto L_0x0123
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            r14 = 18
            java.util.List<fit.hplus.bluetooth.bean.RealSingleHealthBean> r0 = r13.mHealthDataList
            org.json.JSONArray r0 = fit.hplus.bluetooth.bean.RealSingleHealthBean.unPackList(r0)
            r3.<init>(r5, r14, r0)
            java.util.List<fit.hplus.bluetooth.bean.RealSingleHealthBean> r14 = r13.mHealthDataList
            r14.clear()
        L_0x0123:
            r13.removeSendDataTimeOut()
            r13.updateSendData()
            r13.requestSendDataTimeOut()
            goto L_0x050d
        L_0x012e:
            java.util.List<fit.hplus.bluetooth.bean.RealSingleHealthBean> r14 = r13.mHealthDataList
            r14.add(r0)
            goto L_0x050d
        L_0x0135:
            fit.hplus.bluetooth.bean.RealSingleHealthBean r0 = new fit.hplus.bluetooth.bean.RealSingleHealthBean
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r12, r14)
            goto L_0x050d
        L_0x0148:
            int r1 = r14.length
            if (r1 < r8) goto L_0x050d
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            int r6 = r13.mCurrentECGPackIndex
            if (r2 != r6) goto L_0x0154
            return
        L_0x0154:
            r13.mCurrentECGPackIndex = r2
            if (r2 != r5) goto L_0x0164
            int r6 = r1 * 20
            int r9 = r1 + -2
            int r9 = r9 * 4
            int r6 = r6 - r9
            int r6 = r6 - r11
            byte[] r6 = new byte[r6]
            r13.ecgByte = r6
        L_0x0164:
            if (r2 > r1) goto L_0x01a3
            byte[] r6 = r13.ecgByte
            if (r6 == 0) goto L_0x01a3
            if (r2 <= r4) goto L_0x016e
            r6 = 4
            goto L_0x016f
        L_0x016e:
            r6 = 3
        L_0x016f:
            int r9 = r13.mCurrentECGIndex
            int r10 = r14.length
            int r10 = r10 + r9
            int r10 = r10 - r6
            byte[] r11 = r13.ecgByte
            int r12 = r11.length
            if (r10 > r12) goto L_0x0185
            int r10 = r14.length
            int r10 = r10 - r6
            java.lang.System.arraycopy(r14, r6, r11, r9, r10)
            int r9 = r13.mCurrentECGIndex
            int r10 = r14.length
            int r10 = r10 - r6
            int r9 = r9 + r10
            r13.mCurrentECGIndex = r9
        L_0x0185:
            if (r2 != r1) goto L_0x01a3
            fit.hplus.bluetooth.bean.ECGData r1 = new fit.hplus.bluetooth.bean.ECGData
            r1.<init>()
            byte[] r6 = r13.ecgByte
            r1.unPack(r6)
            fit.hplus.bluetooth.bean.BaseBean r6 = new fit.hplus.bluetooth.bean.BaseBean
            r9 = 15
            org.json.JSONObject r1 = r1.toJson()
            r6.<init>(r5, r9, r1)
            r13.mCurrentECGIndex = r0
            r13.mCurrentECGPackIndex = r0
            r13.ecgByte = r3
            r3 = r6
        L_0x01a3:
            r1 = 0
            r6 = 0
        L_0x01a5:
            int r9 = r14.length
            if (r1 >= r9) goto L_0x01b0
            byte r9 = r14[r1]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r9
            int r1 = r1 + 1
            goto L_0x01a5
        L_0x01b0:
            byte[] r14 = new byte[r8]
            r1 = -9
            r14[r0] = r1
            byte r0 = (byte) r2
            r14[r5] = r0
            int r0 = r6 >> 8
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r0 = (byte) r0
            r14[r4] = r0
            r0 = r6 & 255(0xff, float:3.57E-43)
            byte r0 = (byte) r0
            r14[r7] = r0
            fit.hplus.bluetooth.HPlusBleManager r0 = get()
            r0.sendUIDataWrite(r14)
            goto L_0x050d
        L_0x01ce:
            int r1 = r14.length
            if (r1 < r8) goto L_0x050d
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            int r6 = r13.mCurrentHRVPackIndex
            if (r2 != r6) goto L_0x01da
            return
        L_0x01da:
            r13.mCurrentHRVPackIndex = r2
            if (r2 != r5) goto L_0x01ea
            int r6 = r1 * 20
            int r9 = r1 + -2
            int r9 = r9 * 4
            int r6 = r6 - r9
            int r6 = r6 - r11
            byte[] r6 = new byte[r6]
            r13.hrvByte = r6
        L_0x01ea:
            if (r2 > r1) goto L_0x0229
            byte[] r6 = r13.hrvByte
            if (r6 == 0) goto L_0x0229
            if (r2 <= r4) goto L_0x01f4
            r6 = 4
            goto L_0x01f5
        L_0x01f4:
            r6 = 3
        L_0x01f5:
            int r9 = r13.mCurrentHRVIndex
            int r10 = r14.length
            int r10 = r10 + r9
            int r10 = r10 - r6
            byte[] r11 = r13.hrvByte
            int r12 = r11.length
            if (r10 > r12) goto L_0x020b
            int r10 = r14.length
            int r10 = r10 - r6
            java.lang.System.arraycopy(r14, r6, r11, r9, r10)
            int r9 = r13.mCurrentHRVIndex
            int r10 = r14.length
            int r10 = r10 - r6
            int r9 = r9 + r10
            r13.mCurrentHRVIndex = r9
        L_0x020b:
            if (r2 != r1) goto L_0x0229
            fit.hplus.bluetooth.bean.HRVData r1 = new fit.hplus.bluetooth.bean.HRVData
            r1.<init>()
            byte[] r6 = r13.hrvByte
            r1.unPack(r6)
            fit.hplus.bluetooth.bean.BaseBean r6 = new fit.hplus.bluetooth.bean.BaseBean
            r9 = 14
            org.json.JSONObject r1 = r1.toJson()
            r6.<init>(r5, r9, r1)
            r13.mCurrentHRVIndex = r0
            r13.mCurrentHRVPackIndex = r0
            r13.hrvByte = r3
            r3 = r6
        L_0x0229:
            r1 = 0
            r6 = 0
        L_0x022b:
            int r9 = r14.length
            if (r1 >= r9) goto L_0x0236
            byte r9 = r14[r1]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r9
            int r1 = r1 + 1
            goto L_0x022b
        L_0x0236:
            byte[] r14 = new byte[r8]
            r1 = -9
            r14[r0] = r1
            byte r0 = (byte) r2
            r14[r5] = r0
            int r0 = r6 >> 8
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r0 = (byte) r0
            r14[r4] = r0
            r0 = r6 & 255(0xff, float:3.57E-43)
            byte r0 = (byte) r0
            r14[r7] = r0
            fit.hplus.bluetooth.HPlusBleManager r0 = get()
            r0.sendUIDataWrite(r14)
            goto L_0x050d
        L_0x0254:
            int r1 = r14.length
            if (r1 < r8) goto L_0x050d
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            if (r2 != r5) goto L_0x0268
            int r6 = r1 + -2
            int r6 = r6 * 17
            int r6 = r6 + 32
            int r6 = r6 + r4
            byte[] r4 = new byte[r6]
            r13.singleWorkOutByte = r4
        L_0x0268:
            if (r2 > r1) goto L_0x029f
            byte[] r4 = r13.singleWorkOutByte
            if (r4 == 0) goto L_0x029f
            int r6 = r13.mCurrentSingleWorkOutIndex
            int r8 = r14.length
            int r8 = r8 + r6
            int r8 = r8 - r7
            int r9 = r4.length
            if (r8 > r9) goto L_0x0282
            int r8 = r14.length
            int r8 = r8 - r7
            java.lang.System.arraycopy(r14, r7, r4, r6, r8)
            int r4 = r13.mCurrentSingleWorkOutIndex
            int r14 = r14.length
            int r14 = r14 - r7
            int r4 = r4 + r14
            r13.mCurrentSingleWorkOutIndex = r4
        L_0x0282:
            if (r2 != r1) goto L_0x050d
            fit.hplus.bluetooth.bean.SingleWorkoutData r14 = new fit.hplus.bluetooth.bean.SingleWorkoutData
            r14.<init>()
            byte[] r1 = r13.singleWorkOutByte
            r14.unPack(r1)
            fit.hplus.bluetooth.bean.BaseBean r1 = new fit.hplus.bluetooth.bean.BaseBean
            r2 = 11
            org.json.JSONObject r14 = r14.toJson()
            r1.<init>(r5, r2, r14)
            r13.mCurrentSingleWorkOutIndex = r0
            r13.singleWorkOutByte = r3
            goto L_0x00b4
        L_0x029f:
            if (r2 != 0) goto L_0x050d
            if (r1 != 0) goto L_0x050d
            r13.removeSendDataTimeOut()
            r13.updateSendData()
            r13.requestSendDataTimeOut()
            goto L_0x050d
        L_0x02ae:
            int r1 = r14.length
            if (r1 < r8) goto L_0x050d
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            r8 = 10
            if (r2 != r5) goto L_0x02cf
            int r11 = r14.length
            if (r11 < r8) goto L_0x02c6
            byte r9 = r14[r9]
            int r9 = r9 * 2
            int r9 = r9 + r6
            byte[] r4 = new byte[r9]
            r13.sleepChartByte = r4
            goto L_0x02cf
        L_0x02c6:
            int r4 = r1 * 20
            int r6 = r1 * 3
            int r4 = r4 - r6
            byte[] r4 = new byte[r4]
            r13.sleepChartByte = r4
        L_0x02cf:
            if (r1 == 0) goto L_0x030f
            if (r2 == 0) goto L_0x030f
            if (r2 > r1) goto L_0x030f
            int r4 = r13.mCurrentSleepChartIndex
            int r6 = r14.length
            int r6 = r6 + r4
            int r6 = r6 - r7
            byte[] r9 = r13.sleepChartByte
            int r10 = r9.length
            if (r6 > r10) goto L_0x02ec
            int r6 = r14.length
            int r6 = r6 - r7
            java.lang.System.arraycopy(r14, r7, r9, r4, r6)
            int r4 = r13.mCurrentSleepChartIndex
            int r14 = r14.length
            int r14 = r14 - r7
            int r4 = r4 + r14
            r13.mCurrentSleepChartIndex = r4
            goto L_0x02f6
        L_0x02ec:
            int r6 = r9.length
            int r6 = r6 - r4
            java.lang.System.arraycopy(r14, r7, r9, r4, r6)
            int r14 = r13.mCurrentSleepChartIndex
            int r14 = r14 + r6
            r13.mCurrentSleepChartIndex = r14
        L_0x02f6:
            if (r2 != r1) goto L_0x050d
            fit.hplus.bluetooth.bean.SleepChartData r14 = new fit.hplus.bluetooth.bean.SleepChartData
            r14.<init>()
            byte[] r1 = r13.sleepChartByte
            r14.unPack(r1)
            fit.hplus.bluetooth.bean.BaseBean r1 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r14.toJson()
            r1.<init>(r5, r8, r14)
            r13.mCurrentSleepChartIndex = r0
            goto L_0x00b4
        L_0x030f:
            fit.hplus.bluetooth.bean.BaseBean r14 = new fit.hplus.bluetooth.bean.BaseBean
            r14.<init>(r0, r8, r10)
            r13.removeSendDataTimeOut()
            r13.updateSendData()
            r13.requestSendDataTimeOut()
            r3 = r14
            goto L_0x050d
        L_0x0320:
            int r0 = r14.length
            if (r0 < r8) goto L_0x050d
            byte r0 = r14[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r1 = r14[r7]
            r1 = r1 & 255(0xff, float:3.57E-43)
            if (r1 != r5) goto L_0x033b
            fit.hplus.bluetooth.bean.MapData r0 = new fit.hplus.bluetooth.bean.MapData
            r0.<init>()
            r13.mCurrentMapData = r0
            fit.hplus.bluetooth.bean.MapData r0 = r13.mCurrentMapData
            r0.unPacket(r14)
            goto L_0x050d
        L_0x033b:
            if (r1 > r0) goto L_0x050d
            fit.hplus.bluetooth.bean.MapData r2 = r13.mCurrentMapData
            if (r2 == 0) goto L_0x0355
            r2.unPacketLatLng(r14)
            if (r1 != r0) goto L_0x0355
            fit.hplus.bluetooth.bean.BaseBean r14 = new fit.hplus.bluetooth.bean.BaseBean
            r2 = 7
            fit.hplus.bluetooth.bean.MapData r4 = r13.mCurrentMapData
            org.json.JSONObject r4 = r4.toJson()
            r14.<init>(r5, r2, r4)
            r13.mCurrentMapData = r3
            r3 = r14
        L_0x0355:
            if (r1 != 0) goto L_0x050d
            if (r0 != 0) goto L_0x050d
            r13.removeSendDataTimeOut()
            r13.updateSendData()
            r13.requestSendDataTimeOut()
            goto L_0x050d
        L_0x0364:
            java.util.List r14 = fit.hplus.bluetooth.bean.TenMinuteData.unPackList(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONArray r14 = fit.hplus.bluetooth.bean.TenMinuteData.toJsonList(r14)
            r3.<init>(r5, r11, r14)
            goto L_0x050d
        L_0x0373:
            fit.hplus.bluetooth.bean.TenMinuteData r0 = new fit.hplus.bluetooth.bean.TenMinuteData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            r14 = 5
            org.json.JSONObject r1 = r0.toJson()
            r3.<init>(r5, r14, r1)
            int r14 = r0.getSequence()
            if (r14 == 0) goto L_0x0391
            int r14 = r0.getSequence()
            if (r14 != r5) goto L_0x050d
        L_0x0391:
            r13.removeSendDataTimeOut()
            goto L_0x050d
        L_0x0396:
            android.os.Handler r14 = r13.mHandler
            r14.sendEmptyMessage(r5)
            goto L_0x050d
        L_0x039d:
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            r14 = 16
            r3.<init>(r5, r14, r10)
            goto L_0x050d
        L_0x03a6:
            int r1 = r14.length
            if (r1 < r8) goto L_0x050d
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            if (r2 != r5) goto L_0x03bb
            r4 = 872(0x368, float:1.222E-42)
            byte[] r4 = new byte[r4]
            r13.allDayTenMinuteByte = r4
            r13.removeSendDataTimeOut()
            r13.requestSendDataTimeOut()
        L_0x03bb:
            if (r2 > r1) goto L_0x03f5
            byte[] r4 = r13.allDayTenMinuteByte
            if (r4 == 0) goto L_0x03f5
            int r6 = r13.mCurrentAllDatTenMinuteIndex
            int r8 = r14.length
            int r8 = r8 + r6
            int r8 = r8 - r7
            int r9 = r4.length
            if (r8 > r9) goto L_0x03d5
            int r8 = r14.length
            int r8 = r8 - r7
            java.lang.System.arraycopy(r14, r7, r4, r6, r8)
            int r4 = r13.mCurrentAllDatTenMinuteIndex
            int r14 = r14.length
            int r14 = r14 - r7
            int r4 = r4 + r14
            r13.mCurrentAllDatTenMinuteIndex = r4
        L_0x03d5:
            if (r2 != r1) goto L_0x050d
            fit.hplus.bluetooth.bean.AllDayTenMinute r14 = new fit.hplus.bluetooth.bean.AllDayTenMinute
            r14.<init>()
            byte[] r1 = r13.allDayTenMinuteByte
            r14.unPack(r1)
            fit.hplus.bluetooth.bean.BaseBean r1 = new fit.hplus.bluetooth.bean.BaseBean
            r2 = 13
            org.json.JSONObject r14 = r14.toJson()
            r1.<init>(r5, r2, r14)
            r13.mCurrentAllDatTenMinuteIndex = r0
            r13.allDayTenMinuteByte = r3
            r13.requestSendDataTimeOut()
            goto L_0x00b4
        L_0x03f5:
            if (r2 != 0) goto L_0x050d
            if (r1 != 0) goto L_0x050d
            r13.removeSendDataTimeOut()
            r13.updateSendData()
            r13.requestSendDataTimeOut()
            goto L_0x050d
        L_0x0404:
            int r1 = r14.length
            if (r1 < r8) goto L_0x050d
            byte r1 = r14[r4]
            byte r2 = r14[r7]
            r4 = 128(0x80, float:1.794E-43)
            if (r2 != r5) goto L_0x0413
            byte[] r6 = new byte[r4]
            r13.singleCircleByte = r6
        L_0x0413:
            if (r1 == 0) goto L_0x0449
            if (r2 == 0) goto L_0x0449
            if (r2 > r1) goto L_0x0449
            int r6 = r13.mCurrentSingleIndex
            int r7 = r14.length
            int r7 = r7 + r6
            int r7 = r7 - r8
            if (r7 > r4) goto L_0x042e
            byte[] r4 = r13.singleCircleByte
            int r7 = r14.length
            int r7 = r7 - r8
            java.lang.System.arraycopy(r14, r8, r4, r6, r7)
            int r4 = r13.mCurrentSingleIndex
            int r14 = r14.length
            int r14 = r14 - r8
            int r4 = r4 + r14
            r13.mCurrentSingleIndex = r4
        L_0x042e:
            if (r2 != r1) goto L_0x050d
            fit.hplus.bluetooth.bean.SingleCircleData r14 = new fit.hplus.bluetooth.bean.SingleCircleData
            r14.<init>()
            byte[] r1 = r13.singleCircleByte
            r14.unPack(r1)
            fit.hplus.bluetooth.bean.BaseBean r1 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r14.toJson()
            r1.<init>(r5, r9, r14)
            r13.mCurrentSingleIndex = r0
            r13.singleCircleByte = r3
            goto L_0x00b4
        L_0x0449:
            if (r2 != 0) goto L_0x050d
            if (r1 != 0) goto L_0x050d
            r13.removeSendDataTimeOut()
            r13.updateSendData()
            r13.requestSendDataTimeOut()
            goto L_0x050d
        L_0x0458:
            fit.hplus.bluetooth.bean.ForgingData r0 = new fit.hplus.bluetooth.bean.ForgingData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r8, r14)
            int r14 = r0.getYear()
            if (r14 != 0) goto L_0x050d
            int r14 = r0.getMonth()
            if (r14 != 0) goto L_0x050d
            int r14 = r0.getDay()
            if (r14 != 0) goto L_0x050d
            r13.removeSendDataTimeOut()
            r13.updateSendData()
            r13.requestSendDataTimeOut()
            goto L_0x050d
        L_0x0486:
            android.content.Context r14 = mContext
            fit.hplus.bluetooth.util.PhoneUtils.myendcall(r14)
            goto L_0x050d
        L_0x048d:
            fit.hplus.bluetooth.bean.SportRecordData r0 = new fit.hplus.bluetooth.bean.SportRecordData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r7, r14)
            int r14 = r0.getYear()
            if (r14 != 0) goto L_0x050d
            int r14 = r0.getMonth()
            if (r14 != 0) goto L_0x050d
            int r14 = r0.getDay()
            if (r14 != 0) goto L_0x050d
            r13.removeSendDataTimeOut()
            r13.updateSendData()
            r13.requestSendDataTimeOut()
            goto L_0x050d
        L_0x04ba:
            fit.hplus.bluetooth.bean.RealSportData r0 = new fit.hplus.bluetooth.bean.RealSportData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r5, r14)
            goto L_0x050d
        L_0x04cc:
            fit.hplus.bluetooth.bean.DeviceInfoData r0 = new fit.hplus.bluetooth.bean.DeviceInfoData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r6, r14)
            goto L_0x050d
        L_0x04de:
            r13.requestSendDataTimeOut()
            fit.hplus.bluetooth.bean.SleepTotalData r0 = new fit.hplus.bluetooth.bean.SleepTotalData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r4, r14)
            int r14 = r0.getYear()
            if (r14 != 0) goto L_0x050d
            int r14 = r0.getMonth()
            if (r14 != 0) goto L_0x050d
            int r14 = r0.getDay()
            if (r14 != 0) goto L_0x050d
            r13.removeSendDataTimeOut()
            r13.updateSendData()
            r13.requestSendDataTimeOut()
        L_0x050d:
            if (r3 == 0) goto L_0x0528
            android.os.Handler r14 = r13.mHandler
            if (r14 == 0) goto L_0x0528
            android.os.Message r14 = android.os.Message.obtain()
            r14.what = r5
            org.json.JSONObject r0 = r3.toJson()
            java.lang.String r0 = r0.toString()
            r14.obj = r0
            android.os.Handler r0 = r13.mHandler
            r0.handleMessage(r14)
        L_0x0528:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: fit.hplus.bluetooth.HPlusBleManager.commandResolve(byte[]):void");
    }

    private void updateSendData() {
        this.mHandler.sendEmptyMessage(2);
    }

    private void removeSendDataTimeOut() {
        this.mHandler.sendEmptyMessage(3);
    }

    private void requestSendDataTimeOut() {
        this.mHandler.sendEmptyMessage(4);
    }
}
