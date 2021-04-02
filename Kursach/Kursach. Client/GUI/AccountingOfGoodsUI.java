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
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Gagadok
 */
public class AccountingOfGoodsUI extends JFrame {

    private static JLabel productNameLabel, changeLabel, howMuchLabel, checkLabel;
    private static Font font;
    private static JRadioButton add, subtract;
    private static JButton ok, check, chart;
    private static JTextField howMuchTextField, checkTextField;
    private static JComboBox productNameComboBox;
    private static DefaultComboBoxModel<String> productNameComboBoxModel;
    private static ButtonGroup changeButtonGroup;
    private static boolean clickedChangeInTheNumber, clickedCheck, clickedChart;
    private static OurActionListener ourActionListener;
    private static PlainDocument doc;

    public AccountingOfGoodsUI() {
        super("Учёт товаров");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Отключение менеджера размещения

        clickedChangeInTheNumber = false; // Нажата ли клавиша изменения количества товара?
        clickedCheck = false; // Нажата ли клавиша учёта товара?
        clickedChart = false; // Нажата ли клавиша вывода диаграмм товаров?

        ourActionListener = new OurActionListener();

        int wOwner = 250;
        int hOwner = 60;
        int xOwner = 450 - wOwner / 2;
        int yOwner = 50;

        int x = 20;
        int y = 20;
        int w = 250;
        int h = 30;

        font = new Font("TimesRoman", Font.BOLD, 16);

        // Наименование учётного товара
        productNameLabel = new JLabel("<html> <p align=\"center\">Наименование<br> учётного товара</p></html>");
        productNameLabel.setLocation(xOwner + 50, yOwner);
        productNameLabel.setSize(wOwner, hOwner);
        add(productNameLabel);
        productNameLabel.setFont(font);
        productNameComboBoxModel = new DefaultComboBoxModel<>();
        productNameComboBox = new JComboBox(productNameComboBoxModel);
        productNameComboBox.setLocation(xOwner, yOwner + hOwner + 10);
        productNameComboBox.setSize(wOwner, h);
        add(productNameComboBox);
        productNameComboBox.setFont(font);
        chart = new JButton("<html> <p align=\"center\">Вывести<br> диаграмму товаров</p></html>");
        chart.setLocation(xOwner + 20, 360 - hOwner);
        chart.setSize(wOwner - 40, hOwner);
        add(chart);
        chart.setFont(font);
        chart.addActionListener(ourActionListener);

        // Изменение количества товаров
        changeLabel = new JLabel("<html> <p align=\"center\">Изменение<br> количества товаров</p></html>");
        changeLabel.setLocation(x, y);
        changeLabel.setSize(w, hOwner);
        add(changeLabel);
        changeLabel.setFont(font);
        add = new JRadioButton("Прибавить");
        add.setLocation(x - 10, y + hOwner + 10);
        add.setSize(w, h);
        add(add);
        add.setFont(font);
        subtract = new JRadioButton("Убавить");
        subtract.setLocation(x - 10, y + hOwner + 20 + h);
        subtract.setSize(w, h);
        add(subtract);
        subtract.setFont(font);
        changeButtonGroup = new ButtonGroup();
        changeButtonGroup.add(add);
        changeButtonGroup.add(subtract);

        howMuchLabel = new JLabel("<html> <p align=\"center\">На сколько?</p></html>");
        howMuchLabel.setLocation(x + 60, y + hOwner + 40 + h * 2);
        howMuchLabel.setSize(w, h);
        add(howMuchLabel);
        howMuchLabel.setFont(font);
        howMuchTextField = new JTextField();
        howMuchTextField.setLocation(x - 10, y + hOwner + 50 + h * 3);
        howMuchTextField.setSize(w, h);
        add(howMuchTextField);
        howMuchTextField.setFont(font);
        doc = (PlainDocument) howMuchTextField.getDocument();
        doc.setDocumentFilter(new OnlyNumbers());
        ok = new JButton("Ок");
        ok.setLocation(x + 10, y + hOwner + 60 + h * 4);
        ok.setSize(w - 50, h);
        add(ok);
        ok.setFont(font);
        ok.addActionListener(ourActionListener);

        // Учёт количества товаров
        checkLabel = new JLabel("<html> <p align=\"center\">Учёт<br> количества товаров</p></html>");
        checkLabel.setLocation(900 - w + 20, y);
        checkLabel.setSize(w, hOwner);
        add(checkLabel);
        checkLabel.setFont(font);
        checkTextField = new JTextField();
        checkTextField.setLocation(900 - 20 - w, y + hOwner + 10);
        checkTextField.setSize(w, h);
        add(checkTextField);
        checkTextField.setFont(font);
        doc = (PlainDocument) checkTextField.getDocument();
        doc.setDocumentFilter(new OnlyNumbers());
        check = new JButton("Проверить");
        check.setLocation(900 - w - 10, y + hOwner + 20 + h);
        check.setSize(w - 20, h);
        add(check);
        check.setFont(font);
        check.addActionListener(ourActionListener);
    }

    public class OurActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((e.getSource() == ok) && ((add.isSelected()) || (subtract.isSelected())) &&
                    howMuchTextField.getText().trim().length() > 0){
                clickedChangeInTheNumber = true;
            }
            
            if ((e.getSource() == check) && checkTextField.getText().trim().length() > 0){
                clickedCheck = true;
            }
            
            if (e.getSource() == chart){
                clickedChart = true;
            }
        }
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

    public void setProductNameComboBoxModel(String[] message) {
        for (int i = 0; i < message.length; i++) {
            productNameComboBoxModel.addElement(message[i]);
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

    public boolean isClickedChangeInTheNumber() {
        return clickedChangeInTheNumber;
    }

    public void setClickedChangeInTheNumber() {
        clickedChangeInTheNumber = false;
    }

    public String getRadioButton() {
        if (add.isSelected()) {
            return "0"; // Добовление количества товаров
        } else {
            return "1"; // Списывание количества товаров
        }
    }

    public String getProductName() {
        return productNameComboBoxModel.getElementAt(productNameComboBox.getSelectedIndex());
    }

    public String getHowMuch() {
        return howMuchTextField.getText();
    }

    public void setOkDialog(String text, String Label) {
        OkDialog okDialog = null;
        if (okDialog == null) {
            okDialog = new OkDialog(AccountingOfGoodsUI.this, text, Label);
        }
        okDialog.setVisible(true);
        okDialog = null;
    }
    
    public boolean isClickedCheck(){
        return clickedCheck;
    }
    
    public void setClickedCheck(){
        clickedCheck = false;
    }
    
    public String getTheNumberOfProducts(){
        return checkTextField.getText();
    }
    
    public boolean isClickedChart(){
        return clickedChart;
    }
    
    public void setClickedChart(){
        clickedChart = false;
    }
}
