package Lk.ijse.Dress.Model.tm;

import Lk.ijse.Dress.Model.Enum.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderTm {
    private  String payid;
    private  String cusid;
    private  String name;
    private  double total;
    private  String nic;
    private PaymentType paymentType;
    private  double amount;

}
