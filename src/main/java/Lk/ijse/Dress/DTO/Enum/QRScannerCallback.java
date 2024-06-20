package Lk.ijse.Dress.DTO.Enum;

public interface QRScannerCallback {
    void onSuccess(String result);
    void onFailure(String message);
}
