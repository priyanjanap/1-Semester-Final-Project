package Lk.ijse.Dress.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialModel {

    private  String MaterialId;
    private  String MaterialName;
    private  int Qty;
    private  double price;

}
