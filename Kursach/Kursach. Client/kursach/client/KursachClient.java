/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kursach.client;

import GUI.AccountingOfGoodsUI;
import GUI.AdminUI;
import GUI.AuthorizationUI;
import GUI.DiagramProducts;
import GUI.RegistrationOfGoodsUI;
import static SeparatorGeneration.SeparatorGeneration.decoding;
import java.io.IOException;
import static java.lang.Thread.sleep;

/**
 *
 * @author Gagadok
 */
public class KursachClient {

    private static Connection server;
    private static final int port = 2525;
    private static final String address = "127.0.0.1";
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

    public static void main(String[] args) throws IOException, InterruptedException {
        server = new Connection(address, port);

        AuthorizationUI authorization = new AuthorizationUI();
        authorization.setVisible(true); // Отображение окна (по умолчанию оконо невидимо)
        authorization.setResizable(false); // Запрет изменения размеров окан
        authorization.setLocationRelativeTo(null); // Центрирование

        String message;

        while (true) {
            if (authorization.isClickedEnter()) {
                String[] dataAuthorization = {command[0], authorization.getLogin(),
                    authorization.getPassword()};

                server.send(dataAuthorization);
                authorization.setFalseClickedEnter();
                message = server.receive();
                if ("0".equals(message)) {
                    break;
                }
                if ("1".equals(message)) {
                    authorization.setLoginStatusDialog("Неверный пароль");
                }
                if ("2".equals(message)) {
                    authorization.setLoginStatusDialog("Неверный логин");
                }
            }

            if (authorization.isСlickedRegistration()) {
                String[] dataAuthorization = {command[1], authorization.getRegistrationLogin(),
                    authorization.getRegistrationPassword(),
                    authorization.getRegistrationSurname(),
                    authorization.getRegistrationName(),
                    authorization.getRegistrationPatronymic(),
                    String.valueOf(authorization.getRegistrationTypeOfWork())};

                server.send(dataAuthorization);
                authorization.setFalseСlickedRegistration();

                message = server.receive();
                if ("0".equals(message)) {
                    authorization.setVisibleRegistration(false);
                }
                if ("1".equals(message)) {
                    authorization.setRegistrationLogin();
                }
            }

            sleep(0);
        }

        sleep(0);
        authorization.setVisible(false);

        String[] dataAuthorization = {command[2], authorization.getLogin()};
        server.send(dataAuthorization);
        message = server.receive();
        if ("0".equals(message)) {
            admin();
        }
        if ("1".equals(message)) {
            accountingOfGoods();
        }
        if ("2".equals(message)) {
            registrationOfGoods();
        }

        System.out.println("Упс, конец");
        server.close();
    }

    public static void admin() throws InterruptedException, IOException {
        AdminUI adminUI = new AdminUI();
        adminUI.setVisible(true); // Отображение окна (по умолчанию оконо невидимо)
        adminUI.setResizable(false); // Запрет изменения размеров окан
        adminUI.setLocationRelativeTo(null); // Центрирование

        String message;

        while (true) {
            server.send(command[3] + "~" + adminUI.getSuppliersListModel());
            message = server.receive();
            if (!"0".equals(message)) {
                adminUI.setSuppliersListModel(decoding(message));
            }
            message = server.receive();
            if (!"0".equals(message)) {
                adminUI.delSuppliersListModel(decoding(message));
            }

            if (adminUI.isСlickedAddSuppliers()) {
                String[] dataAuthorization = {command[4], adminUI.getAddSupplier()};

                server.send(dataAuthorization);
                message = server.receive();
                if ("0".equals(message)) {
                    System.out.println("Такой поставщик есть");
                }
                if ("1".equals(message)) {
                    System.out.println("Поставщик успешно добавлен");
                }
                adminUI.setСlickedAddSuppliers();
            }

            if (adminUI.isСlickedDelSuppliers()) {
                String[] dataAuthorization = {command[5], adminUI.getDelSupplier()};

                server.send(dataAuthorization);
                adminUI.setСlickedDelSuppliers();
            }

            if (adminUI.isСlickedEditSuppliers()) {
                String[] dataAuthorization = {command[6], adminUI.getEditSupplier()};

                server.send(dataAuthorization);
                adminUI.setСlickedEditSuppliers();
            }

            if (adminUI.isСlickedAddProduct()) {
                String[] dataAuthorization = {command[7], adminUI.getAddProduct()};

                server.send(dataAuthorization);
                adminUI.setСlickedAddProduct();
            }
            sleep(0);
        }
    }

