package Lk.ijse.Dress.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierPayment {
    private  String id1;
    private  String id2;
    private  String name;
    private  double amount;
    private Date date;
}
