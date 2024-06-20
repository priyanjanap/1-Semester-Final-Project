package Lk.ijse.Dress.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class MaterialDress {


        private  String id1;
       private  int qty;
          private  String id2;
        private  double price;
        private  double total=qty*price;

}
