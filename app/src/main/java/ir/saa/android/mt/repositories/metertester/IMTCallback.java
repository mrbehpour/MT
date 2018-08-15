package ir.saa.android.mt.repositories.metertester;

public interface IMTCallback {
    void onConnected();
    void onDisConnected();
    void onConnectionError(String errMsg);
    void onReportStatus(String statusMsg);
}
