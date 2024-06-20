package Lk.ijse.Dress.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order_discount {
    private  String orderId;
    private  String item_id;
    private  double unit_price;
    private int qty;

}
