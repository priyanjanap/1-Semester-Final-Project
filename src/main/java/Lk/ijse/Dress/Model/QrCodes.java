package Lk.ijse.Dress.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QrCodes {
    private int id;
    private  Byte qrData;
    private String nic;
    private  String empId;
}
