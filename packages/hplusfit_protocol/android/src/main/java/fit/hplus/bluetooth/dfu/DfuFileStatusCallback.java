package fit.hplus.bluetooth.dfu;

public interface DfuFileStatusCallback {
    void notFileWritePermission();

    void notInternetPermission();

    void onFailOTAFile(String str);

    void onSuccessOTAFile(int i, String str);
}
