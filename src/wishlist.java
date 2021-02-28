
import com.google.gson.Gson;
import java.awt.Color;
import static java.lang.Integer.parseInt;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc
 */
public class wishlist extends javax.swing.JInternalFrame {
    Streams stream;
    /**
     * Creates new form wishlist
     */
    public wishlist(Streams stream) {
        initComponents();
        this.stream = stream;
        IdDel.setEditable(false);
        System.out.println("wishlist stream is "+stream);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui =(BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
    }

//    /**
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        wishtab = new javax.swing.JTable();
        InsertBtn = new javax.swing.JButton();
        DelBtn = new javax.swing.JButton();
        IdDel = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(610, 400));

        wishtab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "username", "item", "CONTRIBUTOR_NAME", "amount"
            }
        ));
        wishtab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wishtabMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(wishtab);

        InsertBtn.setBackground(new java.awt.Color(0, 69, 129));
        InsertBtn.setForeground(new java.awt.Color(255, 255, 255));
        InsertBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-add-new-15.png"))); // NOI18N
        InsertBtn.setText("Insert");
        InsertBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InsertBtnMouseClicked(evt);
            }
        });
        InsertBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertBtnActionPerformed(evt);
            }
        });

        DelBtn.setBackground(new java.awt.Color(0, 69, 129));
        DelBtn.setForeground(new java.awt.Color(255, 255, 255));
        DelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-delete-15.png"))); // NOI18N
        DelBtn.setText("Delete");
        DelBtn.setPreferredSize(new java.awt.Dimension(61, 23));
        DelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelBtnActionPerformed(evt);
            }
        });

        IdDel.setForeground(new java.awt.Color(153, 153, 153));
        IdDel.setText("Select the product  id");
        IdDel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                IdDelFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                IdDelFocusLost(evt);
            }
        });
        IdDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(IdDel, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(DelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(InsertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InsertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void InsertBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertBtnActionPerformed
        UserDTO regSelectItem = new UserDTO(); 
        //Streams stream = new Streams();
        regSelectItem.tag="BringProductData";
        regSelectItem.username=Streams.myUsername;
        String data = new Gson().toJson(regSelectItem);
        stream.pos.println(data);
    }//GEN-LAST:event_InsertBtnActionPerformed

    private void InsertBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InsertBtnMouseClicked
        
        
        insertitem ProList = new insertitem(stream);
        ProList.setVisible(true);
        ProList.pack();
        ProList.setLocationRelativeTo(null);                                   
              // insert
    }//GEN-LAST:event_InsertBtnMouseClicked

    private void DelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelBtnActionPerformed
        
        if (IdDel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Product ID");

        } else {
        int ProID = parseInt(IdDel.getText());
        UserDTO regDelItem = new UserDTO(); 
        //Streams stream = new Streams();
        regDelItem.tag="DeleteFromMyWishList";
        regDelItem.username=Streams.myUsername;
        regDelItem.PROD_ID=ProID;
        String data = new Gson().toJson(regDelItem);
        stream.pos.println(data); 
        }
    }//GEN-LAST:event_DelBtnActionPerformed

    private void IdDelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdDelFocusGained
                                       
        
           if(IdDel.getText().equals("Select the product  id"))
            {
                IdDel.setText("");
                IdDel.setForeground(new Color(153,153,153));
            }// TODO add your handling code here:*/
                                         

                          // TODO add your handling code here:
    }//GEN-LAST:event_IdDelFocusGained

    private void IdDelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdDelFocusLost
                                  
        if(IdDel.getText().equals(""))
            {
                IdDel.setText("Select the product  id");
                IdDel.setForeground(new Color(153,153,153));
            }//// TODO add your handling code here:
           
    }//GEN-LAST:event_IdDelFocusLost

    private void IdDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdDelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdDelActionPerformed

    private void wishtabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wishtabMouseClicked
        int index = wishtab.getSelectedRow(); /// el row aly ana wa2fa 3ndo (index,0)
        String name = wishtab.getValueAt(index, 0).toString();
        IdDel.setText(name);
    }//GEN-LAST:event_wishtabMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DelBtn;
    private javax.swing.JTextField IdDel;
    private javax.swing.JButton InsertBtn;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable wishtab;
    // End of variables declaration//GEN-END:variables
}
