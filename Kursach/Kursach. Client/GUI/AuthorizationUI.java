/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Gagadok
 */
public class AuthorizationUI extends JFrame {

    private static JTextField login;
    private static JPasswordField password;
    private static JButton enter, registration;
    private static OurFocusListener focusListener;
    private static String loginHintString, passwordHintString, userLogin;
    private static char[] userPassword;
    private static Font font;
    private static OurActionListener actionListener;
    private static AboutDialog dialog;
    private static LoginStatusDialog loginStatusDialog;
    private static boolean clickedEnter;
    private static boolean clickedRegistration;
    private static String registrationLogin, registrationPassword,
            registrationSurname, registrationName, registrationPatronymic;
    private static int registrationTypeOfWork;

    public AuthorizationUI() {
        super("Авторизация");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Отключение менеджера размещения

        clickedEnter = false; // Нажата ли клавиша ОК в окне входа?
        clickedRegistration = false; // Нажата ли клавиша ОК в окне регистрации?

        loginStatusDialog = new LoginStatusDialog(AuthorizationUI.this);

        font = new Font("TimesRoman", Font.BOLD, 20);

        loginHintString = "Логин";
        passwordHintString = "Пароль";

        focusListener = new OurFocusListener();
        actionListener = new OurActionListener();

        // Поле с логином
        login = new JTextField();
        login.setLocation(50, 20);
        login.setSize(300, 50);
        login.setFont(font);
        add(login);
        login.addFocusListener(focusListener);

        // Поле с паролем
        password = new JPasswordField();
        password.setLocation(50, 90);
        password.setSize(300, 50);
        password.setFont(font);
        add(password);
        password.setEchoChar((char) 0);
        password.setText(passwordHintString);
        password.addFocusListener(focusListener);

        // Кнопка входа
        enter = new JButton("Вход");
        enter.setLocation(70, 160);
        enter.setSize(260, 50);
        enter.setFont(font);
        add(enter);
        enter.addActionListener(actionListener);

        // Кнопка регистрации
        registration = new JButton("Регистрация");
        registration.setLocation(100, 230);
        registration.setSize(200, 30);
        add(registration);
        registration.addActionListener(actionListener);
    }

    private boolean confirmationForHint(char[] pass) {
        char[] passHintString = passwordHintString.toCharArray();
        boolean confirmation = true;
        if (pass.length == passHintString.length) {
            for (int i = 0; i < pass.length; i++) {
                if (passHintString[i] != pass[i]) {
                    confirmation = false;
                    break;
                }
            }
        } else {
            confirmation = false;
        }
        return confirmation;
    }

    public class OurFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            if ((login.getText().equals(loginHintString)) && (e.getSource() == login)) {
                login.setText("");
            }

