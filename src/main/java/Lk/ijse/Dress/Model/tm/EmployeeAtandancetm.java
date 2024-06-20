package Lk.ijse.Dress.Model.tm;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeAtandancetm {
    private String id;
    private String id2;
    private DatePicker datePicker;
    private ChoiceBox<String> statusChoiceBox;
    private ChoiceBox<String> punStatusChoiceBox;
}
