/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Gagadok
 */
public class AdminUI extends JFrame {

    private static JLabel suppliersLabel, enterTextLabel, addProductLabel,
            productNameLabel, descriptionLabel, manufacturerLabel;
    private static JList<String> suppliersList;
    private static JTextField suppliersTextField,
            productNameTextField, manufacturerTextField;
    private static JTextArea descriptionTextArea;
    private static JButton addSuppliers, delSuppliers, addProduct, editUsers, delProduct;
    private static DefaultListModel<String> suppliersListModel;
    private static Font font;
    private static OurActionListener actionListener;
    private static MouseClicked mouseListener;
    private static boolean clickedAddSuppliers, clickedAddProduct, clickedDelSuppliers, clickedEditSuppliers;
    private static String lastAddSupplier, delSupplier, oldSupplier, newSupplier,
            newProductName, newProductManufacturer, newProductDescription;

    public AdminUI() {
        super("Админка");

        setSize(900, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Отключение менеджера размещения

        clickedAddSuppliers = false; // Нажата ли клавиша добовления поставщика?
        clickedDelSuppliers = false; // Нажата ли клавиша удаления поставщика?
        clickedEditSuppliers = false; // Нажата ли клавиша редактирования поставщика?
        clickedAddProduct = false; // Нажата ли клавиша добовления товара?

        int xSuppliersList = 30;
        int ySuppliersList = 40;
        int wSuppliersList = 240;
        int hSuppliersList = 400;

        int hLabelAndButton = 20;

        int wAddProduct = 350;
        int wAddProductLabels = 200;
        int hDescriptionTextArea = 200;

        font = new Font("TimesRoman", Font.BOLD, 16);

        // Список поставщиков
        suppliersLabel = new JLabel("Добовление поставщиков");
        suppliersLabel.setLocation(xSuppliersList + 10, ySuppliersList - 30);
        suppliersLabel.setSize(wSuppliersList, hLabelAndButton);
        add(suppliersLabel);
        suppliersLabel.setFont(font);
        suppliersListModel = new DefaultListModel<>();
        suppliersList = new JList<>(suppliersListModel);
        suppliersList.setLocation(xSuppliersList, ySuppliersList);
        suppliersList.setSize(wSuppliersList, hSuppliersList);
        add(suppliersList);
        suppliersList.setFont(font);

        // Кнопки добавления и удаления инфы в список поставщиков
        addSuppliers = new JButton("Добавить");
        addSuppliers.setLocation(xSuppliersList, ySuppliersList + hSuppliersList + 10);
        addSuppliers.setSize(wSuppliersList / 2 - 5, hLabelAndButton + 5);
        add(addSuppliers);
        addSuppliers.setFont(font);
        delSuppliers = new JButton("Удалить");
        delSuppliers.setLocation(xSuppliersList + wSuppliersList / 2 + 5, ySuppliersList + hSuppliersList + 10);
        delSuppliers.setSize(wSuppliersList / 2 - 5, hLabelAndButton + 5);
        add(delSuppliers);
        delSuppliers.setFont(font);

        // Поле для ввода новых поставщиков
        enterTextLabel = new JLabel("Введите поставщика");
        enterTextLabel.setLocation(xSuppliersList + 30, ySuppliersList + hSuppliersList + 20 + hLabelAndButton);
        enterTextLabel.setSize(wSuppliersList - 20, hLabelAndButton);
        add(enterTextLabel);
        enterTextLabel.setFont(font);
        suppliersTextField = new JTextField();
        suppliersTextField.setLocation(xSuppliersList, ySuppliersList + hSuppliersList + 25 + hLabelAndButton * 2);
        suppliersTextField.setSize(wSuppliersList, hLabelAndButton + 10);
        add(suppliersTextField);
        suppliersTextField.setFont(font);

        // Добавление товара
        addProductLabel = new JLabel("Добовление товара");
        addProductLabel.setLocation(xSuppliersList + wSuppliersList + 290, ySuppliersList - 30);
        addProductLabel.setSize(wAddProduct, hLabelAndButton);
        add(addProductLabel);
        addProductLabel.setFont(font);
        productNameLabel = new JLabel("Наименование товара: ");
        productNameLabel.setLocation(xSuppliersList + wSuppliersList + 10, ySuppliersList);
        productNameLabel.setSize(wAddProductLabels, hLabelAndButton);
        add(productNameLabel);
        productNameLabel.setFont(font);
        productNameTextField = new JTextField();
        productNameTextField.setLocation(xSuppliersList + wSuppliersList + 20 + wAddProductLabels, ySuppliersList - 5);
        productNameTextField.setSize(wAddProduct, hLabelAndButton + 10);
        add(productNameTextField);
        productNameTextField.setFont(font);
        descriptionLabel = new JLabel("Описание товара: ");
        descriptionLabel.setLocation(xSuppliersList + wSuppliersList + 10, hDescriptionTextArea - 40);
        descriptionLabel.setSize(wAddProductLabels, hLabelAndButton);
        add(descriptionLabel);
        descriptionLabel.setFont(font);
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setLocation(xSuppliersList + wSuppliersList + 20 + wAddProductLabels, ySuppliersList + hLabelAndButton + 15);
        descriptionTextArea.setSize(wAddProduct, hDescriptionTextArea);
        add(descriptionTextArea);
        descriptionTextArea.setFont(font);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        manufacturerLabel = new JLabel("Производитель товара:");
        manufacturerLabel.setLocation(xSuppliersList + wSuppliersList + 10, ySuppliersList + hLabelAndButton + 30 + hDescriptionTextArea);
        manufacturerLabel.setSize(wAddProductLabels, hLabelAndButton);
        add(manufacturerLabel);
        manufacturerLabel.setFont(font);
        manufacturerTextField = new JTextField();
        manufacturerTextField.setLocation(xSuppliersList + wSuppliersList + 20 + wAddProductLabels, ySuppliersList + hLabelAndButton + 25 + hDescriptionTextArea);
        manufacturerTextField.setSize(wAddProduct, hLabelAndButton + 10);
        add(manufacturerTextField);
        manufacturerTextField.setFont(font);
        addProduct = new JButton("Добавить");
        addProduct.setLocation(xSuppliersList + wSuppliersList + 290, ySuppliersList + hLabelAndButton * 2 + 45 + hDescriptionTextArea);
        addProduct.setSize(wAddProduct / 2 + 50, hLabelAndButton * 2);
        add(addProduct);
        addProduct.setFont(font);

        // Удаление товара
        delProduct = new JButton("Удаление товара");
        delProduct.setLocation(xSuppliersList + wSuppliersList + 10 + wAddProductLabels / 2, ySuppliersList + hLabelAndButton * 4 + 85 + hDescriptionTextArea);
        delProduct.setSize(wAddProductLabels + 100, hLabelAndButton * 2);
        //add(delProduct);
        delProduct.setFont(font);

        // Редактирование пользователей
        editUsers = new JButton("Редактирование пользователей");
        editUsers.setLocation(xSuppliersList + wSuppliersList + 10 + wAddProductLabels / 2, ySuppliersList + hLabelAndButton * 6 + 100 + hDescriptionTextArea);
        editUsers.setSize(wAddProductLabels + 100, hLabelAndButton * 2);
        //add(editUsers);
        editUsers.setFont(font);

        // Генераторы событий
        actionListener = new OurActionListener();
        addSuppliers.addActionListener(actionListener);
        delSuppliers.addActionListener(actionListener);
        addProduct.addActionListener(actionListener);
        editUsers.addActionListener(actionListener);
        delProduct.addActionListener(actionListener);

        mouseListener = new MouseClicked();
        suppliersList.addMouseListener(mouseListener);
    }

    public class OurActionListener implements ActionListener {

        OkDialog okDialog;
        DelProductDialog delProductDialog;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addSuppliers) {
                if (suppliersTextField.getText().trim().length() > 0) {
                    lastAddSupplier = suppliersTextField.getText();
                    suppliersListModel.addElement(lastAddSupplier);
                    suppliersTextField.setText("");
                    clickedAddSuppliers = true;
                }
            }

            if (e.getSource() == delSuppliers) {
                int selectedIndex = suppliersList.getSelectedIndex();
                if (selectedIndex != -1) {
                    delSupplier = suppliersListModel.get(selectedIndex);
                    suppliersListModel.remove(selectedIndex);
                    clickedDelSuppliers = true;
                }
            }

            if ((e.getSource() == addProduct) && (productNameTextField.getText().trim().length() > 0)
                    && (manufacturerTextField.getText().trim().length() > 0)
                    && (descriptionTextArea.getText().trim().length() > 0)) {
                if (okDialog == null) {
                    okDialog = new OkDialog(AdminUI.this, "Продукт добавлен", "Продукт добавлен");
                }
                okDialog.setVisible(true);
                okDialog = null;
                newProductName = productNameTextField.getText();
                newProductManufacturer = manufacturerTextField.getText();
                newProductDescription = descriptionTextArea.getText();
                clickedAddProduct = true;
            } else if (e.getSource() == addProduct) {
                if (okDialog == null) {
                    okDialog = new OkDialog(AdminUI.this, "Продукт не добавлен", "Поля не заполнены");
                }
                okDialog.setVisible(true);
                okDialog = null;
            }

            if (e.getSource() == delProduct) {
                if (delProductDialog == null) {
                    delProductDialog = new DelProductDialog(AdminUI.this);
                }
                delProductDialog.setVisible(true);
                delProductDialog = null;
            }
        }
    }

    public class MouseClicked implements MouseListener {

        AboutDialog dialog;

        @Override
        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && e.getSource() == suppliersList) {
                int index = suppliersList.locationToIndex(e.getPoint());
                if (dialog == null) {
                    dialog = new AboutDialog(AdminUI.this, index, suppliersList);
                }
                dialog.setVisible(true);
                dialog = null;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    class AboutDialog extends JDialog {

        JLabel label;
        JTextField textField;
        JButton ok;
        Font ft;
        DialogActionListener dialogActionListener;
        int index;
        JList<String> list;

        public AboutDialog(JFrame owner, int index, JList list) {
            super(owner, "Редактирование элемента", true);
            setSize(500, 300);
            setResizable(false); // Запрет изменения размеров окан
            setLocationRelativeTo(null); // Центрирование
            this.index = index;
            this.list = list;

            setLayout(null);

            ft = new Font("TimesRoman", Font.ITALIC, 30);

            dialogActionListener = new DialogActionListener();

            label = new JLabel("Введите новый текст");
            label.setFont(ft);
            label.setLocation(85, 20);
            label.setSize(350, 50);
            add(label);

            textField = new JTextField();
            textField.setFont(ft);
            textField.setLocation(100, 100);
            textField.setSize(300, 50);
            add(textField);

            ok = new JButton("Ok");
            ok.setFont(ft);
            ok.setLocation(150, 200);
            ok.setSize(200, 50);
            ok.addActionListener(dialogActionListener);
            add(ok);
        }

        public class DialogActionListener implements ActionListener {

            DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ok && index != -1) {
                    if (textField.getText().trim().length() > 0) {
                        oldSupplier = model.get(index);
                        newSupplier = textField.getText();
                        model.set(index, newSupplier);
                        textField.setText("");
                        clickedEditSuppliers = true;
                    }
                }
                setVisible(false);
            }
        }
    }

    class DelProductDialog extends JDialog {

        Font ft;
        JLabel delLabel, delProductNameLabel, delDescriptionLabel, delManufacturerLabel;
        JTextField delManufacturerTextField;
        JTextArea delDescriptionTextArea;
        JButton delProd;
        JComboBox delPoductNameComboBox;
        DefaultComboBoxModel<String> productNameComboBoxModel;

        public DelProductDialog(JFrame owner) {
            super(owner, "Удаление товара", true);
            int xWindow = 700;
            int yWindow = 600;
            setSize(xWindow, yWindow);
            setResizable(false); // Запрет изменения размеров окан
            setLocationRelativeTo(null); // Центрирование

            setLayout(null);

            int wOwner = 200;
            int hOwner = 30;
            int xOwner = xWindow / 2 - wOwner / 2;
            int yOwner = 20;

            int x = 10;

            ft = new Font("TimesRoman", Font.BOLD, 30);

            delLabel = new JLabel("Удаление товара");
            delLabel.setLocation(xOwner, yOwner);
            delLabel.setSize(wOwner, hOwner);
            add(delLabel);
            delLabel.setFont(font);
            delProductNameLabel = new JLabel("Наименование товара: ");
            delProductNameLabel.setLocation(x, yOwner + hOwner + 10);
            delProductNameLabel.setSize(wOwner, hOwner);
            add(delProductNameLabel);
            delProductNameLabel.setFont(font);
            productNameComboBoxModel = new DefaultComboBoxModel<>();
            delPoductNameComboBox = new JComboBox(productNameComboBoxModel);
            delPoductNameComboBox.setLocation(x + wOwner + 10, yOwner + hOwner + 10);
            delPoductNameComboBox.setSize(xWindow - x - wOwner - 30, hOwner);
            add(delPoductNameComboBox);
            delPoductNameComboBox.setFont(font);
            delDescriptionLabel = new JLabel("Описание товара: ");
            delDescriptionLabel.setLocation(x, yOwner + hOwner * 2 + 20);
            delDescriptionLabel.setSize(wOwner, hOwner);
            add(delDescriptionLabel);
            delDescriptionLabel.setFont(font);
            delDescriptionTextArea = new JTextArea();
            delDescriptionTextArea.setLocation(x + wOwner + 10, yOwner + hOwner * 2 + 20);
            delDescriptionTextArea.setSize(xWindow - x - wOwner - 30, hOwner * 8);
            add(delDescriptionTextArea);
            delDescriptionTextArea.setFont(font);
            delDescriptionTextArea.setLineWrap(true);
            delDescriptionTextArea.setWrapStyleWord(true);
            delManufacturerLabel = new JLabel("Производитель товара:");
            delManufacturerLabel.setLocation(x, yOwner + hOwner * 10 + 30);
            delManufacturerLabel.setSize(wOwner, hOwner);
            add(delManufacturerLabel);
            delManufacturerLabel.setFont(font);
            delManufacturerTextField = new JTextField();
            delManufacturerTextField.setLocation(x + wOwner + 10, yOwner + hOwner * 10 + 30);
            delManufacturerTextField.setSize(xWindow - x - wOwner - 30, hOwner);
            add(delManufacturerTextField);
            delManufacturerTextField.setFont(font);
            delProd = new JButton("Удалить");
            delProd.setLocation(xOwner, yOwner + hOwner * 11 + 40);
            delProd.setSize(wOwner, hOwner);
            add(delProd);
            delProd.setFont(font);
            delProd.addActionListener(actionListener);
        }

        public class DialogActionListener implements ActionListener {

            OkDialog okDialog;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == delProd) {
                    int selectedIndex = delPoductNameComboBox.getSelectedIndex();
                    if (selectedIndex != -1) {
                        productNameComboBoxModel.removeElementAt(selectedIndex);
                        clickedDelSuppliers = true;
                    }
                }
            }
        }
    }

    public boolean isСlickedAddSuppliers() {
        return clickedAddSuppliers;
    }

    public boolean isСlickedAddProduct() {
        return clickedAddProduct;
    }

    public boolean isСlickedDelSuppliers() {
        return clickedDelSuppliers;
    }

    public void setСlickedAddSuppliers() {
        clickedAddSuppliers = false;
    }

    public void setСlickedAddProduct() {
        clickedAddProduct = false;
    }

    public String getAddProduct() {
        String str = newProductName + "~" + newProductManufacturer + "~" + newProductDescription;
        return str;
    }

    public void setСlickedDelSuppliers() {
        clickedDelSuppliers = false;
    }

    public String getSuppliersListModel() {
        ArrayList<String> str = new ArrayList<>();
        for (int i = 0; i < suppliersListModel.size(); i++) {
            str.add(suppliersListModel.get(i));
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

    public void setSuppliersListModel(String[] message) {
        for (int i = 0; i < message.length; i++) {
            suppliersListModel.addElement(message[i]);
        }
    }

    public String getAddSupplier() {
        return lastAddSupplier;
    }

    public String getDelSupplier() {
        return delSupplier;
    }

    public void delSuppliersListModel(String[] message) {
        for (int i = 0; i < message.length; i++) {
            for (int j = 0; j < suppliersListModel.getSize(); j++) {
                if (message[i].equals(suppliersListModel.get(j))) {
                    suppliersListModel.remove(j);
                }
            }
        }
    }

    public boolean isСlickedEditSuppliers() {
        return clickedEditSuppliers;
    }

    public void setСlickedEditSuppliers() {
        clickedEditSuppliers = false;
    }

    public String getEditSupplier() {
        String str = oldSupplier + "~" + newSupplier;
        return str;
    }
}
