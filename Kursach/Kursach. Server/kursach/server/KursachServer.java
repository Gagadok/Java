/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kursach.server;

import database.PostgreSQL;
import SeparatorGeneration.SeparatorGeneration;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gagadok
 */
public class KursachServer extends SeparatorGeneration {

    private static ArrayList<Connection> connections = new ArrayList<Connection>();
    private static final int port = 2525;
    private static ServerSocket ss;
    private static PostgreSQL postgreSQL;
    private static String[] command = {"entrance", // Вход
        "registration", // Регистрация
        "getUserRole", // Получить роль пользователя
        "sendSupplier", // Отправить список имеющихся поставщиков и получить изменения
        "addSupplier", // Добовление поставшика
        "delSupplier", // Удаление поставшика
        "editSuppliers", // Редактирование поставщика
        "addProduct", // Добовление продукта
        "sendProduct", // Отправить список имеющихся продуктов и получить изменения
        "getProductCode", // Получить код товара
        "toChangeTheQuantityOfGoods", // Изменить количество товара
        "toMakeAnInventoryOfTheGoods", // Провести учёт товара
        "getNumber", // Получить количесто каждого товара
        "getProductInfo", // Получить информацию о товаре
        "productRegistration" // Регистрация продукта
};

    public static void main(String[] args) {
        try {
            ss = new ServerSocket(port);
            System.out.println("Сервер запущен");

            postgreSQL = new PostgreSQL();

            while (true) {
                Socket socket = ss.accept();
                connections.add(new Connection(socket));
                System.out.println("\nПодклчился новый клиент!☻\n Количество пользоваталей: " + connections.size());
            }
        } catch (IOException ex) {
        }
    }

    private static class Connection extends Thread {

        private DataInputStream input;
        private DataOutputStream output;
        private Socket socket;
        private Connection connect;

        public Connection(Socket socket) {
            try {
                this.socket = socket;
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                start();
            } catch (IOException ex) {
            }
        }

