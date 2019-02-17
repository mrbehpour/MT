package ir.saa.android.mt.repositories.IEC;

public interface IIECCallback {
    void onConnected();
    void onDisConnected();
    void onConnectionError(String errMsg);
    void onReportStatus(String statusMsg);
}
