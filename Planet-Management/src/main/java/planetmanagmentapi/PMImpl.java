package planetmanagmentapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Gagadok
 */
public class PMImpl implements PlanetManagmentAPI {

    private Connection connection = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/";

    public PMImpl(String database, String login, String password) {
        DBConnecting(database, login, password);
    }

    @Override
    public Queue<String> ShowOverlords(){
        String request = "SELECT name, age FROM Overlord";
        return Request(request, 1);
    }
    
    @Override
    public boolean AddOverlord(String overlords_name, int overlords_age) {
        String request = "INSERT INTO Overlord (Name, Age)"
                + "VALUES ('" + overlords_name + "', " + Integer.toString(overlords_age) + ");";
        return Request(request);
    }

    @Override
    public boolean AddPlanet(String planets_title) {
        String request = "INSERT INTO Planet (Title)"
                + "VALUES ('" + planets_title + "');";
        return Request(request);
    }

    @Override
    public boolean AppointOverlord(int overlords_id, String planets_title) {
        String request = "UPDATE Planet SET Fk_Overlord_ID = "
                + Integer.toString(overlords_id) + "WHERE Title = '" + planets_title + "';";
        return Request(request);
    }

    @Override
    public boolean DestroyPlanet(String planets_title) {
        String request = "DELETE FROM Planet WHERE Title = '" + planets_title + "';";
        return Request(request);
    }

    @Override
    public Queue<String> FindLoafers() {
        String request = """
                         SELECT Overlord_ID FROM Overlord a LEFT JOIN
                         Planet b ON a.Overlord_ID = b.Fk_Overlord_ID
                         WHERE b.Fk_Overlord_ID IS NULL""";
        return Request(request, 0);
    }

    @Override
    public Queue<String> Top10YoungOverlords() {
        String request = """
                         SELECT name, age FROM Overlord
                         ORDER BY age
                         FETCH FIRST 10 ROWS ONLY""";
        return Request(request, 1);
    }

    private Queue<String> Request(String request, int function_id) {
        ResultSet resultSet;
        Queue<String> values = new LinkedList<>();
        try {
            resultSet = connection.createStatement().executeQuery(request);

            while (resultSet.next()) {
                if (function_id == 0) {
                    values.add(resultSet.getString(1));
                } else {
                    values.add(resultSet.getString(1));
                    values.add(resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }

    private boolean Request(String request) {
        boolean successfully = true;
        try {
            connection.createStatement().executeUpdate(request);
        } catch (SQLException e) {
            successfully = false;
            e.printStackTrace();
        }
        return successfully;
    }

    private void DBConnecting(String database, String login, String password) {
        String db_url = URL + database;
        try {
            connection = DriverManager.getConnection(db_url, login, password);
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

}
