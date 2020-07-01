package fit.hplus.bluetooth.util;

public class FullMutualToHalf {
    public static String fullToHalf(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 12288) {
                charArray[i] = ' ';
            } else if (charArray[i] > 65280 && charArray[i] < 65375) {
                charArray[i] = (char) (charArray[i] - 65248);
            }
        }
        return new String(charArray);
    }

    public static String halfToFull(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == ' ') {
                charArray[i] = 12288;
            } else if (charArray[i] > ' ' && charArray[i] < 127) {
                charArray[i] = (char) (charArray[i] + 65248);
            }
        }
        return new String(charArray);
    }
}
