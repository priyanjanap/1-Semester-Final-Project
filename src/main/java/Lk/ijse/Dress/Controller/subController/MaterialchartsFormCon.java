package Lk.ijse.Dress.Controller.subController;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class MaterialchartsFormCon {
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

        chart.setTitle("Material Usage Chart");
        xAxis.setLabel("Month");
        yAxis.setLabel("Usage (in kilograms)");

        XYChart.Series<String, Number> seriesCotton = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesPolyester = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesWool = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesSilk = new XYChart.Series<>();

        seriesCotton.setName("Cotton");
        seriesPolyester.setName("Polyester");
        seriesWool.setName("Wool");
        seriesSilk.setName("Silk");

        seriesCotton.getData().add(new XYChart.Data<>("January", 100));
        seriesPolyester.getData().add(new XYChart.Data<>("January", 150));
        seriesWool.getData().add(new XYChart.Data<>("January", 200));
        seriesSilk.getData().add(new XYChart.Data<>("January", 80));
        seriesCotton.getData().add(new XYChart.Data<>("February", 120));
        seriesCotton.getData().add(new XYChart.Data<>("March", 130));
        seriesCotton.getData().add(new XYChart.Data<>("April", 110));
        seriesCotton.getData().add(new XYChart.Data<>("May", 140));
        seriesCotton.getData().add(new XYChart.Data<>("June", 120));

        seriesPolyester.getData().add(new XYChart.Data<>("February", 160));
        seriesPolyester.getData().add(new XYChart.Data<>("March", 170));
        seriesPolyester.getData().add(new XYChart.Data<>("April", 180));
        seriesPolyester.getData().add(new XYChart.Data<>("May", 150));
        seriesPolyester.getData().add(new XYChart.Data<>("June", 170));

        seriesWool.getData().add(new XYChart.Data<>("February", 220));
        seriesWool.getData().add(new XYChart.Data<>("March", 240));
        seriesWool.getData().add(new XYChart.Data<>("April", 230));
        seriesWool.getData().add(new XYChart.Data<>("May", 250));
        seriesWool.getData().add(new XYChart.Data<>("June", 260));

        seriesSilk.getData().add(new XYChart.Data<>("February", 90));
        seriesSilk.getData().add(new XYChart.Data<>("March", 100));
        seriesSilk.getData().add(new XYChart.Data<>("April", 110));
        seriesSilk.getData().add(new XYChart.Data<>("May", 120));
        seriesSilk.getData().add(new XYChart.Data<>("June", 100));


        chart.getData().addAll(seriesCotton, seriesPolyester, seriesWool, seriesSilk);
    }
}
