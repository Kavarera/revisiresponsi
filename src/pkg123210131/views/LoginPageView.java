    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg123210131.views;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author OWNER
 */
public class LoginPageView extends JFrame{
 
    JLabel ltitle = new JLabel("Login");
    
    JLabel lusername = new JLabel("Username");
    JTextField fusername = new JTextField();

    public void setFusername(JTextField fusername) {
        this.fusername = fusername;
    }

    public void setFpassword(JPasswordField fpassword) {
        this.fpassword = fpassword;
    }

    public JTextField getFusername() {
        return fusername;
    }

    public JPasswordField getFpassword() {
        return fpassword;
    }

    public JButton getBlogin() {
        return blogin;
    }
    
    JLabel lpassword = new JLabel("Password");
    JPasswordField fpassword = new JPasswordField();
    
    JButton blogin = new JButton("Login");
    
    public LoginPageView(){
        setSize(800, 600);
        setTitle("Login Page");
        setVisible(true);
        setLayout(null);
        
        setLocationRelativeTo(null);
        
        add(ltitle);
        add(lusername);
        add(lpassword);
        add(fusername);
        add(fpassword);
        add(blogin);
        
        ltitle.setBounds(365, 50, 75, 50);
        
        lusername.setBounds(275, 120, 100, 30);
        fusername.setBounds(275, 150, 250, 30);
        
        lpassword.setBounds(275, 180, 100, 30);
        fpassword.setBounds(275, 210, 250, 30);
        
        blogin.setBounds(350, 260, 100, 30);
        
    }
    
}
