package Lk.ijse.Dress.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductAddtm {

        private  String id;
        private  String name;
        private  int qty;
        private  double price;
        private  String description;
        private  String type;
        private  String category;
        private JFXButton action;

}
