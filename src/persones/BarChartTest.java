package persones;

/**
 *
 * @author julian
 */
public class BarChartTest extends BasicBarChart {

    private String[] categories;
    private double[] data;
    
    public static void main(String[] args) {
        launch(args);   
    }
    
    @Override
    public String[] getCatData() {
        return this.categories;
    }

    @Override
    public double[] getYData() {
        return this.data;
    }
    
    // MODIFY ONLY FROM HERE
    
    @Override
    public void calculate() {
        // do the calculations
        this.categories = new String[]{"one", "two"};
        this.data = new double[]{75, 50};
    }
    
}
