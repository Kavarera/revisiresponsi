/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg123210131.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import pkg123210131.models.Account;
import pkg123210131.views.LoginPageView;
import pkg123210131.helper.AccountHelper;

/**
 *
 * @author rafli
 */
public class LoginController implements ActionListener {
    
    LoginPageView view;
    public LoginController(){
        view = new LoginPageView();
        view.getBlogin().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.getBlogin()){
            
            Account acc = new Account();
            acc.setUsername(view.getFusername().getText());
            acc.setPassword(view.getFpassword().getText());
            try{
                AccountHelper.LoginAccount(acc);
                if(acc.getRole()==null){
                    System.out.println("Akun tidak tersedia");
                }
                else if("ADMIN".equals(acc.getRole().toUpperCase())){
                    //navigate to admin page;
                    AdminController admin = new AdminController(this);
                    showPage(false);
                }
                else{
                    RoomListController renter = new RoomListController(this);
                    showPage(false);
                }
            }
            catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void showPage(boolean stat){
        view.setVisible(stat);
    }
    
}
