package Lk.ijse.Dress.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalTable {
    private  String id1;
    private  String id2;
    private  String name;
    private  int number;
    private  String id3;
    private Date date1;
    private  Date date2;
}
