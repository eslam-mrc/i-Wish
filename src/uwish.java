
import com.google.gson.Gson;
import static java.lang.Integer.parseInt;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
public class uwish extends javax.swing.JInternalFrame {
Streams stream;
    /**
     * Creates new form uwish
     */
    public uwish(Streams stream) {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui =(BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        this.stream = stream;
        
        UserDTO objRecharge = new UserDTO();
        objRecharge.tag="CurrentCredit";
        objRecharge.username=stream.myUsername;
        //objRecharge.currCredit = parseInt(currCreditLabl.getText());
        String data = new Gson().toJson(objRecharge);
        stream.pos.println(data);

       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        currCreditLabl = new javax.swing.JLabel();
        moneyamount = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(610, 400));
        getContentPane().setLayout(null);

        currCreditLabl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        currCreditLabl.setText("Credit");
        getContentPane().add(currCreditLabl);
        currCreditLabl.setBounds(260, 60, 180, 22);

        moneyamount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moneyamountActionPerformed(evt);
            }
        });
        getContentPane().add(moneyamount);
        moneyamount.setBounds(230, 110, 150, 30);

        jButton1.setBackground(new java.awt.Color(0, 69, 129));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-money-15.png"))); // NOI18N
        jButton1.setText("Recharge");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(250, 170, 110, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Recharge Amount: ");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(44, 110, 160, 22);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Your Current Credit:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(40, 60, 180, 22);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void moneyamountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moneyamountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moneyamountActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.out.println("d5lt 3shan a5od elflos mn el box");
        int rechargeAmt = parseInt(moneyamount.getText());   
       System.out.println(rechargeAmt);
       UserDTO regRecharge = new UserDTO();       
        regRecharge.tag="Recharge My Credit";
        regRecharge.username=Streams.myUsername;
        regRecharge.recharge_Amount=rechargeAmt;
        String Con_data = new Gson().toJson(regRecharge);
        System.out.println(stream);
        stream.pos.println(Con_data);  
        

// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel currCreditLabl;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField moneyamount;
    // End of variables declaration//GEN-END:variables
}
