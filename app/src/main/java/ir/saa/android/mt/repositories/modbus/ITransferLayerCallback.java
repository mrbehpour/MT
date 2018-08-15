package ir.saa.android.mt.repositories.modbus;

public interface ITransferLayerCallback {
    void onConnected();
    void onDisConnected();
    void onConnectionError(String errMsg);
    void onReportStatus(String statusMsg);
    void onRecieveData(byte[] responseArray);
}
