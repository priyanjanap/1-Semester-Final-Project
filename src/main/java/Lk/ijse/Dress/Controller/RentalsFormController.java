package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.DTO.RentalTable;
import Lk.ijse.Dress.DTO.tm.Rental2Tm;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class RentalsFormController {

    @FXML
    private TableView<Rental2Tm> btlRental;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colCusName;

    @FXML
    private TableColumn<?, ?> colDressId;

    @FXML
    private TableColumn<?, ?> colReId;

    @FXML
    private TableColumn<?, ?> colResComplete;

    @FXML
    private TableColumn<?, ?> colReservationDate;

    @FXML
    private TableColumn<?, ?> colReturnComplte;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private Label lblLate;

    @FXML
    private Label lblongoing;

    @FXML
    private Label lblrentalcount;

    @FXML
    private Label lblupcoming;
    private void setRentalCount(int rentalCount) {
        lblrentalcount.setText(String.valueOf(rentalCount));
    }
    @FXML
    void btmNewRentalOnAction(ActionEvent event) {
    }

    public void initialize() {
        setCellValueFactory();
        loadAllRental();
        updateLateRentalCount();
        updateOngoingRentalCount();
        updateUpcomingRentalCount();
        loadCounts();
    }

    private void setCellValueFactory() {
        colReId.setCellValueFactory(new PropertyValueFactory<>("id1"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("id2"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("number"));
        colDressId.setCellValueFactory(new PropertyValueFactory<>("id3"));
        colReservationDate.setCellValueFactory(new PropertyValueFactory<>("date1"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("date2"));
        colResComplete.setCellValueFactory(new PropertyValueFactory<>("check"));
        colReturnComplte.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    public static ObservableList<RentalTable> loadRentalAndOrdersData() {
        ObservableList<RentalTable> rentalList = FXCollections.observableArrayList();

        String url = "jdbc:mysql://localhost:3306/luxora";
        String user = "root";
        String password = "p1a2s3i4n5@P";

        String joinQuery = "SELECT r.rental_id, r.start_date, r.last_date, c.Customer_Id, c.Customer_name, c.Customer_contact_number, rd.Dressid " +
                "FROM customer c " +
                "JOIN rental r ON c.Customer_Id = r.customer_id " +
                "JOIN rentaldress rd ON r.rental_id = rd.rentalID";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(joinQuery)) {

            while (resultSet.next()) {
                String rentalId = resultSet.getString("rental_id");
                Date startDate = resultSet.getDate("start_date");
                Date lastDate = resultSet.getDate("last_date");
                String customerId = resultSet.getString("Customer_Id");
                String customerName = resultSet.getString("Customer_name");
                int customerContactNumber = resultSet.getInt("Customer_contact_number");
                String dressId = resultSet.getString("Dressid");

                RentalTable rentalTable = new RentalTable(rentalId, customerId, customerName, customerContactNumber, dressId, startDate, lastDate);
                rentalList.add(rentalTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentalList;
    }

    ObservableList<Rental2Tm> obList = FXCollections.observableArrayList();

    public void loadAllRental() {
        List<RentalTable> rentalTableList = loadRentalAndOrdersData();

        for (RentalTable rentalTable : rentalTableList) {
            Rental2Tm rentalTm = new Rental2Tm(
                    rentalTable.getId1(),
                    rentalTable.getId2(),
                    rentalTable.getName(),
                    rentalTable.getNumber(),
                    rentalTable.getId3(),
                    rentalTable.getDate1(),
                    rentalTable.getDate2(),
                    createCheckBox(),
                    new JFXButton("action")
            );

            obList.add(rentalTm);
        }

        btlRental.setItems(obList);
    }

    private CheckBox createCheckBox() {
        CheckBox checkBox = new CheckBox("compelte");
        checkBox.setCursor(Cursor.HAND);

        checkBox.setOnAction(e -> {
            String message = checkBox.isSelected() ? "CheckBox is selected." : "CheckBox is deselected.";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
            alert.setHeaderText(null);
            alert.showAndWait();
        });

        return checkBox;
    }

    private void updateLateRentalCount() {
        long lateRentalCount = obList.stream()
                .filter(rental -> {
                    Date rentalDate = (Date) rental.getDate2();
                    LocalDate localDate = rentalDate.toLocalDate();
                    return localDate.isBefore(LocalDate.now());
                })
                .count();

        lblLate.setText(String.valueOf(lateRentalCount));
    }

    private void updateOngoingRentalCount() {
        long ongoingRentalCount = obList.stream()
                .filter(rental -> {
                    Date startDate = (Date) rental.getDate1();
                    Date endDate = (Date) rental.getDate2();
                    LocalDate today = LocalDate.now();
                    LocalDate startLocalDate = startDate.toLocalDate();

                    if (endDate == null) {
                        return startLocalDate.isBefore(today) || startLocalDate.isEqual(today);
                    }

                    LocalDate endLocalDate = endDate.toLocalDate();
                    return startLocalDate.isBefore(today) || startLocalDate.isEqual(today) &&
                            endLocalDate.isAfter(today) || endLocalDate.isEqual(today);
                })
                .count();

        lblongoing.setText(String.valueOf(ongoingRentalCount));
    }
    private void updateUpcomingRentalCount() {
        long upcomingRentalCount = obList.stream()
                .filter(rental -> {
                    Date startDate = (Date) rental.getDate1();
                    LocalDate today = LocalDate.now();
                    LocalDate startLocalDate = startDate.toLocalDate();
                    return startLocalDate.isAfter(today);
                })
                .count();

        lblupcoming.setText(String.valueOf(upcomingRentalCount));
    }
    private int getRentalCount() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT COUNT(*) AS rental_count FROM rental";
        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("rental_count");
            }
        }
        return 0;
    }
    private void loadCounts() {
        try {
            setRentalCount(getRentalCount());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load counts: " + e.getMessage()).show();
        }
    }


}
