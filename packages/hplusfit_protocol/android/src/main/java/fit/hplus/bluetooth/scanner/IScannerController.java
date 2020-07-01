package fit.hplus.bluetooth.scanner;

public interface IScannerController {
    void addScanListener(ScannerListener scannerListener);

    void removeScanListener(ScannerListener scannerListener);

    void startScan();

    void stopScan();
}
