package Lk.ijse.Dress.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Discounttm {

    private String Code;
    private String Description;
    private  int Qty;
    private double UnitPrice;
    private double Discount;
    private double Total;
    private JFXButton btmRemove;


}
