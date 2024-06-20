package Lk.ijse.Dress.Controller.subController;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ProductChartsFormController {
    @FXML
    private LineChart<String, Number> chart;

    public void initialize() {
        chart.setAnimated(false);
        lineChart();
    }

    private void lineChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        chart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);

        chart.setTitle("Product Sales Chart");
        xAxis.setLabel("Month");
        yAxis.setLabel("Quantity Sold");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
        XYChart.Series<String, Number> series5 = new XYChart.Series<>();
        XYChart.Series<String, Number> series6 = new XYChart.Series<>();
        XYChart.Series<String, Number> series7 = new XYChart.Series<>();

        series1.setName("Product 1");
        series2.setName("Product 2");
        series3.setName("Product 3");
        series4.setName("Product 4");
        series5.setName("Product 5");
        series6.setName("Product 6");
        series7.setName("Product 7");

        addData(series1, "January", 100);
        addData(series2, "January", 150);
        addData(series3, "January", 200);
        addData(series4, "January", 80);
        addData(series5, "January", 120);
        addData(series6, "January", 130);
        addData(series7, "January", 110);
        addData(series1, "February", 110);
        addData(series1, "March", 120);
        addData(series1, "April", 130);
        addData(series1, "May", 140);
        addData(series1, "June", 150);
        addData(series1, "July", 160);

        addData(series2, "February", 160);
        addData(series2, "March", 170);
        addData(series2, "April", 180);
        addData(series2, "May", 190);
        addData(series2, "June", 200);
        addData(series2, "July", 210);

        addData(series3, "February", 210);
        addData(series3, "March", 220);
        addData(series3, "April", 230);
        addData(series3, "May", 240);
        addData(series3, "June", 250);
        addData(series3, "July", 260);

        addData(series4, "February", 100);
        addData(series4, "March", 110);
        addData(series4, "April", 120);
        addData(series4, "May", 130);
        addData(series4, "June", 140);
        addData(series4, "July", 150);

        addData(series5, "February", 150);
        addData(series5, "March", 160);
        addData(series5, "April", 170);
        addData(series5, "May", 180);
        addData(series5, "June", 190);
        addData(series5, "July", 200);

        addData(series6, "February", 200);
        addData(series6, "March", 210);
        addData(series6, "April", 220);
        addData(series6, "May", 230);
        addData(series6, "June", 240);
        addData(series6, "July", 250);

        addData(series7, "February", 250);
        addData(series7, "March", 260);
        addData(series7, "April", 270);
        addData(series7, "May", 280);
        addData(series7, "June", 290);
        addData(series7, "July", 300);

        chart.getData().addAll(series1, series2, series3, series4, series5, series6, series7);
    }

    private void addData(XYChart.Series<String, Number> series, String month, int quantity) {
        series.getData().add(new XYChart.Data<>(month, quantity));
    }

}
