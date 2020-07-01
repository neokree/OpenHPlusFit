package fit.hplus.bluetooth.dfu;

public interface IDfuController {
    void getOTAFile(int i, int i2, DfuFileStatusCallback dfuFileStatusCallback);

    void startFirmwareUpgrade(String str);
}
