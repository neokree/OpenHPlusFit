package fit.hplus.bluetooth.command;

import android.util.Log;
import fit.hplus.bluetooth.HPlusBleManager;
import fit.hplus.bluetooth.util.LogUtil;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SendCommand extends Thread {
    private boolean isDownTimer = false;
    private HPlusBleManager mHPlusBleManager;
    private Condition mInnerCondition = this.mInnerLock.newCondition();
    private Lock mInnerLock = new ReentrantLock();
    private boolean mIsRun = true;
    private final Queue<SendDataCommand> nSendQueue = new LinkedList();

    public SendCommand(HPlusBleManager hPlusBleManager) {
        this.mHPlusBleManager = hPlusBleManager;
        Log.d("SendCommand", "Send is run");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0036, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void addCommand(byte[] r4, int r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            int r0 = r4.length     // Catch:{ all -> 0x003a }
            if (r0 > 0) goto L_0x000d
            java.lang.String r4 = "SendCommand"
            java.lang.String r5 = "buffer is null"
            android.util.Log.d(r4, r5)     // Catch:{ all -> 0x003a }
            monitor-exit(r3)
            return
        L_0x000d:
            java.util.Queue<fit.hplus.bluetooth.command.SendCommand$SendDataCommand> r0 = r3.nSendQueue     // Catch:{ all -> 0x003a }
            monitor-enter(r0)     // Catch:{ all -> 0x003a }
            fit.hplus.bluetooth.command.SendCommand$SendDataCommand r1 = new fit.hplus.bluetooth.command.SendCommand$SendDataCommand     // Catch:{ all -> 0x0037 }
            r2 = 0
            r1.<init>()     // Catch:{ all -> 0x0037 }
            byte[] unused = r1.bytes = r4     // Catch:{ all -> 0x0037 }
            int unused = r1.type = r5     // Catch:{ all -> 0x0037 }
            java.util.Queue<fit.hplus.bluetooth.command.SendCommand$SendDataCommand> r4 = r3.nSendQueue     // Catch:{ all -> 0x0037 }
            r4.add(r1)     // Catch:{ all -> 0x0037 }
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            boolean r4 = r3.isDownTimer     // Catch:{ all -> 0x003a }
            if (r4 != 0) goto L_0x0035
            java.util.concurrent.locks.Lock r4 = r3.mInnerLock     // Catch:{ all -> 0x003a }
            r4.lock()     // Catch:{ all -> 0x003a }
            java.util.concurrent.locks.Condition r4 = r3.mInnerCondition     // Catch:{ all -> 0x003a }
            r4.signalAll()     // Catch:{ all -> 0x003a }
            java.util.concurrent.locks.Lock r4 = r3.mInnerLock     // Catch:{ all -> 0x003a }
            r4.unlock()     // Catch:{ all -> 0x003a }
        L_0x0035:
            monitor-exit(r3)
            return
        L_0x0037:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            throw r4     // Catch:{ all -> 0x003a }
        L_0x003a:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: fit.hplus.bluetooth.command.SendCommand.addCommand(byte[], int):void");
    }

    public void run() {
        while (this.mIsRun) {
            SendDataCommand dataBuffer = getDataBuffer();
            if (!(dataBuffer == null || dataBuffer.bytes == null || this.isDownTimer)) {
                byte[] access$100 = dataBuffer.bytes;
                if (dataBuffer.type == 1) {
                    this.mHPlusBleManager.sendUIDataWrite(access$100);
                } else {
                    this.mHPlusBleManager.sendDataCommand(access$100);
                }
                this.isDownTimer = true;
            }
            this.mInnerLock.lock();
            try {
                LogUtil.d("SendCommand", "waiting the sendCommand.");
                this.mInnerCondition.await();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("SendCommand", "await() is fails.");
            } catch (Throwable th) {
                this.mInnerLock.unlock();
                throw th;
            }
            this.mInnerLock.unlock();
        }
    }

    public void cancel() {
        Lock lock = this.mInnerLock;
        if (!(lock == null || this.mInnerCondition == null)) {
            lock.lock();
            this.mInnerCondition.signalAll();
            this.mInnerLock.unlock();
        }
        this.mIsRun = false;
        this.nSendQueue.clear();
        this.isDownTimer = false;
        LogUtil.d("SendCommand", "Send is stop");
    }

    public SendDataCommand getDataBuffer() {
        synchronized (this.nSendQueue) {
            if (this.nSendQueue.peek() == null) {
                return null;
            }
            SendDataCommand peek = this.nSendQueue.peek();
            return peek;
        }
    }

    public void reCancel() {
        if (this.nSendQueue.peek() != null) {
            this.nSendQueue.poll();
        }
        LogUtil.d("SendCommand", "timer is cancel = " + this.nSendQueue.size());
        this.isDownTimer = false;
        Lock lock = this.mInnerLock;
        if (lock != null && this.mInnerCondition != null) {
            lock.lock();
            this.mInnerCondition.signalAll();
            this.mInnerLock.unlock();
        }
    }

    private class SendDataCommand {
        /* access modifiers changed from: private */
        public byte[] bytes;
        /* access modifiers changed from: private */
        public int type;

        private SendDataCommand() {
        }
    }
}
