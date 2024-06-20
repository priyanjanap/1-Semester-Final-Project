package Lk.ijse.Dress.DTO;

import Lk.ijse.Dress.DTO.Enum.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrder {
    private  String payid;
    private  String cusid;
    private  String name;
    private  double total;
    private  String nic;
    private PaymentType paymentType;
    private  double amount;

}
