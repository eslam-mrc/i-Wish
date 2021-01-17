
import com.google.gson.Gson;
import java.awt.Color;
import static java.lang.Integer.parseInt;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc
 */
public class friendwishlist extends javax.swing.JFrame {
         
       Streams stream;
       int mouserpx ;
       int mouserpy ;
    /**
     * Creates new form friendwishlist
     */
    public friendwishlist(Streams stream) {
        initComponents();
        item_id.setEditable(false);
        this.stream=stream;
        System.out.println("friendwishlist stream is "+stream);
        TableColumnModel tcm = jTable1.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(300);     //ID
        tcm.getColumn(1).setPreferredWidth(400);    //Name
        tcm.getColumn(2).setPreferredWidth(300);    //Price
        tcm.getColumn(3).setPreferredWidth(700);    //Contribution
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelclose = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelclose1 = new javax.swing.JLabel();
        jLabelclose2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        item_id = new javax.swing.JTextField();
        item_amout = new javax.swing.JTextField();
        contribute = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(23, 35, 51));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelclose.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelclose.setForeground(new java.awt.Color(255, 255, 255));
        jLabelclose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-left-20.png"))); // NOI18N
        jLabelclose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelcloseMouseClicked(evt);
            }
        });
        jPanel1.add(jLabelclose, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 20, 19));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("-");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 16, -1));

        jLabelclose1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelclose1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelclose1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_Multiply_25px.png"))); // NOI18N
        jLabelclose1.setText("x");
        jLabelclose1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelclose1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelclose1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 30, 30));

        jLabelclose2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelclose2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelclose2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-left-20.png"))); // NOI18N
        jLabelclose2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelclose2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabelclose2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 20, 19));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-star-15.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-star-15.png"))); // NOI18N
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-star-15.png"))); // NOI18N
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, -1, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-star-15.png"))); // NOI18N
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 20, 20, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-star-15.png"))); // NOI18N
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, -1, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-star-15.png"))); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-star-15.png"))); // NOI18N
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, -1));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-star-15.png"))); // NOI18N
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 40));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Product Price", "Amount of Contribution"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        item_id.setForeground(new java.awt.Color(51, 51, 51));
        item_id.setText("Select Product id from the list");
        item_id.setCaretColor(new java.awt.Color(102, 102, 102));
        item_id.setSelectionColor(new java.awt.Color(102, 102, 102));
        item_id.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                item_idFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                item_idFocusLost(evt);
            }
        });
        item_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_idActionPerformed(evt);
            }
        });

        item_amout.setForeground(new java.awt.Color(51, 51, 51));
        item_amout.setText("Amount");
        item_amout.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                item_amoutFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                item_amoutFocusLost(evt);
            }
        });

        contribute.setBackground(new java.awt.Color(0, 69, 129));
        contribute.setForeground(new java.awt.Color(255, 255, 255));
        contribute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-money-15 (1).png"))); // NOI18N
        contribute.setText("contribute");
        contribute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contributeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(item_id, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(item_amout, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(contribute)
                .addGap(108, 108, 108))
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contribute, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(item_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(item_amout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 33, 570, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelcloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelcloseMouseClicked
        this.dispose();//new myfriends(null).setVisible(true);
        //dispose();
    }//GEN-LAST:event_jLabelcloseMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.setState(login.ICONIFIED);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabelclose1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelclose1MouseClicked
       dispose();                // TODO add your handling code here:
    }//GEN-LAST:event_jLabelclose1MouseClicked

    private void contributeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contributeActionPerformed
        if (item_id.getText().isEmpty() && item_amout.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Product ID and Amount");

        } else {   
      int RowsNum=jTable1.getRowCount();
      if(RowsNum == 0)
      { 
          
         item_id.setEnabled(true);
         item_amout.setEnabled(true);
         contribute.setEnabled(true);
          System.out.println("not found");
       JOptionPane.showMessageDialog(null, "No Products To Contribute at ");
      }
      else
      {
        int colNum =jTable1.getSelectedRow(); /// el row aly ana wa2fa 3ndo (index,0)
        int pID = parseInt(jTable1.getValueAt(colNum, 0).toString());
        System.out.println(RowsNum);
        System.out.println("PID is" +pID);        
       int index =myfriends.friendsTable.getSelectedRow(); /// el row aly ana wa2fa 3ndo (index,0)
       String name = myfriends.friendsTable.getValueAt(index, 0).toString(); 
       System.out.println(name);
       int ProID = parseInt(item_id.getText());
       int AmountCON = parseInt(item_amout.getText());      
       String friendName =name;
       System.out.println(ProID);
       System.out.println(AmountCON);
       System.out.println(friendName);
       if(pID !=ProID){
         System.out.println("not found product");
       JOptionPane.showMessageDialog(null, "Enter vaild product id ");
       
       
       }
       else{
        UserDTO regDelItem = new UserDTO();        
       //Streams stream = new Streams();
        regDelItem.tag="Contribute";
        regDelItem.username=Streams.myUsername;
        regDelItem.PROD_ID=ProID;
        regDelItem.Amount_CONTRIBUTION=AmountCON;
        regDelItem.Friendname=friendName;
        String Con_data = new Gson().toJson(regDelItem);
        System.out.println(Con_data);
        System.out.println("abl");
        System.out.println(stream);
        stream.pos.println(Con_data);      
        System.out.println("baad");
      } };
        }
    }//GEN-LAST:event_contributeActionPerformed

    private void item_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_idActionPerformed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
       int kordinatx = evt.getXOnScreen();
        int kordinaty = evt.getYOnScreen();
        this.setLocation(kordinatx-mouserpx, kordinaty-mouserpy);        // TODO add your handling code here:
    }                                    

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {                                     
           mouserpx =evt.getX();
           mouserpy =evt.getY();             // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jLabelclose2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelclose2MouseClicked
        this.hide();
        myfriends friend = new myfriends(stream);
        friend.setVisible(true);

    }//GEN-LAST:event_jLabelclose2MouseClicked

    private void item_amoutFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_item_amoutFocusGained
        if(item_amout.getText().equals("Amount"))
            {
                item_amout.setText("");
                item_amout.setForeground(new Color(153,153,153));
            }// TODO add your handling code here:
    }//GEN-LAST:event_item_amoutFocusGained

    private void item_amoutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_item_amoutFocusLost
           if(item_amout.getText().equals(""))
            {
                item_amout.setText("Amount");
                item_amout.setForeground(new Color(153,153,153));
            }//
    }//GEN-LAST:event_item_amoutFocusLost

    private void item_idFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_item_idFocusGained
        if(item_id.getText().equals("item's id"))
            {
                item_id.setText("");
                item_id.setForeground(new Color(153,153,153));
            }
    }//GEN-LAST:event_item_idFocusGained

    private void item_idFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_item_idFocusLost
        if(item_id.getText().equals(""))
            {
                item_id.setText("item's id");
                item_id.setForeground(new Color(153,153,153));
            }// TODO add your handling code here:
    }//GEN-LAST:event_item_idFocusLost

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int index =jTable1.getSelectedRow(); // get row index
        String name = jTable1.getValueAt(index, 0).toString();
        item_id.setText(name);// TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(friendwishlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(friendwishlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(friendwishlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(friendwishlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new friendwishlist(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton contribute;
    private javax.swing.JTextField item_amout;
    private javax.swing.JTextField item_id;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelclose;
    private javax.swing.JLabel jLabelclose1;
    private javax.swing.JLabel jLabelclose2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
