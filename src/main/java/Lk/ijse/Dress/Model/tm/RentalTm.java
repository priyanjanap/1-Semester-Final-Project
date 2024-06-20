package Lk.ijse.Dress.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalTm {
    private  String id;
    private  String name;

    private LocalDate date;

    private  LocalDate reservationDate;

    private  double price;


    private JFXButton update;

    private  JFXButton action;

}
