package ir.saa.android.mt.repositories.metertester;

public interface IMTResponse {
    void onGetData(String responseStr);
    void onGetError(String errStr);
}
