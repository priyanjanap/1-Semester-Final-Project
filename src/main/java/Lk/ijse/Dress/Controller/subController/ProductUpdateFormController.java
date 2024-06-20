package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Repository.ProductDTO;
import Lk.ijse.Dress.DTO.Product;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;

public class ProductUpdateFormController {

    @FXML
    private JFXButton btmCansel;

    @FXML
    private JFXButton btmDelete;

    @FXML
    private JFXButton btmUpdate;

    @FXML
    private TextField txtCategoray;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtType;

    @FXML
    public void initialize() {
        txtProductId.setOnKeyPressed(this::handleEnterKeyPress);
    }

    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            fetchProductDetails();
        }
    }

    @FXML
    void btmCanselOnAction(ActionEvent event) {
        // Handle cancel action
    }

    @FXML
    void btmDeleteOnAction(ActionEvent event) {
        // Handle delete action
    }

    @FXML
    void btmUpdateOnAction(ActionEvent event) {
        updateProductDetails();
    }

    private void fetchProductDetails() {
        String productId = txtProductId.getText().trim();

        if (productId.isEmpty()) {
            showAlert("Validation Error", "Product ID is required.");
            return;
        }

        try {
            Product product = ProductDTO.searchById(productId);
            if (product == null) {
                showAlert("Error", "Not a valid Product ID. No such product exists.");
            } else {
                populateForm(product);
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to fetch product: " + e.getMessage());
        }
    }

    private void updateProductDetails() {
        String productId = txtProductId.getText().trim();

        if (productId.isEmpty()) {
            showAlert("Validation Error", "Product ID is required.");
            return;
        }

        try {
            Product productToUpdate = createProductFromForm();
            boolean success = ProductDTO.update(productToUpdate);
            if (success) {
                showAlert("Update Successful", "Product updated successfully.");
            } else {
                showAlert("Update Failed", "Failed to update the product.");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to update product: " + e.getMessage());
        }
    }

    private Product createProductFromForm() {
        String productId = txtProductId.getText().trim();
        String name = txtName.getText().trim();
        String category = txtCategoray.getText().trim();
        String description = txtDescription.getText().trim();
        double price = Double.parseDouble(txtPrice.getText().trim());
        int quantity = Integer.parseInt(txtQuantity.getText().trim());
        String type = txtType.getText().trim();

        return new Product(productId, name, quantity, price, description, type, category);
    }

    private void refreshForm() {
        txtProductId.clear();
        txtName.clear();
        txtCategoray.clear();
        txtDescription.clear();
        txtPrice.clear();
        txtQuantity.clear();
        txtType.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void populateForm(Product product) {
        txtName.setText(product.getName());
        txtQuantity.setText(String.valueOf(product.getQty()));
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtDescription.setText(product.getDescription());
        txtType.setText(product.getType());
        txtCategoray.setText(product.getCategory());
    }
}
