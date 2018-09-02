package ir.saa.android.mt.repositories.modbus;

public interface ITransferLayer {
    void writeByteArrayToDevice(byte[] writeData);
    void writeStringToDevice(String writeData, Boolean ConevertHex);
    String getDeviceResponse();
    void init(String bluetotthDeviceName);
    void setTransferLayerCallback(ITransferLayerCallback iTransferLayerCallback);
    boolean isConnected();
}
