package persones;

import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Michael Brzustowicz
 */
public abstract class BasicBarChart extends Application {

    public abstract void calculate();
    public abstract String[] getCatData();
    public abstract double[] getYData();
    
    @Override
    public void start(Stage stage) throws Exception {
        
        calculate();
        String[] catData = getCatData();
        double[] yData = getYData();
        
        /*
         create some data
        */
        Series series = new Series();
        for (int i = 0; i < yData.length; i++) {
            series.getData().add(new Data(catData[i], yData[i]));
        }
        
        
        //defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");
        
        //creating the bar chart;
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setAnimated(false);
        barChart.getData().add(series);
        barChart.setTitle("x vs. y");
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setVerticalZeroLineVisible(false);
        
        /* 
        create a scene using the chart
        */
        Scene scene  = new Scene(barChart,800,600);
        
        /*
         tell the stage what scene to use and render it!
        */
        stage.setScene(scene);
        stage.show();
        
        WritableImage image = barChart.snapshot(new SnapshotParameters(), null);
        File file = new File("data/chart.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        
    }
    
}
