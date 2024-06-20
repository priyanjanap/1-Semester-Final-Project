package Lk.ijse.Dress.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Employeetm {
    private String EmployeeId;
    private String EmployeeName;
    private String EmployeeAddress;
    private int EmployeeAge;
    private double Salary;
    private String Gender;
    private Date dateOfBirth;
    private  int ContactNumber;
   @Getter
    private JFXButton Edit;
    @Getter
    private JFXButton btnRemove;




}
