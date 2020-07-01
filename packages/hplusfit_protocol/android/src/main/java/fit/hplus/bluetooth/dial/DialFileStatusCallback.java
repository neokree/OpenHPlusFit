package fit.hplus.bluetooth.dial;

public interface DialFileStatusCallback {
    void notFileWritePermission();

    void notInternetPermission();

    void onFailDialFile(String str);

    void onSuccessDialFile(String str);
}
