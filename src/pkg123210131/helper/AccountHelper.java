/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg123210131.helper;

import pkg123210131.models.Account;
import java.sql.*;

/**
 *
 * @author rafli
 */
public class AccountHelper {
    Account acc;
    
    public static void LoginAccount(Account acc) throws SQLException{
        ConnectorDB db = new ConnectorDB();
        
        String query = "select * from accounts where username = \""+acc.getUsername()+"\" and password=\""+acc.getPassword()+"\"";
        try{
            ResultSet rs =  db.getStatement().executeQuery(query);
            if(rs.next()){
                acc.setRole(rs.getString("role"));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            db.closeConnection();
        }
    }
    
}
