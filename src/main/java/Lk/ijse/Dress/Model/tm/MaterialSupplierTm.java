package Lk.ijse.Dress.Model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaterialSupplierTm {
    private  String supplierId;
    private  String materialId;
    private  String materialName;
    private  String supllierName;
    private  int qty;
    private  double price;



}
