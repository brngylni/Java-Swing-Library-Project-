package com.library.ui;

import com.library.db.concretes.UserDAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame{
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField passwordField;
    private JButton loginBtn;
    private JLabel idLabel;
    private JLabel passwordLabel;

    public LoginForm(String title, String connectionString, String dbUsername, String dbPassword){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = idField.getText();
                String password = passwordField.getText();
                var userDal = new UserDAL(connectionString, dbUsername, dbPassword);
                if(userDal.exists(username, password)){
                    dispose();
                    var app = new MainForm("Welcome to the Great Library", connectionString, dbUsername, dbPassword);
                    app.setVisible(true);
                }else{
                    idField.setBackground(Color.RED);
                    passwordField.setBackground(Color.RED);
                }
            }
        });
    }
}