    public static void registrationOfGoods() throws IOException, InterruptedException {
        RegistrationOfGoodsUI registrationOfGoodsUI = new RegistrationOfGoodsUI();
        registrationOfGoodsUI.setVisible(true); // Отображение окна (по умолчанию оконо невидимо)
        registrationOfGoodsUI.setResizable(false); // Запрет изменения размеров окан
        registrationOfGoodsUI.setLocationRelativeTo(null); // Центрирование

        String message;

        while (true) {
            server.send(command[3] + "~" + registrationOfGoodsUI.getSuppliersComboBoxModel());
            message = server.receive();
            if (!"0".equals(message)) {
                registrationOfGoodsUI.setSuppliersComboBoxModel(decoding(message));
            }
            message = server.receive();
            if (!"0".equals(message)) {
                registrationOfGoodsUI.delSuppliersComboBoxModel(decoding(message));
            }

            server.send(command[8] + "~" + registrationOfGoodsUI.getProductNameComboBoxModel());
            message = server.receive();
            if (!"0".equals(message)) {
                registrationOfGoodsUI.setProductNameComboBoxModel(decoding(message));
            }
            message = server.receive();
            if (!"0".equals(message)) {
                registrationOfGoodsUI.delProductNameComboBoxModel(decoding(message));
            }

            server.send(command[9] + "~" + registrationOfGoodsUI.getProductName());
            message = server.receive();
            if (!"0".equals(message)) {
                registrationOfGoodsUI.setProductCode(message);
            }

            if (registrationOfGoodsUI.isClickedReadMore()) {
                String[] dataAuthorization = {command[13], registrationOfGoodsUI.getProductCode()};

                server.send(dataAuthorization);
                message = server.receive();
                if (!"e".equals(message)) {
                    registrationOfGoodsUI.setProductInformationDialog(message);
                }
                registrationOfGoodsUI.setClickedReadMore();
            }

            if (registrationOfGoodsUI.isClickedRegistration()) {
                String[] dataAuthorization = {command[14], registrationOfGoodsUI.getSupplier(),
                    registrationOfGoodsUI.getProductName(), registrationOfGoodsUI.getProductCode(),
                    registrationOfGoodsUI.getNumberOfProduct()};

                server.send(dataAuthorization);
                registrationOfGoodsUI.setOkDialog("Зарегестрировано", "Зарегестрировано");
                registrationOfGoodsUI.setClickedRegistration();
            }
            sleep(0);
        }
    }

    public static void accountingOfGoods() throws IOException, InterruptedException {
        AccountingOfGoodsUI accountingOfGoodsUI = new AccountingOfGoodsUI();
        accountingOfGoodsUI.setVisible(true); // Отображение окна (по умолчанию оконо невидимо)
        accountingOfGoodsUI.setResizable(false); // Запрет изменения размеров окан
        accountingOfGoodsUI.setLocationRelativeTo(null); // Центрирование

        String message;

        while (true) {
            server.send(command[8] + "~" + accountingOfGoodsUI.getProductNameComboBoxModel());
            message = server.receive();
            if (!"0".equals(message)) {
                accountingOfGoodsUI.setProductNameComboBoxModel(decoding(message));
            }
            message = server.receive();
            if (!"0".equals(message)) {
                accountingOfGoodsUI.delProductNameComboBoxModel(decoding(message));
            }

            if (accountingOfGoodsUI.isClickedChangeInTheNumber()) {
                String[] dataAuthorization = {command[10], accountingOfGoodsUI.getProductName(),
                    accountingOfGoodsUI.getRadioButton(), accountingOfGoodsUI.getHowMuch()};

                server.send(dataAuthorization);
                message = server.receive();
                if ("e".equals(message)) {
                    accountingOfGoodsUI.setOkDialog("Слишком много вычетается", "Введите меньше");
                } else {
                    accountingOfGoodsUI.setOkDialog("Изменение прошло успешно", "Стало: " + message);
                }
                accountingOfGoodsUI.setClickedChangeInTheNumber();
            }

            if (accountingOfGoodsUI.isClickedCheck()) {
                String[] dataAuthorization = {command[11], accountingOfGoodsUI.getProductName(),
                    accountingOfGoodsUI.getTheNumberOfProducts()};

                server.send(dataAuthorization);
                message = server.receive();
                String[] mess = decoding(message);
                if ("0".equals(mess[0])) {
                    accountingOfGoodsUI.setOkDialog("Подсчитано верно", "Подсчитано верно");
                } else {
                    if ("1".equals(mess[0])) {
                        accountingOfGoodsUI.setOkDialog("Подсчитано не верно", "Не хватает: " + mess[1]);
                    }
                    if ("2".equals(mess[0])) {
                        accountingOfGoodsUI.setOkDialog("Подсчитано не верно", "Перебор на: " + mess[1]);
                    }
                }
                accountingOfGoodsUI.setClickedCheck();
            }

            if (accountingOfGoodsUI.isClickedChart()) {
                String[] dataAuthorization = {command[12], accountingOfGoodsUI.getProductNameComboBoxModel()};

                server.send(dataAuthorization);
                String[] mess = decoding(server.receive());
                if (!"e".equals(mess[0])) {
                    String[] productName = decoding(accountingOfGoodsUI.getProductNameComboBoxModel());
                    DiagramProducts diagramProducts = new DiagramProducts(productName, mess);
                }
                accountingOfGoodsUI.setClickedChart();
            }

            sleep(0);
        }
    }
}
