/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg123210131.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg123210131.helper.RenterHelper;
import pkg123210131.helper.RoomHelper;
import pkg123210131.models.Renter;
import pkg123210131.views.RenterDataView;

/**
 *
 * @author rafli
 */
public class RenterDataController implements ActionListener{

    Renter renter;
    RenterDataView view;
    AdminController ac;
    String selectedRoom;
    public RenterDataController(Renter r, AdminController parent){
        this.renter=r;
        this.ac = parent;
        view = new RenterDataView();
        view.setOnTop(true);
        view.setTfContact(renter.getContact());
        view.setTfName(renter.getNama());
        view.setTfRentTime(String.valueOf(renter.getDuration()));
        view.setTfid(String.valueOf(renter.getId()));
        
        //add listener
        view.getBtnAddPanel().addActionListener(this);
        view.getBtnLogout().setVisible(false);
    }
    
    RoomListController rlc;
    public RenterDataController(String room, RoomListController parent){
        view = new RenterDataView();
        this.selectedRoom=room;
        this.rlc = parent;
        view.getBtnAddPanel().addActionListener(this);
        view.getBtnLogout().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==view.getBtnAddPanel()){
            if(view.getBtnLogout().isVisible()==true){
                System.out.println("added clicked");
                Renter renter = new Renter();
                renter.setNama(view.getTfName().getText());
                renter.setContact(view.getTfContact().getText());
                renter.setDuration(Integer.parseInt(view.getTfRentTime().getText().toString()));
                renter.setId(Integer.parseInt(view.getTfid().getText().toString()));
                renter.setRoom(selectedRoom);
                rlc.updateTable();
                try {
                    RenterHelper.addRenter(renter);
                    RoomHelper.updateRoom(renter.getRoom(),renter.getNama());
                } catch (SQLException ex) {
                    Logger.getLogger(RenterDataController.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally{
                    view.getWindow().dispose();
                }
            }
            else{
                renter.setContact(view.getTfContact().getText());
                renter.setId(Integer.parseInt(view.getTfid().getText()));
                renter.setDuration(Integer.parseInt(view.getTfRentTime().getText()));
                renter.setNama(view.getTfName().getText());
                try {
                    renter.setBill(renter.calculateTotalBill(RoomHelper.getPrice(renter.getRoom())));
                    RenterHelper.updateRenter(renter);
                    view.getWindow().dispose();
                    ac.updateTable();
                } catch (SQLException ex) {
                    Logger.getLogger(RenterDataController.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch(ArrayIndexOutOfBoundsException aex){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,aex.getCause()+"\n"+aex.getMessage());
                }
            }
        }
        else if(e.getSource()==view.getBtnLogout()){
            view.getWindow().dispose();
        }
    }
    
}
