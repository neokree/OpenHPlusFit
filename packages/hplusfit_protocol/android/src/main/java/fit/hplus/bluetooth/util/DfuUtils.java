package fit.hplus.bluetooth.util;

import android.util.Base64;
import android.util.Xml;
import fit.hplus.bluetooth.bean.DeviceVersionBean;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.xmlpull.v1.XmlPullParser;

public class DfuUtils {
    private static final String TAG = DfuUtils.class.getName();

    public static List<DeviceVersionBean> pullxml(InputStream inputStream) throws Exception {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, "utf-8");
        ArrayList arrayList = null;
        DeviceVersionBean deviceVersionBean = null;
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 0) {
                arrayList = new ArrayList();
            } else if (eventType == 2) {
                String name = newPullParser.getName();
                if (name.equals("DirectoryListing")) {
                    deviceVersionBean = new DeviceVersionBean();
                }
                if (deviceVersionBean != null) {
                    if (name.equals("model")) {
                        deviceVersionBean.setModel(newPullParser.nextText());
                    }
                    if (name.equals("subcode")) {
                        deviceVersionBean.setSubcode(newPullParser.next());
                    }
                    if (name.equals("ver")) {
                        deviceVersionBean.setVer(newPullParser.next());
                    }
                    if (name.equals("object")) {
                        deviceVersionBean.setObject(newPullParser.next());
                    }
                    if (name.equals("md5")) {
                        deviceVersionBean.setMd5(newPullParser.nextText());
                    }
                }
            } else if (eventType == 3 && newPullParser.getName().equals("DirectoryListing")) {
                arrayList.add(deviceVersionBean);
                deviceVersionBean = null;
            }
        }
        return arrayList;
    }

    public static byte[] decrypt(String str, byte[] bArr) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        SecretKey generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str.getBytes()));
        Cipher instance = Cipher.getInstance("DES");
        instance.init(2, generateSecret, secureRandom);
        return instance.doFinal(Base64.decode(bArr, 0));
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0044 A[SYNTHETIC, Splitter:B:31:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x004e A[SYNTHETIC, Splitter:B:36:0x004e] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x005a A[SYNTHETIC, Splitter:B:42:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0064 A[SYNTHETIC, Splitter:B:47:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void createFileWithByte(byte[] r2, java.lang.String r3) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r3)
            r3 = 0
            boolean r1 = r0.exists()     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            if (r1 == 0) goto L_0x000f
            r0.delete()     // Catch:{ Exception -> 0x003d, all -> 0x003a }
        L_0x000f:
            r0.createNewFile()     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            r1.<init>(r0)     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0036, all -> 0x0032 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0036, all -> 0x0032 }
            r0.write(r2)     // Catch:{ Exception -> 0x0030, all -> 0x002e }
            r0.flush()     // Catch:{ Exception -> 0x0030, all -> 0x002e }
            r1.close()     // Catch:{ IOException -> 0x0026 }
            goto L_0x002a
        L_0x0026:
            r2 = move-exception
            r2.printStackTrace()
        L_0x002a:
            r0.close()     // Catch:{ Exception -> 0x0052 }
            goto L_0x0056
        L_0x002e:
            r2 = move-exception
            goto L_0x0034
        L_0x0030:
            r2 = move-exception
            goto L_0x0038
        L_0x0032:
            r2 = move-exception
            r0 = r3
        L_0x0034:
            r3 = r1
            goto L_0x0058
        L_0x0036:
            r2 = move-exception
            r0 = r3
        L_0x0038:
            r3 = r1
            goto L_0x003f
        L_0x003a:
            r2 = move-exception
            r0 = r3
            goto L_0x0058
        L_0x003d:
            r2 = move-exception
            r0 = r3
        L_0x003f:
            r2.printStackTrace()     // Catch:{ all -> 0x0057 }
            if (r3 == 0) goto L_0x004c
            r3.close()     // Catch:{ IOException -> 0x0048 }
            goto L_0x004c
        L_0x0048:
            r2 = move-exception
            r2.printStackTrace()
        L_0x004c:
            if (r0 == 0) goto L_0x0056
            r0.close()     // Catch:{ Exception -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0056:
            return
        L_0x0057:
            r2 = move-exception
        L_0x0058:
            if (r3 == 0) goto L_0x0062
            r3.close()     // Catch:{ IOException -> 0x005e }
            goto L_0x0062
        L_0x005e:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0062:
            if (r0 == 0) goto L_0x006c
            r0.close()     // Catch:{ Exception -> 0x0068 }
            goto L_0x006c
        L_0x0068:
            r3 = move-exception
            r3.printStackTrace()
        L_0x006c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: fit.hplus.bluetooth.util.DfuUtils.createFileWithByte(byte[], java.lang.String):void");
    }

    public static String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(str), instance);
            byte[] bArr = new byte[8986];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (digestInputStream.read(bArr) != -1) {
                byteArrayOutputStream.write(bArr);
            }
            byteArrayOutputStream.toString();
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
