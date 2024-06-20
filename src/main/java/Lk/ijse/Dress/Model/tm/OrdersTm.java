package Lk.ijse.Dress.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrdersTm {
    private  String id1;
    private  String id2;
    private  String name;
    private Date date;
    private  int number;
    private  String view;
    private  String finish;
    private  String handover;
    private JFXButton action;

}
