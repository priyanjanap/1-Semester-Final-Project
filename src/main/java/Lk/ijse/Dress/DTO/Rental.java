package Lk.ijse.Dress.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rental {
    private  String rentalId;
    private  String customeid;
    private  String paymentId;
    private LocalDate stDate;
    private  LocalDate lastDate;
    private  int timeDuration;
}
