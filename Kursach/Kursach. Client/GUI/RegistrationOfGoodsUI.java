/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import filter.OnlyNumbers;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Gagadok
 */
public class RegistrationOfGoodsUI extends JFrame {

    private static JLabel suppliersLabel, productNameLabel, productCodeLabel, numberOfProductLabel;
    private static Font font;
    private static JButton ok, productInformation;
    private static JTextField productCodeTextField, numberOfProductTextField;
    private static JComboBox suppliersComboBox, productNameComboBox;
    private static DefaultComboBoxModel<String> suppliersComboBoxModel, productNameComboBoxModel;
    private static boolean clickedReadMore, clickedRegistration;
    private static OurActionListener ourActionListener;
    private static PlainDocument doc;

    public RegistrationOfGoodsUI() {
        super("Регистрация товаров");
        setSize(900, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Отключение менеджера размещения

        clickedReadMore = false; // Кнопка подробнее не нажата
        clickedRegistration = false; // Кнопка регистрации не нажата

        ourActionListener = new OurActionListener();

        int xSuppliersLabel = 20;
        int ySuppliersLabel = 20;
        int wSuppliersLabel = 230;
        int hSuppliersLabel = 30;

        int ofSet = 90;

        int wOk = 260;
        int hOk = 50;

        int wSize = 600;

        font = new Font("TimesRoman", Font.BOLD, 16);
        suppliersComboBoxModel = new DefaultComboBoxModel<>();
        productNameComboBoxModel = new DefaultComboBoxModel<>();

        // Поставщик
        suppliersLabel = new JLabel("Поставщик: ");
        suppliersLabel.setLocation(xSuppliersLabel, ySuppliersLabel);
        suppliersLabel.setSize(wSuppliersLabel, hSuppliersLabel);
        add(suppliersLabel);
        suppliersLabel.setFont(font);
        suppliersComboBox = new JComboBox(suppliersComboBoxModel);
        suppliersComboBox.setLocation(xSuppliersLabel + 10 + wSuppliersLabel, ySuppliersLabel);
        suppliersComboBox.setSize(wSize, hSuppliersLabel);
        add(suppliersComboBox);
        suppliersComboBox.setFont(font);

        // Наименование товара
        productNameLabel = new JLabel("Наименование товара: ");
        productNameLabel.setLocation(xSuppliersLabel, ySuppliersLabel + hSuppliersLabel + ofSet);
        productNameLabel.setSize(wSuppliersLabel, hSuppliersLabel);
        add(productNameLabel);
        productNameLabel.setFont(font);
        productNameComboBox = new JComboBox(productNameComboBoxModel);
        productNameComboBox.setLocation(xSuppliersLabel + 10 + wSuppliersLabel, ySuppliersLabel + hSuppliersLabel + ofSet);
        productNameComboBox.setSize(wSize, hSuppliersLabel);
        add(productNameComboBox);
        productNameComboBox.setFont(font);

        // Код товара
        productCodeLabel = new JLabel("Код товара: ");
        productCodeLabel.setLocation(xSuppliersLabel, ySuppliersLabel + hSuppliersLabel * 2 + ofSet * 2);
        productCodeLabel.setSize(wSuppliersLabel, hSuppliersLabel);
        add(productCodeLabel);
        productCodeLabel.setFont(font);
        productCodeTextField = new JTextField();
        productCodeTextField.setLocation(xSuppliersLabel + 10 + wSuppliersLabel, ySuppliersLabel + hSuppliersLabel * 2 + ofSet * 2);
        productCodeTextField.setSize(wSize / 2 + 150, hSuppliersLabel);
        add(productCodeTextField);
        productCodeTextField.setFont(font);
        productCodeTextField.setEditable(false);
        productInformation = new JButton("Подробнее");
        productInformation.setLocation(xSuppliersLabel + 20 + wSuppliersLabel + wSize / 2 + 150, ySuppliersLabel + hSuppliersLabel * 2 + ofSet * 2);
        productInformation.setSize(wSize / 2 - 160, hSuppliersLabel);
        add(productInformation);
        productInformation.setFont(font);
        productInformation.addActionListener(ourActionListener);

        //Количество едениц товара
        numberOfProductLabel = new JLabel("Количество едениц товара: ");
        numberOfProductLabel.setLocation(xSuppliersLabel, ySuppliersLabel + hSuppliersLabel * 3 + ofSet * 3);
        numberOfProductLabel.setSize(wSuppliersLabel, hSuppliersLabel);
        add(numberOfProductLabel);
        numberOfProductLabel.setFont(font);
        numberOfProductTextField = new JTextField();
        numberOfProductTextField.setLocation(xSuppliersLabel + 10 + wSuppliersLabel, ySuppliersLabel + hSuppliersLabel * 3 + ofSet * 3);
        numberOfProductTextField.setSize(wSize, hSuppliersLabel);
        add(numberOfProductTextField);
        numberOfProductTextField.setFont(font);
        doc = (PlainDocument) numberOfProductTextField.getDocument();
        doc.setDocumentFilter(new OnlyNumbers());

        // Ок
        ok = new JButton("Зарегестрировать");
        ok.setLocation(450 - wOk / 2, ySuppliersLabel + hSuppliersLabel * 4 + ofSet * 3 + 50);
        ok.setSize(wOk, hOk);
        add(ok);
        ok.setFont(new Font("TimesRoman", Font.BOLD, 24));
        ok.addActionListener(ourActionListener);
    }

    public class OurActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((e.getSource() == productInformation) && (productCodeTextField.getText().trim().length() > 0)) {
                clickedReadMore = true;
            }
            if ((e.getSource() == ok) && (numberOfProductTextField.getText().trim().length() > 0)) {
                clickedRegistration = true;
            }
        }
    }

    class productInformationDialog extends JDialog {

        JButton ok;
        Font ft;
        JTextArea productInformation;
        OkActionListener okActionListener;

        public productInformationDialog(JFrame owner, String text) {
            super(owner, "Информация о товаре", true);
            setSize(500, 300);
            setResizable(false); // Запрет изменения размеров окан
            setLocationRelativeTo(null); // Центрирование

            setLayout(null);

            okActionListener = new OkActionListener();

            ft = new Font("TimesRoman", Font.ITALIC, 14);

            productInformation = new JTextArea();
            productInformation.setFont(ft);
            productInformation.setLocation(20, 20);
            productInformation.setSize(440, 200);
            add(productInformation);
            productInformation.setText(text);
            productInformation.setEditable(false);
            productInformation.setLineWrap(true);
            productInformation.setWrapStyleWord(true);

            ok = new JButton("Ok");
            ok.setFont(ft);
            ok.setLocation(150, 240);
            ok.setSize(200, 30);
            ok.addActionListener(okActionListener);
            add(ok);
        }

        public class OkActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ok) {
                    setVisible(false);
                }
            }
        }
    }

    public String getSuppliersComboBoxModel() {
        ArrayList<String> str = new ArrayList<>();
        for (int i = 0; i < suppliersComboBoxModel.getSize(); i++) {
            str.add(suppliersComboBoxModel.getElementAt(i));
        }
        String st = "";
        if (!str.isEmpty()) {
            st = str.get(0);
        }
        for (int i = 1; i < str.size(); i++) {
            st += "~" + str.get(i);
        }
        return st;
    }

    public String getProductNameComboBoxModel() {
        ArrayList<String> str = new ArrayList<>();
        for (int i = 0; i < productNameComboBoxModel.getSize(); i++) {
            str.add(productNameComboBoxModel.getElementAt(i));
        }
        String st = "";
        if (!str.isEmpty()) {
            st = str.get(0);
        }
        for (int i = 1; i < str.size(); i++) {
            st += "~" + str.get(i);
        }
        return st;
    }

    public void setSuppliersComboBoxModel(String[] message) {
        for (int i = 0; i < message.length; i++) {
            suppliersComboBoxModel.addElement(message[i]);
        }
    }

    public void setProductNameComboBoxModel(String[] message) {
        for (int i = 0; i < message.length; i++) {
            productNameComboBoxModel.addElement(message[i]);
        }
    }

    public void delSuppliersComboBoxModel(String[] message) {
        for (int i = 0; i < message.length; i++) {
            for (int j = 0; j < suppliersComboBoxModel.getSize(); j++) {
                if (message[i].equals(suppliersComboBoxModel.getElementAt(j))) {
                    suppliersComboBoxModel.removeElementAt(j);
                }
            }
        }
    }

    public void delProductNameComboBoxModel(String[] message) {
        for (int i = 0; i < message.length; i++) {
            for (int j = 0; j < productNameComboBoxModel.getSize(); j++) {
                if (message[i].equals(productNameComboBoxModel.getElementAt(j))) {
                    productNameComboBoxModel.removeElementAt(j);
                }
            }
        }
    }

    public String getProductName() {
        return productNameComboBoxModel.getElementAt(productNameComboBox.getSelectedIndex());
    }

    public void setProductCode(String code) {
        productCodeTextField.setText(code);
    }

    public boolean isClickedReadMore() {
        return clickedReadMore;
    }

    public void setClickedReadMore() {
        clickedReadMore = false;
    }

    public String getProductCode() {
        return productCodeTextField.getText();
    }

    public void setProductInformationDialog(String text) {
        productInformationDialog ProductInformationDialog = null;
        if (ProductInformationDialog == null) {
            ProductInformationDialog = new productInformationDialog(RegistrationOfGoodsUI.this, text);
        }
        ProductInformationDialog.setVisible(true);
        ProductInformationDialog = null;
    }
    
    public String getSupplier(){
        return suppliersComboBoxModel.getElementAt(suppliersComboBox.getSelectedIndex());
    }
    
    public String getNumberOfProduct(){
        return numberOfProductTextField.getText();
    }
    
    public boolean isClickedRegistration(){
        return clickedRegistration;
    }
    
    public void setClickedRegistration(){
        clickedRegistration = false;
    }
    
    public void setOkDialog(String text, String Label) {
        OkDialog okDialog = null;
        if (okDialog == null) {
            okDialog = new OkDialog(RegistrationOfGoodsUI.this, text, Label);
        }
        okDialog.setVisible(true);
        okDialog = null;
    }
}
