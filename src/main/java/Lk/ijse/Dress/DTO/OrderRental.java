package Lk.ijse.Dress.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor@Data@AllArgsConstructor
public class OrderRental {
    private Rental rental;
    private Payment payment;
    private  String name;
}
