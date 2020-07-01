package fit.hplus.bluetooth.dial;

public interface DialFileCallback {
    void getFailDialFile(String str);

    void notFileWritePermission();

    void notInternetPermission();

    void pushDialFail();

    void pushDialLoading();

    void pushDialProgress(int i);

    void pushDialSuccess();
}