            if (e.getSource() == password) {
                char[] pass = password.getPassword();
                if (confirmationForHint(pass)) {
                    password.setText("");
                    password.setEchoChar('☻');
                }
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if ((login.getText().isEmpty()) && (e.getSource() == login)) {
                login.setText(loginHintString);
            }

            if ((password.getPassword().length == 0) && (e.getSource() == password)) {
                password.setEchoChar((char) 0);
                password.setText(passwordHintString);
            }
        }
    }

    public class OurActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((e.getSource() == enter) && !(login.getText().equals(loginHintString)) && !(confirmationForHint(password.getPassword()))) {
                userLogin = login.getText();
                userPassword = password.getPassword();

                clickedEnter = true;
            }

            if (e.getSource() == registration) {
                if (dialog == null) {
                    dialog = new AboutDialog(AuthorizationUI.this);
                }
                dialog.setVisible(true);
                dialog = null;
            }
        }
    }

    class AboutDialog extends JDialog {

        Font ft;
        JTextField login, password, repeatedPassword, surname, name, patronymic;
        JLabel loginLabel, passwordLabel, repeatedPasswordLabel, surnameLabel, nameLabel, patronymicLabel, typeOfWorkLabel;
        JButton ok;
        JComboBox typeOfWork;
        OurActionListenerDialog ourActionListenerDialog;
        int labelWidth = 200; // Ширина JLabel
        int labelHeight = 30; // Высота JLabel и JTextField
        int textFieldWidth = 650; // Ширина JTextField
        int xLabel = 20; // Позиция х JLabel
        int xTextField = 230; // Позиция х JTextField

        public AboutDialog(JFrame owner) {
            super(owner, "Регистрация нового пользователя", true);
            setSize(900, 580);
            setResizable(false); // Запрет изменения размеров окан
            setLocationRelativeTo(null); // Центрирование
            setLayout(null);

            ft = new Font("TimesRoman", Font.BOLD, 15);

            ourActionListenerDialog = new OurActionListenerDialog();

            // Новый логин
            loginLabel = new JLabel("Логин: ");
            loginLabel.setLocation(xLabel, 20);
            loginLabel.setSize(labelWidth, labelHeight);
            loginLabel.setFont(font);
            add(loginLabel);

            login = new JTextField();
            login.setLocation(xTextField, 20);
            login.setSize(textFieldWidth, labelHeight);
            login.setFont(font);
            add(login);

            // Новый пароль
            passwordLabel = new JLabel("Пароль: ");
            passwordLabel.setLocation(xLabel, 70);
            passwordLabel.setSize(labelWidth, labelHeight);
            passwordLabel.setFont(font);
            add(passwordLabel);

            password = new JTextField();
            password.setLocation(xTextField, 70);
            password.setSize(textFieldWidth, labelHeight);
            password.setFont(font);
            add(password);

            // Повторение нового пароля
            repeatedPasswordLabel = new JLabel("Повторите пароль: ");
            repeatedPasswordLabel.setLocation(xLabel, 120);
            repeatedPasswordLabel.setSize(labelWidth, labelHeight);
            repeatedPasswordLabel.setFont(font);
            add(repeatedPasswordLabel);

            repeatedPassword = new JTextField();
            repeatedPassword.setLocation(xTextField, 120);
            repeatedPassword.setSize(textFieldWidth, labelHeight);
            repeatedPassword.setFont(font);
            add(repeatedPassword);

            // Новая фамилиля
            surnameLabel = new JLabel("Фамилия: ");
            surnameLabel.setLocation(xLabel, 170);
            surnameLabel.setSize(labelWidth, labelHeight);
            surnameLabel.setFont(font);
            add(surnameLabel);

            surname = new JTextField();
            surname.setLocation(xTextField, 170);
            surname.setSize(textFieldWidth, labelHeight);
            surname.setFont(font);
            add(surname);

            // Новое имя
            nameLabel = new JLabel("Имя: ");
            nameLabel.setLocation(xLabel, 220);
            nameLabel.setSize(labelWidth, labelHeight);
            nameLabel.setFont(font);
            add(nameLabel);

            name = new JTextField();
            name.setLocation(xTextField, 220);
            name.setSize(textFieldWidth, labelHeight);
            name.setFont(font);
            add(name);

            // Новое отчество
            patronymicLabel = new JLabel("Отчество: ");
            patronymicLabel.setLocation(xLabel, 270);
            patronymicLabel.setSize(labelWidth, labelHeight);
            patronymicLabel.setFont(font);
            add(patronymicLabel);

            patronymic = new JTextField();
            patronymic.setLocation(xTextField, 270);
            patronymic.setSize(textFieldWidth, labelHeight);
            patronymic.setFont(font);
            add(patronymic);

            // Тип работы
            typeOfWorkLabel = new JLabel("Тип работы: ");
            typeOfWorkLabel.setLocation(xLabel, 320);
            typeOfWorkLabel.setSize(labelWidth, labelHeight);
            typeOfWorkLabel.setFont(font);
            add(typeOfWorkLabel);

            String[] items = {
                "Учёт товаров",
                "Регистрация поступления товаров",};
            typeOfWork = new JComboBox(items);
            typeOfWork.setLocation(xTextField, 320);
            typeOfWork.setSize(textFieldWidth, labelHeight);
            typeOfWork.setFont(font);
            add(typeOfWork);

            // Кнопка ОК
            ok = new JButton("OK");
            ok.setLocation(50, 460);
            ok.setSize(800, 70);
            ok.setFont(new Font("Areal", Font.BOLD, 30));
            add(ok);
            ok.addActionListener(ourActionListenerDialog);
        }

        public class OurActionListenerDialog implements ActionListener {

            ConfirmDialog dialog;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ok) {
                    if (dialog == null) {
                        dialog = new ConfirmDialog(AboutDialog.this);
                    }
                    dialog.setVisible(true);
                    dialog = null;
                }
            }
        }

        public String getLogin() {
            return login.getText();
        }

        public String getPassword() {
            return password.getText();
        }

        public String getRepeatedPassword() {
            return repeatedPassword.getText();
        }

        public String getSurname() {
            return surname.getText();
        }

        public String getName() {
            return name.getText();
        }

        public String getPatronymic() {
            return patronymic.getText();
        }
    }

    class ConfirmDialog extends JDialog {

        JLabel textLabel;
        JButton ok, no;
        OurActionListenerDialog ourActionListenerDialog;
        AboutDialog owner;

        private ConfirmDialog(AboutDialog Owner) {
            super(Owner, "Подтверждение регестрации", true);
            setSize(250, 130);
            setResizable(false); // Запрет изменения размеров окан
            setLocationRelativeTo(null); // Центрирование
            setLayout(null);

            owner = Owner;

            ourActionListenerDialog = new OurActionListenerDialog();

            textLabel = new JLabel("Закончить регистрацию?");
            textLabel.setLocation(20, 20);
            textLabel.setSize(210, 20);
            add(textLabel);
            textLabel.setFont(new Font("TimesNewRoman", Font.BOLD, 16));

            ok = new JButton("Да");
            ok.setLocation(10, 60);
            ok.setSize(100, 30);
            add(ok);
            ok.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
            ok.addActionListener(ourActionListenerDialog);

            no = new JButton("Нет");
            no.setLocation(140, 60);
            no.setSize(100, 30);
            add(no);
            no.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
            no.addActionListener(ourActionListenerDialog);
        }

        public class OurActionListenerDialog implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if ((e.getSource() == ok)
                        && !(owner.getLogin().isEmpty())
                        && !(owner.getPassword().isEmpty())
                        && !(owner.getSurname().isEmpty())
                        && !(owner.getName().isEmpty())
                        && !(owner.getPatronymic().isEmpty())) {
                    if (owner.getPassword().equals(owner.getRepeatedPassword())) {
                        
                        registrationLogin = owner.getLogin();
                        registrationPassword = owner.getPassword();
                        registrationSurname = owner.getSurname();
                        registrationName = owner.getName();
                        registrationPatronymic = owner.getPatronymic();
                        registrationTypeOfWork = owner.typeOfWork.getSelectedIndex() + 1;
                        
                        setVisible(false);
                        
                        clickedRegistration = true;
                    } else {
                        setVisible(false);
                        owner.repeatedPassword.setText("Пароли не совпадают!");
                    }
                } else {
                    if (owner.getLogin().isEmpty()) {
                        owner.login.setText("А это заполнить?");
                    }

                    if (owner.getPassword().isEmpty()) {
                        owner.password.setText("А это заполнить?");
                    }

                    if (owner.getSurname().isEmpty()) {
                        owner.surname.setText("А это заполнить?");
                    }

                    if (owner.getName().isEmpty()) {
                        owner.name.setText("А это заполнить?");
                    }

                    if (owner.getPatronymic().isEmpty()) {
                        owner.patronymic.setText("А это заполнить?");
                    }

                    if (owner.getRepeatedPassword().isEmpty()) {
                        owner.repeatedPassword.setText("А это заполнить?");
                    }

                    setVisible(false);
                }

                if (e.getSource() == no) {
                    setVisible(false);
                }
            }
        }
    }

    class LoginStatusDialog extends JDialog {

        JLabel textLabel;
        String text = "";
        JButton ok;
        OurActionListenerDialog ourActionListenerDialog;

        private LoginStatusDialog(JFrame owner) {
            super(owner, "Состояние входа", true);
            setSize(250, 130);
            setResizable(false); // Запрет изменения размеров окан
            setLocationRelativeTo(null); // Центрирование
            setLayout(null);

            ourActionListenerDialog = new OurActionListenerDialog();

            textLabel = new JLabel();
            textLabel.setLocation(50, 20);
            textLabel.setSize(150, 20);
            add(textLabel);
            textLabel.setFont(new Font("TimesNewRoman", Font.BOLD, 16));

            ok = new JButton("Ок");
            ok.setLocation(75, 60);
            ok.setSize(100, 30);
            add(ok);
            ok.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
            ok.addActionListener(ourActionListenerDialog);
        }

        public class OurActionListenerDialog implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ok) {
                    setVisible(false);
                }
            }
        }

        public void setTextInLoginStatusDialog(String Text) {
            textLabel.setText(Text);
        }
    }

    public String getLogin() {
        return userLogin;
    }

    public String getPassword() {
        String pass = "";
        for (int i = 0; i < userPassword.length; i++) {
            pass += userPassword[i];
        }
        return pass;
    }

    public boolean isClickedEnter() {
        return clickedEnter;
    }

    public void setFalseClickedEnter() {
        clickedEnter = false;
    }

    public boolean isСlickedRegistration() {
        return clickedRegistration;
    }

    public void setFalseСlickedRegistration() {
        clickedRegistration = false;
    }

    public void setLoginStatusDialog(String Text) {
        loginStatusDialog.setTextInLoginStatusDialog(Text);
        loginStatusDialog.setVisible(true);
    }

    public String getRegistrationLogin() {
        return registrationLogin;
    }

    public String getRegistrationPassword() {
        return registrationPassword;
    }

    public String getRegistrationSurname() {
        return registrationSurname;
    }

    public String getRegistrationName() {
        return registrationName;
    }

    public String getRegistrationPatronymic() {
        return registrationPatronymic;
    }

    public int getRegistrationTypeOfWork() {
        return registrationTypeOfWork;
    }
    
    public void setVisibleRegistration (boolean flag){
        dialog.setVisible(flag);
    }
    
    public void setRegistrationLogin(){
        dialog.login.setText("Такой логин занят!");
    }
            
}
