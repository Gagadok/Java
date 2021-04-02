/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gagadok
 */
public class PostgreSQL {

    // Данные, для подключения к PostgreSQL
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Kursach_DB";
    private static final String USER = "postgres";
    private static final String PASS = "22041998";
    private String url = DB_URL + "?user=" + USER + "&password=" + PASS;
    private Connection connection = null;

    public PostgreSQL() {
        testPostgreSQL();
    }

    private void testPostgreSQL() {
        System.out.println("\nТестирование подключения к PostgreSQL");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Драйвер не найден");
            e.printStackTrace();
            return;
        }

        System.out.println("Драйвер PostgreSQL JDBC подключён");

        try {
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println("Ошибка подключения");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("Подключение к базе данных прошло успешно");
        } else {
            System.out.println("Невозможно подключиться к базе данных");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
            }
        }
    }

    public void close(java.sql.Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
            }
        }
    }

    public void destroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    public ResultSet find(String table, String field, String objectOfTheSearch) throws SQLException {
        ResultSet res;
        String request = "SELECT * FROM public.\"" + table + "\" WHERE " + field + " = " + "'" + objectOfTheSearch + "';";
        return res = connection.createStatement().executeQuery(request);
    }
    
    public ResultSet find(String table, String field) throws SQLException {
        ResultSet res;
        String request = "SELECT " +  field + " FROM public.\"" + table + "\";";
        return res = connection.createStatement().executeQuery(request);
    }

    public void insert(String table, String variables, String values) throws SQLException {
        String request = "INSERT INTO public.\"" + table + "\"" + variables + " VALUES " + values;
        connection.createStatement().executeUpdate(request);
    }
    
    public void delete(String table, String field, String delObject) throws SQLException {
        String request = "DELETE FROM public.\"" + table + "\" WHERE " + field + " = " + "'" + delObject + "';";
        connection.createStatement().executeUpdate(request);
    }
    
    public void update(String table, String field, String oldObject, String newObject) throws SQLException{
        String request = "UPDATE public.\"" + table + "\" SET " + field + " = " + "'" + newObject + "' WHERE " +
                field + " = '" + oldObject + "';";
        connection.createStatement().executeUpdate(request);
    }
}
