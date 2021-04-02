/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Gagadok
 */
public class OkDialog extends JDialog {

    JLabel label;
    JButton ok;
    Font ft;
    DialogActionListener dialogActionListener;

    public OkDialog(JFrame owner, String text, String Label) {
        super(owner, text, true);
        setSize(500, 170);
        setResizable(false); // Запрет изменения размеров окан
        setLocationRelativeTo(null); // Центрирование

        setLayout(null);

        ft = new Font("TimesRoman", Font.BOLD, 30);

        dialogActionListener = new DialogActionListener();

        label = new JLabel(Label);
        label.setFont(ft);
        label.setLocation(100, 20);
        label.setSize(300, 50);
        add(label);

        ok = new JButton("Ok");
        ok.setFont(ft);
        ok.setLocation(150, 80);
        ok.setSize(200, 50);
        ok.addActionListener(dialogActionListener);
        add(ok);
    }

    public class DialogActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }
}
