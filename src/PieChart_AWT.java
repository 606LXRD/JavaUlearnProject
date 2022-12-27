import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import java.sql.*;

// Класс для рисовки графика
public class PieChart_AWT extends ApplicationFrame {
    // Конструктор класса
    public PieChart_AWT( String title ) throws ClassNotFoundException, SQLException {
        super( title );
        setContentPane(createDemoPanel( ));
    }

    // метод, считающий общее кол-во товаров у каждого региона
    public static int count(String region) throws ClassNotFoundException, SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\delat\\IdeaProjects\\untitled1\\database.db", "", "");
        Statement stmt = conn.createStatement();
        // делаем запрос по всей базе, идем по ней и смотрим Region и Units_Sold,
        // если название региона равно заданому прибавляем значение Units_Sold
        ResultSet rs1 = stmt.executeQuery("SELECT * FROM Продукты");
        int count = 0;
        while (rs1.next()) {
            if (region.equals(rs1.getString(2))) {
                count += rs1.getInt(8);
            }
        }
        return count;
    }
    // метод заполнения графика данными
    private static PieDataset createDataset()throws ClassNotFoundException, SQLException{
        // заполнив каждый регион нужными данными, создаем массив их этих данных
        DefaultPieDataset dataset = new DefaultPieDataset( );
        int asia = count("Asia");
        int australita = count("Australia and Oceania");
        int central_america = count("Central America and the Caribbean");
        int europe = count("Europe");
        int middle_east = count("Middle East and North Africa");
        int north_america = count("North America");
        int africa = count("Sub-Saharan Africa");
        int [] array = {asia,australita,central_america,europe,middle_east,north_america,africa};
        // передаем значение массива каждому региноу
        dataset.setValue( "Asia" , array [0]);
        dataset.setValue( "Australia and Oceania" , array [1] );
        dataset.setValue( "Central America and the Caribbean" , array [2] );
        dataset.setValue( "Europe" , array [3] );
        dataset.setValue( "Middle East and North Africa" , array [4] );
        dataset.setValue( "North America" , array [5] );
        dataset.setValue( "Sub-Saharan Africa" , array [6] );
        return dataset;
    }
    // создаем график
    private static JFreeChart createChart( PieDataset dataset ) {
        JFreeChart chart = ChartFactory.createPieChart("Общее количество товаров по регионам", dataset, true,true, false);
        return chart;
    }
    // создаем окно с графиком
    public static JPanel createDemoPanel( )throws ClassNotFoundException, SQLException {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel( chart );
    }

}
