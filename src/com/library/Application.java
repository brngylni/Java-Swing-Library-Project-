package com.library;
import com.library.ui.LoginForm;

import javax.swing.*;

public class Application {


    public static void main(String[] args){
        var loginThread = new Runnable() {
            @Override
            public void run() {
                var login = new LoginForm("Login", "jdbc:mysql://localhost:3306/library", "root", "12345");
                login.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(loginThread);
    }
}
