package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.PlaceRental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PlaceRentalDTO {




    public static boolean placeRental(PlaceRental po) {


        try {
            Connection  connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxora","root","p1a2s3i4n5@P");
            connection.setAutoCommit(false);

            boolean isOrderSaved = RentalREPO.insert(po.getRental());
            boolean isOrderDetailSaved = RentalDressDTO.save(po.getOdList());
            boolean isPaymentSaved = PaymentRepo.save(po.getPayment());

            System.out.println("OrderSaved: "+isOrderSaved);
            System.out.println("OD saved : "+isOrderDetailSaved);
            System.out.println("payme save: "+isPaymentSaved);

            if (isOrderSaved && isOrderDetailSaved && isPaymentSaved) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                System.out.println("Transaction failed. Rolling back changes.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }
}