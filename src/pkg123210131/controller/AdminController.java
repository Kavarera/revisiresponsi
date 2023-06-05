/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg123210131.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
import pkg123210131.models.Renter;
import pkg123210131.views.AdminPageView;
import pkg123210131.views.EditDeleteDialogView;


/**
 *
 * @author rafli
 */
public class AdminController implements ActionListener , ListSelectionListener {
    JDialog dialog;
    AdminPageView view;
    LoginController loginPage;
    List<Renter> renterList;
    public AdminController(LoginController lc){
        view = new AdminPageView();
        loginPage = lc;
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Mengembalikan nilai false untuk mencegah pengeditan sel
            }
        };
        dtm.addColumn("ID");
        dtm.addColumn("Name");
        dtm.addColumn("Contact");
        dtm.addColumn("Room");
        dtm.addColumn("Duration");
        dtm.addColumn("Bill");
        dtm.addColumn("Status");
        view.getTabel().getSelectionModel().addListSelectionListener(this);
        view.getBlogout().addActionListener(this);
        try{
            renterList = RenterHelper.getListRenter();
            for(Renter r : renterList){
                dtm.addRow(new Object[]{r.getId(),r.getNama(),r.getContact(),r.getRoom(),r.getDuration(),r.getBill(),r.getStatus()});
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
       if(e.getSource()==view.getBlogout()){
           loginPage.showPage(true);
           view.dispose();
           
       }
    }
    
    public JDialog getDialog(){
        return this.dialog;
    }
    
    public void updateTable(){
        renterList.clear();
        view.setTableModel(null);
        try {
            renterList = RenterHelper.getListRenter();
            DefaultTableModel dtm = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Mengembalikan nilai false untuk mencegah pengeditan sel
                }
            };
            dtm.addColumn("ID");
            dtm.addColumn("Name");
            dtm.addColumn("Contact");
            dtm.addColumn("Room");
            dtm.addColumn("Duration");
            dtm.addColumn("Bill");
            dtm.addColumn("Status");
            
            for(Renter r : renterList){
                dtm.addRow(new Object[]{r.getId(),r.getNama(),r.getContact(),r.getRoom(),r.getDuration(),r.getBill(),r.getStatus()});
            }
            view.getTabel().setModel(dtm);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(ArrayIndexOutOfBoundsException aex){
            
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            System.out.println(view.getTabel().getValueAt(view.getTabel().getSelectedRow(), 6));
            if(!"PAID".equals(view.getTabel().getValueAt(view.getTabel().getSelectedRow(), 6).toString().toUpperCase())){
                try{
                    int choice = JOptionPane.showOptionDialog(null, "Mengubah Menjadi Sudah Dibayar?", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if(choice == JOptionPane.YES_OPTION){
                        try {
                            RenterHelper.updateRenter(Integer.parseInt(view.getTabel().getValueAt(view.getTabel().getSelectedRow(),0).toString()));
                            updateTable();
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                }
                catch(Exception ex){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,ex.getMessage());
                }
            }
            else{
                System.out.println("already paid");
                EditDeleteDialogController edc = null;
                try {
                    edc = new EditDeleteDialogController(this,RenterHelper.getRenterById(Integer.parseInt(view.getTabel().getValueAt(view.getTabel().getSelectedRow(), 0).toString())));
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(edc!=null){
                    dialog = new JDialog(view.getWindow(), "Choose Action", true);
                    dialog.setContentPane(edc.getView().getContentPane());
                    dialog.pack();
                    dialog.setLocationRelativeTo(view.getWindow());
                    dialog.setVisible(true);
                    dialog.requestFocus();
                }
                
                
            }
        }
    }
    
    public class EditDeleteDialogController implements ActionListener{
        EditDeleteDialogView EDview;
        Renter renter;
        AdminController parent;
        public EditDeleteDialogController(AdminController p,Renter r){
            this.EDview = new EditDeleteDialogView();
            this.EDview.getjButton1().addActionListener(this);
            this.EDview.getjButton2().addActionListener(this);
            this.renter = r;
            this.parent=p;
        }
        
        public EditDeleteDialogView getView(){
            return this.EDview;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==EDview.getjButton2()){
                try{
                    RenterHelper.deleteRenter(renter.getId());
                    RoomHelper.setEmptyRoom(renter.getRoom());
                    parent.updateTable();
                    parent.getDialog().dispose();
                }
                catch(SQLException ex){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage());
                }
            }
            else{
                System.out.println(renter.getRoom());
                RenterDataController rdc = new RenterDataController(renter,parent);
                System.out.println("di klik");
                parent.getDialog().dispose();
            }
        }
        
    }
}
