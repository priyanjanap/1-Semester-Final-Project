package Lk.ijse.Dress.Model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDresstm {
    private  String id1;
    private  String id2;
    private  int qty;
    private  double price;
    private  double total=qty*price;


}
