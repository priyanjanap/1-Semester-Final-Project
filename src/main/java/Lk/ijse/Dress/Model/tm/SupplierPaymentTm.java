package Lk.ijse.Dress.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierPaymentTm {
    private  String id1;
    private  String id2;
    private  String name;
    private  double amount;
    private Date date;
    private JFXButton action;
}
