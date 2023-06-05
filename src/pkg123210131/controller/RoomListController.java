/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg123210131.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import pkg123210131.helper.RenterHelper;
import pkg123210131.helper.RoomHelper;
import pkg123210131.models.Room;
import pkg123210131.views.RoomListView;

/**
 *
 * @author rafli
 */
public class RoomListController implements ActionListener, ListSelectionListener{
    
    LoginController parent;
    RoomListView view;
    public RoomListController(LoginController lc){
        this.parent=lc;
        view = new RoomListView();
        view.getTabel().getSelectionModel().addListSelectionListener(this);
        view.getBcancel().addActionListener(this);
        
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Mengembalikan nilai false untuk mencegah pengeditan sel
            }
        };
        dtm.addColumn("Name");
        dtm.addColumn("Size");
        dtm.addColumn("Price");
        dtm.addColumn("Status");
        
        List<Room> roomList = new ArrayList<>();
        
        try{
            roomList = RoomHelper.getRoomList();
            for(Room r :roomList){
                dtm.addRow(new Object[]{r.getNama(),r.getSize(),r.getPrice(),r.getStatus()});
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            view.getTabel().setModel(dtm);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.getBcancel()){
           parent.showPage(true);
           view.dispose();
       }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            try{
                String name = view.getTabel().getValueAt(view.getTabel().getSelectedRow(), 0).toString();
                RenterDataController rdc = new RenterDataController(name,this);
                rdc.view.setOnTop(true);
            }
            catch(ArrayIndexOutOfBoundsException ex){
                
            }
        }
    }
    
    public void updateTable(){
        view.setTableModel(null);
        view.getTabel().clearSelection();
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Mengembalikan nilai false untuk mencegah pengeditan sel
            }
        };
        dtm.addColumn("Name");
        dtm.addColumn("Size");
        dtm.addColumn("Price");
        dtm.addColumn("Status");
        
        List<Room> roomList = new ArrayList<>();
        
        try{
            roomList = RoomHelper.getRoomList();
            for(Room r :roomList){
                dtm.addRow(new Object[]{r.getNama(),r.getSize(),r.getPrice(),r.getStatus()});
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        catch(ArrayIndexOutOfBoundsException ex){
            
        }
        finally{
            view.getTabel().setModel(dtm);
        }
    }
    
    
}
