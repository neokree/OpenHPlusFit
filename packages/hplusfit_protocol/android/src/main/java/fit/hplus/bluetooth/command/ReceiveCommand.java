package fit.hplus.bluetooth.command;

import android.content.Context;
import android.os.Handler;
import fit.hplus.bluetooth.bean.MapData;
import fit.hplus.bluetooth.bean.RealSingleHealthBean;
import fit.hplus.bluetooth.util.LogUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReceiveCommand extends Thread {
    private static final String TAG = ReceiveCommand.class.getName();
    private byte[] allDayTenMinuteByte;
    private byte[] ecgByte;
    private byte[] hrvByte;
    private Context mContext;
    private int mCurrentAllDatTenMinuteIndex;
    private int mCurrentECGIndex;
    private int mCurrentECGPackIndex;
    private int mCurrentHRVIndex;
    private int mCurrentHRVPackIndex;
    private MapData mCurrentMapData;
    private int mCurrentSingleIndex;
    private int mCurrentSingleWorkOutIndex;
    private int mCurrentSleepChartIndex;
    private Handler mHandler;
    private List<RealSingleHealthBean> mHealthDataList;
    private Condition mInnerCondition;
    private Lock mInnerLock;
    private boolean mIsRun = true;
    private final Queue<byte[]> nReceiveQueue;
    private byte[] singleCircleByte;
    private byte[] singleWorkOutByte;
    private byte[] sleepChartByte;

    public ReceiveCommand(Context context, Handler handler) {
        this.mContext = context;
        this.mInnerLock = new ReentrantLock();
        this.mInnerCondition = this.mInnerLock.newCondition();
        this.nReceiveQueue = new LinkedList();
        this.mHandler = handler;
        this.mHealthDataList = new ArrayList();
    }

    public boolean addDataBuffer(byte[] bArr) {
        if (bArr == null) {
            LogUtil.d("ReceiveCommand", "buffer is null");
            return false;
        } else if (bArr.length <= 0) {
            LogUtil.d("ReceiveCommand", "buffer length is null");
            return false;
        } else {
            synchronized (this.nReceiveQueue) {
                this.nReceiveQueue.offer(bArr);
            }
            return true;
        }
    }

    public void run() {
        while (this.mIsRun) {
            byte[] messageFromBuffer = getMessageFromBuffer();
            if (messageFromBuffer != null && messageFromBuffer.length > 0) {
                commandResolve(messageFromBuffer);
            }
        }
    }

    private byte[] getMessageFromBuffer() {
        synchronized (this.nReceiveQueue) {
            if (this.nReceiveQueue.isEmpty()) {
                return null;
            }
            byte[] poll = this.nReceiveQueue.poll();
            return poll;
        }
    }

    public void cancel() {
        this.mHandler.removeCallbacksAndMessages((Object) null);
        this.mHandler = null;
        this.mIsRun = false;
        this.nReceiveQueue.clear();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:229:0x04dc  */
    /* JADX WARNING: Removed duplicated region for block: B:234:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void commandResolve(byte[] r14) {
        /*
            r13 = this;
            r0 = 0
            byte r1 = r14[r0]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = 26
            r3 = 0
            r4 = 2
            r5 = 1
            if (r1 == r2) goto L_0x04ad
            r2 = 46
            r6 = 8
            if (r1 == r2) goto L_0x049b
            r2 = 51
            if (r1 == r2) goto L_0x0489
            r2 = 54
            r7 = 3
            if (r1 == r2) goto L_0x045f
            r2 = 77
            r8 = 4
            if (r1 == r2) goto L_0x0434
            r2 = 86
            r9 = 9
            if (r1 == r2) goto L_0x03e3
            r2 = 92
            if (r1 == r2) goto L_0x038e
            r2 = 97
            java.lang.String r10 = ""
            if (r1 == r2) goto L_0x0385
            r2 = 241(0xf1, float:3.38E-43)
            if (r1 == r2) goto L_0x037e
            r2 = 56
            if (r1 == r2) goto L_0x035b
            r2 = 57
            if (r1 == r2) goto L_0x035b
            r2 = 82
            r11 = 6
            if (r1 == r2) goto L_0x034c
            r2 = 83
            if (r1 == r2) goto L_0x030b
            r2 = 89
            if (r1 == r2) goto L_0x029c
            r2 = 90
            r12 = 17
            if (r1 == r2) goto L_0x0245
            r2 = 245(0xf5, float:3.43E-43)
            if (r1 == r2) goto L_0x01bf
            r2 = 246(0xf6, float:3.45E-43)
            if (r1 == r2) goto L_0x0139
            switch(r1) {
                case 99: goto L_0x0126;
                case 100: goto L_0x00e6;
                case 101: goto L_0x00ca;
                case 102: goto L_0x045f;
                case 103: goto L_0x04ad;
                case 104: goto L_0x029c;
                case 105: goto L_0x0434;
                case 106: goto L_0x0245;
                case 107: goto L_0x03e3;
                case 108: goto L_0x030b;
                case 109: goto L_0x00b7;
                case 110: goto L_0x005c;
                default: goto L_0x005a;
            }
        L_0x005a:
            goto L_0x04d6
        L_0x005c:
            fit.hplus.bluetooth.util.HexUtil.encodeHexStr(r14)
            int r1 = r14.length
            if (r1 < r8) goto L_0x04d6
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            if (r2 != r5) goto L_0x0071
            r4 = 629(0x275, float:8.81E-43)
            byte[] r4 = new byte[r4]
            r13.allDayTenMinuteByte = r4
            r13.removeSendDataTimeOut()
        L_0x0071:
            r4 = 37
            if (r2 > r1) goto L_0x00ae
            byte[] r4 = r13.allDayTenMinuteByte
            if (r4 == 0) goto L_0x00ae
            int r6 = r13.mCurrentAllDatTenMinuteIndex
            int r8 = r14.length
            int r8 = r8 + r6
            int r8 = r8 - r7
            int r9 = r4.length
            if (r8 > r9) goto L_0x008d
            int r8 = r14.length
            int r8 = r8 - r7
            java.lang.System.arraycopy(r14, r7, r4, r6, r8)
            int r4 = r13.mCurrentAllDatTenMinuteIndex
            int r14 = r14.length
            int r14 = r14 - r7
            int r4 = r4 + r14
            r13.mCurrentAllDatTenMinuteIndex = r4
        L_0x008d:
            if (r2 != r1) goto L_0x04d6
            fit.hplus.bluetooth.bean.AllDayTenMinuteThermometer r14 = new fit.hplus.bluetooth.bean.AllDayTenMinuteThermometer
            r14.<init>()
            byte[] r1 = r13.allDayTenMinuteByte
            r14.unPack(r1)
            fit.hplus.bluetooth.bean.BaseBean r1 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r14.toJson()
            r2 = 20
            r1.<init>(r5, r2, r14)
            r13.mCurrentAllDatTenMinuteIndex = r0
            r13.allDayTenMinuteByte = r3
            r13.requestSendDataTimeOut()
        L_0x00ab:
            r3 = r1
            goto L_0x04d6
        L_0x00ae:
            if (r2 != 0) goto L_0x04d6
            if (r1 != 0) goto L_0x04d6
            r13.updateSendData()
            goto L_0x04d6
        L_0x00b7:
            fit.hplus.bluetooth.bean.RealSportData r0 = new fit.hplus.bluetooth.bean.RealSportData
            r0.<init>()
            r0.unPackNew(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r5, r14)
            goto L_0x04d6
        L_0x00ca:
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
            goto L_0x04d6
        L_0x00e6:
            fit.hplus.bluetooth.bean.RealSingleHealthBean r0 = new fit.hplus.bluetooth.bean.RealSingleHealthBean
            r0.<init>()
            r0.unPack(r14)
            int r14 = r0.getYear()
            if (r14 != 0) goto L_0x011f
            int r14 = r0.getMonth()
            if (r14 != 0) goto L_0x011f
            int r14 = r0.getDay()
            if (r14 != 0) goto L_0x011f
            java.util.List<fit.hplus.bluetooth.bean.RealSingleHealthBean> r14 = r13.mHealthDataList
            int r14 = r14.size()
            if (r14 <= 0) goto L_0x011a
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            r14 = 18
            java.util.List<fit.hplus.bluetooth.bean.RealSingleHealthBean> r0 = r13.mHealthDataList
            org.json.JSONArray r0 = fit.hplus.bluetooth.bean.RealSingleHealthBean.unPackList(r0)
            r3.<init>(r5, r14, r0)
            java.util.List<fit.hplus.bluetooth.bean.RealSingleHealthBean> r14 = r13.mHealthDataList
            r14.clear()
        L_0x011a:
            r13.updateSendData()
            goto L_0x04d6
        L_0x011f:
            java.util.List<fit.hplus.bluetooth.bean.RealSingleHealthBean> r14 = r13.mHealthDataList
            r14.add(r0)
            goto L_0x04d6
        L_0x0126:
            fit.hplus.bluetooth.bean.RealSingleHealthBean r0 = new fit.hplus.bluetooth.bean.RealSingleHealthBean
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r12, r14)
            goto L_0x04d6
        L_0x0139:
            int r1 = r14.length
            if (r1 < r8) goto L_0x04d6
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            int r6 = r13.mCurrentECGPackIndex
            if (r2 != r6) goto L_0x0145
            return
        L_0x0145:
            r13.mCurrentECGPackIndex = r2
            if (r2 != r5) goto L_0x0155
            int r6 = r1 * 20
            int r9 = r1 + -2
            int r9 = r9 * 4
            int r6 = r6 - r9
            int r6 = r6 - r11
            byte[] r6 = new byte[r6]
            r13.ecgByte = r6
        L_0x0155:
            if (r2 > r1) goto L_0x0194
            byte[] r6 = r13.ecgByte
            if (r6 == 0) goto L_0x0194
            if (r2 <= r4) goto L_0x015f
            r6 = 4
            goto L_0x0160
        L_0x015f:
            r6 = 3
        L_0x0160:
            int r9 = r13.mCurrentECGIndex
            int r10 = r14.length
            int r10 = r10 + r9
            int r10 = r10 - r6
            byte[] r11 = r13.ecgByte
            int r12 = r11.length
            if (r10 > r12) goto L_0x0176
            int r10 = r14.length
            int r10 = r10 - r6
            java.lang.System.arraycopy(r14, r6, r11, r9, r10)
            int r9 = r13.mCurrentECGIndex
            int r10 = r14.length
            int r10 = r10 - r6
            int r9 = r9 + r10
            r13.mCurrentECGIndex = r9
        L_0x0176:
            if (r2 != r1) goto L_0x0194
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
        L_0x0194:
            r1 = 0
            r6 = 0
        L_0x0196:
            int r9 = r14.length
            if (r1 >= r9) goto L_0x01a1
            byte r9 = r14[r1]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r9
            int r1 = r1 + 1
            goto L_0x0196
        L_0x01a1:
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
            fit.hplus.bluetooth.HPlusBleManager r0 = fit.hplus.bluetooth.HPlusBleManager.get()
            r0.sendUIDataWrite(r14)
            goto L_0x04d6
        L_0x01bf:
            int r1 = r14.length
            if (r1 < r8) goto L_0x04d6
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            int r6 = r13.mCurrentHRVPackIndex
            if (r2 != r6) goto L_0x01cb
            return
        L_0x01cb:
            r13.mCurrentHRVPackIndex = r2
            if (r2 != r5) goto L_0x01db
            int r6 = r1 * 20
            int r9 = r1 + -2
            int r9 = r9 * 4
            int r6 = r6 - r9
            int r6 = r6 - r11
            byte[] r6 = new byte[r6]
            r13.hrvByte = r6
        L_0x01db:
            if (r2 > r1) goto L_0x021a
            byte[] r6 = r13.hrvByte
            if (r6 == 0) goto L_0x021a
            if (r2 <= r4) goto L_0x01e5
            r6 = 4
            goto L_0x01e6
        L_0x01e5:
            r6 = 3
        L_0x01e6:
            int r9 = r13.mCurrentHRVIndex
            int r10 = r14.length
            int r10 = r10 + r9
            int r10 = r10 - r6
            byte[] r11 = r13.hrvByte
            int r12 = r11.length
            if (r10 > r12) goto L_0x01fc
            int r10 = r14.length
            int r10 = r10 - r6
            java.lang.System.arraycopy(r14, r6, r11, r9, r10)
            int r9 = r13.mCurrentHRVIndex
            int r10 = r14.length
            int r10 = r10 - r6
            int r9 = r9 + r10
            r13.mCurrentHRVIndex = r9
        L_0x01fc:
            if (r2 != r1) goto L_0x021a
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
        L_0x021a:
            r1 = 0
            r6 = 0
        L_0x021c:
            int r9 = r14.length
            if (r1 >= r9) goto L_0x0227
            byte r9 = r14[r1]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r9
            int r1 = r1 + 1
            goto L_0x021c
        L_0x0227:
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
            fit.hplus.bluetooth.HPlusBleManager r0 = fit.hplus.bluetooth.HPlusBleManager.get()
            r0.sendUIDataWrite(r14)
            goto L_0x04d6
        L_0x0245:
            r13.removeSendDataTimeOut()
            int r1 = r14.length
            if (r1 < r8) goto L_0x04d6
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            if (r2 != r5) goto L_0x025c
            int r6 = r1 + -2
            int r6 = r6 * 17
            int r6 = r6 + 32
            int r6 = r6 + r4
            byte[] r4 = new byte[r6]
            r13.singleWorkOutByte = r4
        L_0x025c:
            if (r2 > r1) goto L_0x0293
            byte[] r4 = r13.singleWorkOutByte
            if (r4 == 0) goto L_0x0293
            int r6 = r13.mCurrentSingleWorkOutIndex
            int r8 = r14.length
            int r8 = r8 + r6
            int r8 = r8 - r7
            int r9 = r4.length
            if (r8 > r9) goto L_0x0276
            int r8 = r14.length
            int r8 = r8 - r7
            java.lang.System.arraycopy(r14, r7, r4, r6, r8)
            int r4 = r13.mCurrentSingleWorkOutIndex
            int r14 = r14.length
            int r14 = r14 - r7
            int r4 = r4 + r14
            r13.mCurrentSingleWorkOutIndex = r4
        L_0x0276:
            if (r2 != r1) goto L_0x04d6
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
            goto L_0x00ab
        L_0x0293:
            if (r2 != 0) goto L_0x04d6
            if (r1 != 0) goto L_0x04d6
            r13.updateSendData()
            goto L_0x04d6
        L_0x029c:
            r13.removeSendDataTimeOut()
            int r1 = r14.length
            if (r1 < r8) goto L_0x04d6
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            r8 = 10
            if (r2 != r5) goto L_0x02c0
            int r11 = r14.length
            if (r11 < r8) goto L_0x02b7
            byte r9 = r14[r9]
            int r9 = r9 * 2
            int r9 = r9 + r6
            byte[] r4 = new byte[r9]
            r13.sleepChartByte = r4
            goto L_0x02c0
        L_0x02b7:
            int r4 = r1 * 20
            int r6 = r1 * 3
            int r4 = r4 - r6
            byte[] r4 = new byte[r4]
            r13.sleepChartByte = r4
        L_0x02c0:
            if (r1 == 0) goto L_0x0300
            if (r2 == 0) goto L_0x0300
            if (r2 > r1) goto L_0x0300
            int r4 = r13.mCurrentSleepChartIndex
            int r6 = r14.length
            int r6 = r6 + r4
            int r6 = r6 - r7
            byte[] r9 = r13.sleepChartByte
            int r10 = r9.length
            if (r6 > r10) goto L_0x02dd
            int r6 = r14.length
            int r6 = r6 - r7
            java.lang.System.arraycopy(r14, r7, r9, r4, r6)
            int r4 = r13.mCurrentSleepChartIndex
            int r14 = r14.length
            int r14 = r14 - r7
            int r4 = r4 + r14
            r13.mCurrentSleepChartIndex = r4
            goto L_0x02e7
        L_0x02dd:
            int r6 = r9.length
            int r6 = r6 - r4
            java.lang.System.arraycopy(r14, r7, r9, r4, r6)
            int r14 = r13.mCurrentSleepChartIndex
            int r14 = r14 + r6
            r13.mCurrentSleepChartIndex = r14
        L_0x02e7:
            if (r2 != r1) goto L_0x04d6
            fit.hplus.bluetooth.bean.SleepChartData r14 = new fit.hplus.bluetooth.bean.SleepChartData
            r14.<init>()
            byte[] r1 = r13.sleepChartByte
            r14.unPack(r1)
            fit.hplus.bluetooth.bean.BaseBean r1 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r14.toJson()
            r1.<init>(r5, r8, r14)
            r13.mCurrentSleepChartIndex = r0
            goto L_0x00ab
        L_0x0300:
            fit.hplus.bluetooth.bean.BaseBean r14 = new fit.hplus.bluetooth.bean.BaseBean
            r14.<init>(r0, r8, r10)
            r13.updateSendData()
            r3 = r14
            goto L_0x04d6
        L_0x030b:
            r13.removeSendDataTimeOut()
            int r0 = r14.length
            if (r0 < r8) goto L_0x04d6
            byte r0 = r14[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r1 = r14[r7]
            r1 = r1 & 255(0xff, float:3.57E-43)
            if (r1 != r5) goto L_0x0329
            fit.hplus.bluetooth.bean.MapData r0 = new fit.hplus.bluetooth.bean.MapData
            r0.<init>()
            r13.mCurrentMapData = r0
            fit.hplus.bluetooth.bean.MapData r0 = r13.mCurrentMapData
            r0.unPacket(r14)
            goto L_0x04d6
        L_0x0329:
            if (r1 > r0) goto L_0x04d6
            fit.hplus.bluetooth.bean.MapData r2 = r13.mCurrentMapData
            if (r2 == 0) goto L_0x0343
            r2.unPacketLatLng(r14)
            if (r1 != r0) goto L_0x0343
            fit.hplus.bluetooth.bean.BaseBean r14 = new fit.hplus.bluetooth.bean.BaseBean
            r2 = 7
            fit.hplus.bluetooth.bean.MapData r4 = r13.mCurrentMapData
            org.json.JSONObject r4 = r4.toJson()
            r14.<init>(r5, r2, r4)
            r13.mCurrentMapData = r3
            r3 = r14
        L_0x0343:
            if (r1 != 0) goto L_0x04d6
            if (r0 != 0) goto L_0x04d6
            r13.updateSendData()
            goto L_0x04d6
        L_0x034c:
            java.util.List r14 = fit.hplus.bluetooth.bean.TenMinuteData.unPackList(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONArray r14 = fit.hplus.bluetooth.bean.TenMinuteData.toJsonList(r14)
            r3.<init>(r5, r11, r14)
            goto L_0x04d6
        L_0x035b:
            fit.hplus.bluetooth.bean.TenMinuteData r0 = new fit.hplus.bluetooth.bean.TenMinuteData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            r14 = 5
            org.json.JSONObject r1 = r0.toJson()
            r3.<init>(r5, r14, r1)
            int r14 = r0.getSequence()
            if (r14 == 0) goto L_0x0379
            int r14 = r0.getSequence()
            if (r14 != r5) goto L_0x04d6
        L_0x0379:
            r13.removeSendDataTimeOut()
            goto L_0x04d6
        L_0x037e:
            android.os.Handler r14 = r13.mHandler
            r14.sendEmptyMessage(r5)
            goto L_0x04d6
        L_0x0385:
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            r14 = 16
            r3.<init>(r5, r14, r10)
            goto L_0x04d6
        L_0x038e:
            int r1 = r14.length
            if (r1 < r8) goto L_0x04d6
            byte r1 = r14[r5]
            byte r2 = r14[r4]
            if (r2 != r5) goto L_0x03a0
            r4 = 872(0x368, float:1.222E-42)
            byte[] r4 = new byte[r4]
            r13.allDayTenMinuteByte = r4
            r13.removeSendDataTimeOut()
        L_0x03a0:
            if (r2 > r1) goto L_0x03da
            byte[] r4 = r13.allDayTenMinuteByte
            if (r4 == 0) goto L_0x03da
            int r6 = r13.mCurrentAllDatTenMinuteIndex
            int r8 = r14.length
            int r8 = r8 + r6
            int r8 = r8 - r7
            int r9 = r4.length
            if (r8 > r9) goto L_0x03ba
            int r8 = r14.length
            int r8 = r8 - r7
            java.lang.System.arraycopy(r14, r7, r4, r6, r8)
            int r4 = r13.mCurrentAllDatTenMinuteIndex
            int r14 = r14.length
            int r14 = r14 - r7
            int r4 = r4 + r14
            r13.mCurrentAllDatTenMinuteIndex = r4
        L_0x03ba:
            if (r2 != r1) goto L_0x04d6
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
            goto L_0x00ab
        L_0x03da:
            if (r2 != 0) goto L_0x04d6
            if (r1 != 0) goto L_0x04d6
            r13.updateSendData()
            goto L_0x04d6
        L_0x03e3:
            r13.removeSendDataTimeOut()
            int r1 = r14.length
            if (r1 < r8) goto L_0x04d6
            byte r1 = r14[r4]
            byte r2 = r14[r7]
            r4 = 128(0x80, float:1.794E-43)
            if (r2 != r5) goto L_0x03f5
            byte[] r6 = new byte[r4]
            r13.singleCircleByte = r6
        L_0x03f5:
            if (r1 == 0) goto L_0x042b
            if (r2 == 0) goto L_0x042b
            if (r2 > r1) goto L_0x042b
            int r6 = r13.mCurrentSingleIndex
            int r7 = r14.length
            int r7 = r7 + r6
            int r7 = r7 - r8
            if (r7 > r4) goto L_0x0410
            byte[] r4 = r13.singleCircleByte
            int r7 = r14.length
            int r7 = r7 - r8
            java.lang.System.arraycopy(r14, r8, r4, r6, r7)
            int r4 = r13.mCurrentSingleIndex
            int r14 = r14.length
            int r14 = r14 - r8
            int r4 = r4 + r14
            r13.mCurrentSingleIndex = r4
        L_0x0410:
            if (r2 != r1) goto L_0x04d6
            fit.hplus.bluetooth.bean.SingleCircleData r14 = new fit.hplus.bluetooth.bean.SingleCircleData
            r14.<init>()
            byte[] r1 = r13.singleCircleByte
            r14.unPack(r1)
            fit.hplus.bluetooth.bean.BaseBean r1 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r14.toJson()
            r1.<init>(r5, r9, r14)
            r13.mCurrentSingleIndex = r0
            r13.singleCircleByte = r3
            goto L_0x00ab
        L_0x042b:
            if (r2 != 0) goto L_0x04d6
            if (r1 != 0) goto L_0x04d6
            r13.updateSendData()
            goto L_0x04d6
        L_0x0434:
            r13.removeSendDataTimeOut()
            fit.hplus.bluetooth.bean.ForgingData r0 = new fit.hplus.bluetooth.bean.ForgingData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r8, r14)
            int r14 = r0.getYear()
            if (r14 != 0) goto L_0x04d6
            int r14 = r0.getMonth()
            if (r14 != 0) goto L_0x04d6
            int r14 = r0.getDay()
            if (r14 != 0) goto L_0x04d6
            r13.updateSendData()
            goto L_0x04d6
        L_0x045f:
            r13.removeSendDataTimeOut()
            fit.hplus.bluetooth.bean.SportRecordData r0 = new fit.hplus.bluetooth.bean.SportRecordData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r7, r14)
            int r14 = r0.getYear()
            if (r14 != 0) goto L_0x04d6
            int r14 = r0.getMonth()
            if (r14 != 0) goto L_0x04d6
            int r14 = r0.getDay()
            if (r14 != 0) goto L_0x04d6
            r13.updateSendData()
            goto L_0x04d6
        L_0x0489:
            fit.hplus.bluetooth.bean.RealSportData r0 = new fit.hplus.bluetooth.bean.RealSportData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r5, r14)
            goto L_0x04d6
        L_0x049b:
            fit.hplus.bluetooth.bean.DeviceInfoData r0 = new fit.hplus.bluetooth.bean.DeviceInfoData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r6, r14)
            goto L_0x04d6
        L_0x04ad:
            r13.removeSendDataTimeOut()
            fit.hplus.bluetooth.bean.SleepTotalData r0 = new fit.hplus.bluetooth.bean.SleepTotalData
            r0.<init>()
            r0.unPack(r14)
            fit.hplus.bluetooth.bean.BaseBean r3 = new fit.hplus.bluetooth.bean.BaseBean
            org.json.JSONObject r14 = r0.toJson()
            r3.<init>(r5, r4, r14)
            int r14 = r0.getYear()
            if (r14 != 0) goto L_0x04d6
            int r14 = r0.getMonth()
            if (r14 != 0) goto L_0x04d6
            int r14 = r0.getDay()
            if (r14 != 0) goto L_0x04d6
            r13.updateSendData()
        L_0x04d6:
            if (r3 == 0) goto L_0x04f1
            android.os.Handler r14 = r13.mHandler
            if (r14 == 0) goto L_0x04f1
            android.os.Message r14 = android.os.Message.obtain()
            r14.what = r5
            org.json.JSONObject r0 = r3.toJson()
            java.lang.String r0 = r0.toString()
            r14.obj = r0
            android.os.Handler r0 = r13.mHandler
            r0.handleMessage(r14)
        L_0x04f1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: fit.hplus.bluetooth.command.ReceiveCommand.commandResolve(byte[]):void");
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
