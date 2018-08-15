package ir.saa.android.mt.repositories.modbus;

public interface IModbusCallback {
    void onConnected();
    void onDisConnected();
    void onConnectionError(String errMsg);
    void onReportStatus(String statusMsg);
}
