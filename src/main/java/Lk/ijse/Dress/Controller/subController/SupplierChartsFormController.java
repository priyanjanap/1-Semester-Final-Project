package Lk.ijse.Dress.Controller.subController;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.Random;

public class SupplierChartsFormController {
    @FXML
    private LineChart<String, Number> chart;

    public void initialize() {
        lineChart();
    }

    private void lineChart() {
        String[] suppliers = {"Supplier A", "Supplier B", "Supplier C", "Supplier D", "Supplier E", "Supplier F", "Supplier G"};
        String[] materials = {"Material 1", "Material 2", "Material 3", "Material 4", "Material 5", "Material 6", "Material 7"};

        Random random = new Random();
        for (String supplier : suppliers) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(supplier);
            for (String material : materials) {
                int value = random.nextInt(100);
                series.getData().add(new XYChart.Data<>(material, value));
            }
            chart.getData().add(series);
        }

        chart.getXAxis().setLabel("Suppliers");
        chart.getYAxis().setLabel("Material Quantity");
    }
}
