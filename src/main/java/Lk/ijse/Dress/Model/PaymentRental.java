package Lk.ijse.Dress.Model;


import Lk.ijse.Dress.Model.Enum.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data@NoArgsConstructor
public class PaymentRental {
private String payid;
private  String cusid;
private  String cusname;
private double total;
private String nic;
private PaymentType paymentType;
private  double amount;


}
