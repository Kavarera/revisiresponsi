/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg123210131.helper;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author rafli
 */
public class ConnectorDB {
    private Connection _connectionDB;
    private Statement _statement;
    public ConnectorDB(){
        try{
            _connectionDB = DriverManager.getConnection("jdbc:mysql://localhost/responsipbo", "root", "");
            _statement=_connectionDB.createStatement();
        }
        catch(SQLException ex){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Statement getStatement(){
        return this._statement;
    }
    public void closeConnection() throws SQLException{
        this._connectionDB.close();
    }
}