        public void run() {
            connect = connections.get(connections.size() - 1);
            try {
                while (true) {
                    String data = receive();
                    if (data != null) {
                        String[] message = decoding(data);
                        if (message[0].equals(command[0])) {
                            entrance(message[1], message[2]);
                        }
                        if (message[0].equals(command[1])) {
                            registration(message[1], message[2], message[3], message[4], message[5], message[6]);
                        }
                        if (message[0].equals(command[2])) {
                            ResultSet res = postgreSQL.find("Authorization", "login", message[1]);
                            res.next();
                            send(res.getString("typeOfWork"));
                        }
                        if (message[0].equals(command[3])) {
                            sendSupplier(message);
                        }
                        if (message[0].equals(command[4])) {
                            addSupplier(message[1]);
                        }
                        if (message[0].equals(command[5])) {
                            delSupplier(message[1]);
                        }
                        if (message[0].equals(command[6])) {
                            editSupplier(message[1], message[2]);
                        }
                        if (message[0].equals(command[7])) {
                            addProduct(message[1], message[2], message[3]);
                        }
                        if (message[0].equals(command[8])) {
                            sendProduct(message);
                        }
                        if (message[0].equals(command[9])) {
                            getProductCode(message[1]);
                        }
                        if (message[0].equals(command[10])) {
                            toChangeTheQuantityOfGoods(message[1], message[2], message[3]);
                        }
                        if (message[0].equals(command[11])) {
                            toMakeAnInventoryOfTheGoods(message[1], message[2]);
                        }
                        if (message[0].equals(command[12])) {
                            getNumber(message);
                        }
                        if (message[0].equals(command[13])) {
                            getProductInfo(message[1]);
                        }
                        if (message[0].equals(command[14])) {
                            productRegistration(message[1], message[2], message[3], message[4]);
                        }
                    }
                }
            } catch (IOException ex) {
                for (int i = 0; i < connections.size(); i++) {
                    if (connections.get(i) == connect) {
                        connections.remove(i);
                        break;
                    }
                }
                System.out.println("Пользователь отключился\n Количество пользоваталей: " + connections.size());
                try {
                    close();
                } catch (IOException ex1) {
                    Logger.getLogger(KursachServer.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(KursachServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void entrance(String log, String pass) throws SQLException, IOException {
            ResultSet res = postgreSQL.find("Authorization", "login", log);
            if (res.isBeforeFirst()) {
                res.next();
                if (res.getString("password").equals(pass)) {
                    send("0"); // 0 - Вход выполнен успешно
                } else {
                    send("1"); // 1 - Неверный пароль
                }
            } else {
                send("2"); // 2 - Неверный логин
            }
        }

        public void registration(String login, String password, String surname, String name, String patronymic, String typeOfWork) throws SQLException, IOException {
            String values = "('" + login + "', " + "'" + password + "', "
                    + "'" + surname + "', " + "'" + name + "', "
                    + "'" + patronymic + "', " + typeOfWork + ");";
            String variables = "(login, password, surname, name, patronymic, \"typeOfWork\")";
            ResultSet res = postgreSQL.find("Authorization", "login", login);
            if (res.isBeforeFirst()) {
                send("1"); // 2 - Логин занят
            } else {
                postgreSQL.insert("User", variables, values);
                send("0"); // 0 - Регистрация выполнена успешно
            }
        }

        public void sendSupplier(String[] Message) throws IOException, SQLException {
            ArrayList<String> strAdd = new ArrayList<>();
            ArrayList<String> strDel = new ArrayList<>();
            ArrayList<String> buf = new ArrayList<>();
            String supplier = "";
            ResultSet res = postgreSQL.find("Suppliers", "supplier");
            if (Message.length > 1) {
                boolean add = true;
                boolean del = true;
                while (res.next()) {
                    buf.add(res.getString("supplier"));
                }
                // Что добавить
                for (int j = 0; j < buf.size(); j++) {
                    for (int i = 1; i < Message.length; i++) {
                        if (buf.get(j).equals(Message[i])) {
                            add = false;
                        }
                    }
                    if (add) {
                        strAdd.add(buf.get(j));
                    }
                    add = true;
                }

                // Что удалить
                for (int i = 1; i < Message.length; i++) {
                    for (int j = 0; j < buf.size(); j++) {
                        if (buf.get(j).equals(Message[i])) {
                            del = false;
                        }
                    }
                    if (del) {
                        strDel.add(Message[i]);
                    }
                    del = true;
                }
            } else {
                while (res.next()) {
                    strAdd.add(res.getString("supplier"));
                }
            }
            if (strAdd.isEmpty()) {
                send("0"); // Поставщиков нет
            } else {
                supplier = strAdd.get(0);
                for (int i = 1; i < strAdd.size(); i++) {
                    supplier += "~" + strAdd.get(i);
                }
                send(supplier);
            }

            if (strDel.isEmpty()) {
                send("0"); // Удалять нечего
            } else {
                supplier = strDel.get(0);
                for (int i = 1; i < strDel.size(); i++) {
                    supplier += "~" + strDel.get(i);
                }
                send(supplier);
            }
        }

        public void addSupplier(String supplier) throws SQLException, IOException {
            ResultSet res = postgreSQL.find("Suppliers", "supplier", supplier);
            if (res.isBeforeFirst()) {
                send("0"); // Такой поставщик есть
            } else {
                String values = "('" + supplier + "');";
                String variables = "(supplier)";
                postgreSQL.insert("Suppliers", variables, values);
                send("1"); // Поставщик успешно добавлен
            }
        }

        public void addProduct(String newProductName, String newProductManufacturer, String newProductDescription) throws SQLException {
            String values = "('" + newProductName + "', '" + newProductDescription + "', '" + newProductManufacturer + "');";
            String variables = "(name, description, manufacturer)";
            postgreSQL.insert("Product", variables, values);
            ResultSet res = postgreSQL.find("Product", "name", newProductName);
            res.next();
            variables = "(product_id, number)";
            values = "('" + res.getString("product_id") + "', '0');";
            postgreSQL.insert("NumberOfProducts", variables, values);
        }

        public void delSupplier(String supplier) throws SQLException, IOException {
            postgreSQL.delete("Suppliers", "supplier", supplier);
        }

        public void editSupplier(String oldSupplier, String newSupplier) throws SQLException, IOException {
            postgreSQL.update("Suppliers", "supplier", oldSupplier, newSupplier);
        }

        public void sendProduct(String[] Message) throws IOException, SQLException {
            ArrayList<String> strAdd = new ArrayList<>();
            ArrayList<String> strDel = new ArrayList<>();
            ArrayList<String> buf = new ArrayList<>();
            String product = "";
            ResultSet res = postgreSQL.find("Product", "name");
            if (Message.length > 1) {
                boolean add = true;
                boolean del = true;
                while (res.next()) {
                    buf.add(res.getString("name"));
                }
                // Что добавить
                for (int j = 0; j < buf.size(); j++) {
                    for (int i = 1; i < Message.length; i++) {
                        if (buf.get(j).equals(Message[i])) {
                            add = false;
                        }
                    }
                    if (add) {
                        strAdd.add(buf.get(j));
                    }
                    add = true;
                }

                // Что удалить
                for (int i = 1; i < Message.length; i++) {
                    for (int j = 0; j < buf.size(); j++) {
                        if (buf.get(j).equals(Message[i])) {
                            del = false;
                        }
                    }
                    if (del) {
                        strDel.add(Message[i]);
                    }
                    del = true;
                }
            } else {
                while (res.next()) {
                    strAdd.add(res.getString("name"));
                }
            }
            if (strAdd.isEmpty()) {
                send("0"); // Поставщиков нет
            } else {
                product = strAdd.get(0);
                for (int i = 1; i < strAdd.size(); i++) {
                    product += "~" + strAdd.get(i);
                }
                send(product);
            }

            if (strDel.isEmpty()) {
                send("0"); // Удалять нечего
            } else {
                product = strDel.get(0);
                for (int i = 1; i < strDel.size(); i++) {
                    product += "~" + strDel.get(i);
                }
                send(product);
            }
        }

        public void getProductCode(String product) throws SQLException, IOException {
            ResultSet res = postgreSQL.find("Product", "name", product);
            res.next();
            send(res.getString("product_id"));
        }

        public void toChangeTheQuantityOfGoods(String name, String status, String howMuch) throws SQLException, IOException {
            ResultSet res = postgreSQL.find("Product", "name", name);
            res.next();
            res = postgreSQL.find("NumberOfProducts", "product_id", res.getString("product_id"));
            res.next();
            int number = Integer.parseInt(res.getString("number"));
            String oldNumber = res.getString("number");
            if ("1".equals(status)) {
                if ((number - Integer.parseInt(howMuch)) < 0) {
                    send("e"); // Ошибка: отрицательное значение
                } else {
                    number -= Integer.parseInt(howMuch);
                    postgreSQL.update("NumberOfProducts", "number", oldNumber, Integer.toString(number));
                    send(Integer.toString(number));
                }
            } else {
                number += Integer.parseInt(howMuch);
                postgreSQL.update("NumberOfProducts", "number", oldNumber, Integer.toString(number));
                send(Integer.toString(number));
            }
        }

        public void toMakeAnInventoryOfTheGoods(String name, String number) throws SQLException, IOException {
            ResultSet res = postgreSQL.find("Product", "name", name);
            res.next();
            res = postgreSQL.find("NumberOfProducts", "product_id", res.getString("product_id"));
            res.next();
            int count = Integer.parseInt(res.getString("number"));
            if (count == Integer.parseInt(number)) {
                send("0"); // Подсчитано верно
            } else {
                if (count > Integer.parseInt(number)) {
                    send("1" + "~" + Integer.toString(count - Integer.parseInt(number))); // Не хватает
                }
                if (count < Integer.parseInt(number)) {
                    send("2" + "~" + Integer.toString(Integer.parseInt(number) - count)); // Перебор
                }
            }
        }

        public void getNumber(String[] products) throws SQLException, IOException {
            String message;
            if (products.length > 1) {
                ResultSet res = postgreSQL.find("Product", "name", products[1]);
                res.next();
                res = postgreSQL.find("NumberOfProducts", "product_id", res.getString("product_id"));
                res.next();
                message = res.getString("number");
                for (int i = 2; i < products.length; i++) {
                    res = postgreSQL.find("Product", "name", products[i]);
                    res.next();
                    res = postgreSQL.find("NumberOfProducts", "product_id", res.getString("product_id"));
                    res.next();
                    message += "~" + res.getString("number");
                }
                send(message);
            } else {
                send("e"); // В базе нет товаров
            }
        }

        public void getProductInfo(String product_id) throws SQLException, IOException {
            ResultSet res = postgreSQL.find("Product", "product_id", product_id);
            String message;
            if (res.isBeforeFirst()) {
                res.next();
                message = "Наименование товара: " + res.getString("name")
                        + "\nОписание товара: " + res.getString("description")
                        + "\nПроизводитель: " + res.getString("manufacturer");
                send(message);
            } else {
                send("e"); // В базе нет товаров
            }
        }

        public void productRegistration(String supplier, String productName, String productCode, String number) throws SQLException {
            String variables = "(supplier, number_of_products, product_id)";
            String values = "('" + supplier + "', '" + number + "', '" + productCode + "');";
            postgreSQL.insert("Registered", variables, values);
            ResultSet res = postgreSQL.find("NumberOfProducts", "product_id", productCode);
            res.next();
            int count = Integer.parseInt(res.getString("number"));
            String oldNumber = res.getString("number");
            count += Integer.parseInt(number);
            postgreSQL.update("NumberOfProducts", "number", oldNumber, Integer.toString(count));
        }

        public String receive() throws IOException { // Получение данных с клиента
            String data = input.readUTF();
            return data;
        }

        public void send(String data) throws IOException { // Отправка данных на клиент
            output.writeUTF(data);
        }

        public void send(String[] data) throws IOException { // Отправка данных на клиент
            output.writeUTF(separatorGeneration(data));
            output.flush();
        }

        public void close() throws IOException {
            socket.close();
            input.close();
            output.close();
        }
    }
}
