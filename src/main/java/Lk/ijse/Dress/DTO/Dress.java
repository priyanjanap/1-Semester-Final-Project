package Lk.ijse.Dress.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dress {


    private  String id;
    private  String name;

    private  int days;
    private  double pircePerDate;
    private Date stdate;
    private  Date lastDate;

}
