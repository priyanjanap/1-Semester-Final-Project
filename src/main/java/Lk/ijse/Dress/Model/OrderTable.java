package Lk.ijse.Dress.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderTable {
    private String orderId;
    private Date date;
private  String cusid;
private  String cusname;
private  int number;

}
