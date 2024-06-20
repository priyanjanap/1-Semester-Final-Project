package Lk.ijse.Dress;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

import static javafx.fxml.FXMLLoader.load;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage Stage) throws Exception {
        //    Parent rootNode= load((Objects.requireNonNull(this.getClass().getResource("/View/setting.fxml"))));
        Parent rootNode = load((Objects.requireNonNull(this.getClass().getResource("/View/VIEW2/start_form.fxml"))));
        Scene scene = new Scene(rootNode);
        //   Stage.initStyle(StageStyle.UTILITY);

        Stage.initStyle(StageStyle.UNDECORATED);
        // Stage.setMaximized(true);
        Stage.centerOnScreen();

        Stage.setScene(scene);
        Stage.show();


    }


}

//mainAnchorePane
//btmDashboard
/*btmDashboard
btmDashboardOnAction
btmProduct
btmProductOnAction,btmDress,btmDressOnAction,btmPayment,btmPaymentOnAction,btmEmployee,btmEmployeeOnAction,btmrentals,btmrentalsOnAction,btmCustomer,btmCustomerOnAction,
btmOrder,btmOrdersOnAction,btmSupplier,btmSupplierOnAction,btmMaterial,btmMaterialOnAction,lblDay,btmLogout,BtmLogOutOnAction,mainAnchorePane











 */