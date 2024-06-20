package Lk.ijse.Dress.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDiscountTm {
    private String id1;
    private String id2;
    private String name;
    private double total;
    private Date date;
    private String dressId;
    private JFXButton action;
}
