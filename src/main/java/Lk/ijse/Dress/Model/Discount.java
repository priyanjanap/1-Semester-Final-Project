package Lk.ijse.Dress.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Discount {
   private  String item_id;
   private  String description;
   private  double unit_price;
   private int qty;


   public Discount(String orderId, String code, int qty, double unitPrice) {
   }
}
