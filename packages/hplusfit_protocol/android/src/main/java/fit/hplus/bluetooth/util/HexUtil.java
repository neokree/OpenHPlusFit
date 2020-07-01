package fit.hplus.bluetooth.util;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class HexUtil {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static int getHeight4(byte b) {
        return (b & 240) >> 4;
    }

    public static int getLow4(byte b) {
        return b & 15;
    }

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, true);
    }

    public static char[] encodeHex(byte[] bArr, boolean z) {
        return encodeHex(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] bArr, char[] cArr) {
        int length = bArr.length;
        char[] cArr2 = new char[(length << 1)];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return cArr2;
    }

    public static String encodeHexStr(byte[] bArr) {
        return encodeHexStr(bArr, true);
    }

    public static String encodeHexStr(byte[] bArr, boolean z) {
        return encodeHexStr(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static String encodeHexStr(byte[] bArr, char[] cArr) {
        return new String(encodeHex(bArr, cArr));
    }

    public static byte[] decodeHex(char[] cArr) {
        int length = cArr.length;
        if ((length & 1) == 0) {
            byte[] bArr = new byte[(length >> 1)];
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = i + 1;
                i = i3 + 1;
                bArr[i2] = (byte) (((toDigit(cArr[i], i) << 4) | toDigit(cArr[i3], i3)) & 255);
                i2++;
            }
            return bArr;
        }
        throw new RuntimeException("Odd number of characters.");
    }

    protected static int toDigit(char c, int i) {
        int digit = Character.digit(c, 16);
        if (digit != -1) {
            return digit;
        }
        throw new RuntimeException("Illegal hexadecimal character " + c + " at index " + i);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int CRC8(byte[] r7) {
        /*
            int r0 = r7.length
            r1 = 0
            r2 = 255(0xff, float:3.57E-43)
            r3 = 0
            r4 = 255(0xff, float:3.57E-43)
        L_0x0007:
            if (r3 >= r0) goto L_0x0025
            byte r5 = r7[r3]
            r5 = r5 & r2
            r4 = r4 ^ r5
            r5 = r4
            r4 = 0
        L_0x000f:
            r6 = 8
            if (r4 >= r6) goto L_0x0021
            r6 = r5 & 1
            if (r6 == 0) goto L_0x001c
            int r5 = r5 >> 1
            r5 = r5 ^ 184(0xb8, float:2.58E-43)
            goto L_0x001e
        L_0x001c:
            int r5 = r5 >> 1
        L_0x001e:
            int r4 = r4 + 1
            goto L_0x000f
        L_0x0021:
            int r3 = r3 + 1
            r4 = r5
            goto L_0x0007
        L_0x0025:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r4)
            int r7 = r7.intValue()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: fit.hplus.bluetooth.util.HexUtil.CRC8(byte[]):int");
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String bytesToAscii(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length == 0 || i < 0 || i2 <= 0 || i >= bArr.length || bArr.length - i < i2) {
            return null;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        try {
            return new String(bArr2, "ISO8859-1");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static String getBinaryStrFromByte(byte b) {
        String str;
        String str2 = "";
        for (int i = 0; i < 8; i++) {
            byte b2 = (byte) (((byte) (b >> 1)) << 1);
            if (b2 == b) {
                str = "0" + str2;
            } else {
                str = "1" + str2;
            }
            str2 = str;
            b = (byte) (b2 >> 1);
        }
        return new StringBuffer(str2).reverse().toString();
    }

    public static String byteToBit(byte b) {
        return "" + ((byte) ((b >> 7) & 1)) + ((byte) ((b >> 6) & 1)) + ((byte) ((b >> 5) & 1)) + ((byte) ((b >> 4) & 1)) + ((byte) ((b >> 3) & 1)) + ((byte) ((b >> 2) & 1)) + ((byte) ((b >> 1) & 1)) + ((byte) ((b >> 0) & 1));
    }

    public static byte getFbyte(String str) {
        char[] charArray = str.toCharArray();
        int i = 0;
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (charArray[i2] == '1') {
                if (i2 == 0) {
                    i += 128;
                }
                if (i2 == 1) {
                    i += 64;
                }
                if (i2 == 2) {
                    i += 32;
                }
                if (i2 == 3) {
                    i += 16;
                }
                if (i2 == 4) {
                    i += 8;
                }
                if (i2 == 5) {
                    i += 4;
                }
                if (i2 == 6) {
                    i += 2;
                }
                if (i2 == 7) {
                    i++;
                }
            }
        }
        return (byte) i;
    }

    public static int getInt(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i + 0] << 24) & -16777216) | ((bArr[i + 1] << 16) & 16711680) | ((bArr[i + 2] << 8) & 65280);
    }

    public static float getFloat(byte[] bArr, int i) {
        return Float.intBitsToFloat(getInt(bArr, i));
    }

    public static String setformat(int i, String str) {
        Locale locale = Locale.ENGLISH;
        return String.format(locale, "%." + i + "f", new Object[]{tofloat(str)});
    }

    public static String setformat(int i, float f) {
        Locale locale = Locale.ENGLISH;
        return String.format(locale, "%." + i + "f", new Object[]{Float.valueOf(f)});
    }

    public static Float tofloat(String str) {
        Float valueOf = Float.valueOf(0.0f);
        try {
            return Float.valueOf(Float.parseFloat(str));
        } catch (Exception e) {
            e.getMessage();
            return valueOf;
        }
    }
}
