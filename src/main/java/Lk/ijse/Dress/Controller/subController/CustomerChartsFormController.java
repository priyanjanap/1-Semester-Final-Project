package Lk.ijse.Dress.Controller.subController;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import java.util.Random;

public class CustomerChartsFormController {
    @FXML
    private LineChart<?, ?> chart;

    public void initialize() {
        lineChart();
    }

    private void lineChart() {
        XYChart.Series seriesMonday = new XYChart.Series();
        XYChart.Series seriesTuesday = new XYChart.Series();
        XYChart.Series seriesWednesday = new XYChart.Series();
        XYChart.Series seriesThursday = new XYChart.Series();
        XYChart.Series seriesFriday = new XYChart.Series();
        XYChart.Series seriesSaturday = new XYChart.Series();
        XYChart.Series seriesSunday = new XYChart.Series();

        seriesMonday.setName("Monday");
        seriesTuesday.setName("Tuesday");
        seriesWednesday.setName("Wednesday");
        seriesThursday.setName("Thursday");
        seriesFriday.setName("Friday");
        seriesSaturday.setName("Saturday");
        seriesSunday.setName("Sunday");

        Random random = new Random();
        for (int i = 1; i <= 7; i++) {
            int value = random.nextInt(100);
            seriesMonday.getData().add(new XYChart.Data(Integer.toString(i), value));
            value = random.nextInt(100);
            seriesTuesday.getData().add(new XYChart.Data(Integer.toString(i), value));
            value = random.nextInt(100);
            seriesWednesday.getData().add(new XYChart.Data(Integer.toString(i), value));
            value = random.nextInt(100);
            seriesThursday.getData().add(new XYChart.Data(Integer.toString(i), value));
            value = random.nextInt(100);
            seriesFriday.getData().add(new XYChart.Data(Integer.toString(i), value));
            value = random.nextInt(100);
            seriesSaturday.getData().add(new XYChart.Data(Integer.toString(i), value));
            value = random.nextInt(100);
            seriesSunday.getData().add(new XYChart.Data(Integer.toString(i), value));
        }

        chart.getData().addAll(seriesMonday, seriesTuesday, seriesWednesday, seriesThursday,
                seriesFriday, seriesSaturday, seriesSunday);
    }
}
