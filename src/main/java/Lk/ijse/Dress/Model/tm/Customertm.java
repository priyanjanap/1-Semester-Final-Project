package Lk.ijse.Dress.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customertm {
    private  String CustomerId;
    private  String CustomerName;
    private  String CustomerAddress;
    private  int tell;
    private String email;
    private JFXButton edit;
    private  JFXButton btnRemove;


}
