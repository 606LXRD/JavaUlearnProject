import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import org.jfree.ui.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;

class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Подключение к БД
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\delat\\IdeaProjects\\untitled1\\database.db", "", "");
        Statement stmt = conn.createStatement();
        // Запуск парсера и заполнения БД
        Parser();
        // Рисовка графика ( 1 задание )
        PieChart_AWT demo = new PieChart_AWT("Ответ на первое задание");
        demo.setSize(560, 367);
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        // Запрос (2 задание )
        ResultSet rs2;
        rs2 = stmt.executeQuery("SELECT Country FROM Продукты WHERE (Region = 'Europe' OR Region = 'Asia') ORDER BY Total_Profit DESC");
        System.out.println("Ответ на второе задание : ");
        System.out.println(rs2.getString(1));
        ResultSet rs3;
        // Запрос ( 3 задание )
        rs3 = stmt.executeQuery("SELECT Country FROM Продукты WHERE (Total_Profit >= 420000.0 AND Total_Profit <= 440000.0) " +
                "AND (Region = 'Middle East and North Africa' OR Region = 'Sub-Saharan Africa') ORDER BY Total_Profit DESC");
        System.out.println("Ответ на третье задание : ");
        System.out.println(rs3.getString(1));

    }
    // Метод парсера
    public static void Parser()throws SQLException{
        try (BufferedReader br = Files.newBufferedReader(Paths.get("Продажа продуктов в мире.csv"))) {
            String DELIMITER = ",";
            String line;
            int key = 0;
            while ((line = br.readLine()) != null) {

                String[] columns = line.split(DELIMITER);
                String.join(", ", columns);
                // Заполнение БД (парсим csv по столбцам, каждый столб парсера присваеваем переменной и запросом заполняем БД)
                Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\delat\\IdeaProjects\\untitled1\\database.db", "", "");
                Statement stmt = conn.createStatement();
                String region = columns[0];
                String country = columns[1];
                String item_Type = columns[2];
                String sales_Channel = columns[3];
                String order_Date = columns[4];
                String order_Priority = columns[5];
                String units_Sold = columns[6];
                String total_Profit = columns[7];
                stmt.executeUpdate("INSERT INTO Продукты(userid,Region,Country,Item_Type,Sales_Channel,Order_Date,Order_Priority,Units_Sold,Total_Profit) " +
                        "VALUES ('"+key+"','" + region + "','" + country + "','" + item_Type + "','" + sales_Channel + "','" + order_Date + "','" + order_Priority + "','" + units_Sold + "','" + total_Profit + "')");
                key++;

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}





