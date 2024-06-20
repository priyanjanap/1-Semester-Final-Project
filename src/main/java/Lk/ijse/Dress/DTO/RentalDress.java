package Lk.ijse.Dress.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalDress {
    private  String rentalID;
    private String DressId;
    private  double price_per_day;
    private LocalDate stdate;
    private  LocalDate lastDate;


}
