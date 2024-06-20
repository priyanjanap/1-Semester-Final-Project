package Lk.ijse.Dress.Model.tm;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor@Data
public class Rental2Tm {
    private  String id1;
    private  String id2;
    private  String name;
    private  int number;
    private String id3;
    private  Date date1;
    private  Date date2;
    private CheckBox check;
    private JFXButton action;

}
