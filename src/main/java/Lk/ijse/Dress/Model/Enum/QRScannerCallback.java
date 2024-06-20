package Lk.ijse.Dress.Model.Enum;

public interface QRScannerCallback {
    void onSuccess(String result);
    void onFailure(String message);
}
