package majorprojectsem7.gui;

import File.Functions;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import majorprojectsem7.core.InstructionSet_8086;

/**
 *
 * @author Rajat Bothra
 */
public class ProcessorCore extends javax.swing.JPanel {

    InstructionSet_8086 processor = new InstructionSet_8086();
    String contents;
    Timer timer;

    String pad(String s, int radix) {
        int len = s.length();
        StringBuilder x = new StringBuilder();
        while (len++ < radix) {
            x = x.append("0");
        }
        x = x.append(s);
        return x.toString();
    }
    StringBuilder log = new StringBuilder();

    /**
     * Creates new form ProcessorCore
     */
    public ProcessorCore() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CF3 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        CF = new javax.swing.JRadioButton();
        PF = new javax.swing.JRadioButton();
        AF = new javax.swing.JRadioButton();
        ZF = new javax.swing.JRadioButton();
        CF5 = new javax.swing.JRadioButton();
        CF6 = new javax.swing.JRadioButton();

        CF3.setBackground(new java.awt.Color(0, 0, 102));
        CF3.setForeground(new java.awt.Color(102, 255, 0));
        CF3.setText("CF");
        CF3.setBorderPainted(true);

        jLabel1.setText("Program Counter : ");

        jTextField1.setEditable(false);

        jLabel2.setText("Accumulator :");

        jLabel3.setText("CPU REG 2");

        jLabel4.setText("CPU REG 3");

        jTextField2.setEditable(false);
        jTextField2.setText("0");

        jTextField3.setEditable(false);

        jTextField4.setEditable(false);

        jTextField5.setEditable(false);

        jLabel5.setText("INSTRUCTION  : ");

        jButton1.setText("Browse ASM FILE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField6.setEditable(false);
        jTextField6.setText("/");

        jButton2.setText("Start CPU");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        CF.setBackground(new java.awt.Color(0, 0, 102));
        CF.setForeground(new java.awt.Color(102, 255, 0));
        CF.setText("CF");
        CF.setBorderPainted(true);

        PF.setBackground(new java.awt.Color(0, 0, 102));
        PF.setForeground(new java.awt.Color(102, 255, 0));
        PF.setText("PF");
        PF.setBorderPainted(true);

        AF.setBackground(new java.awt.Color(0, 0, 102));
        AF.setForeground(new java.awt.Color(102, 255, 0));
        AF.setText("AF");
        AF.setBorderPainted(true);

        ZF.setBackground(new java.awt.Color(0, 0, 102));
        ZF.setForeground(new java.awt.Color(102, 255, 0));
        ZF.setText("ZF");
        ZF.setBorderPainted(true);

        CF5.setBackground(new java.awt.Color(0, 0, 102));
        CF5.setForeground(new java.awt.Color(102, 255, 0));
        CF5.setText("CF");
        CF5.setBorderPainted(true);

        CF6.setBackground(new java.awt.Color(0, 0, 102));
        CF6.setForeground(new java.awt.Color(102, 255, 0));
        CF6.setText("CF");
        CF6.setBorderPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(40, 40, 40)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(40, 40, 40)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CF, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PF, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AF, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ZF, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CF5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CF6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CF))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PF))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AF))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ZF))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CF5))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CF6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        System.out.println(contents);
        String s[] = contents.split("\n");
        jButton2.setEnabled(false);
        timer = new Timer(100, new ActionListener() {
            int i = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!s[i].isEmpty()) {
                    int index = s[i].indexOf(" ");
                    String inst = s[i].substring(0, index + 1).trim();
                    s[i] = s[i].substring(index + 1).trim();
                    try {
                        processor.execute(inst, s[i]);
                        int k = Integer.parseInt(Arrays.toString(InstructionSet_8086.AX.getValue()).replace("[", "").replace("]", "").replace(",", "").replace(" ", ""));
                        jTextField2.setText(pad(Integer.toBinaryString(k), InstructionSet_8086.REG_SZ) + " (" + k + ")");
                        log = log.append(System.nanoTime()).append(" :: AX value ").append(jTextField2.getText()).append("\n");
                        byte[] b = InstructionSet_8086.FLAGS.getValue();
                        if (b[processor.CF] == 1) {
                            CF.setSelected(true);
                        } else {
                            CF.setSelected(false);
                        }
                        if (b[processor.PF] == 1) {
                            PF.setSelected(true);
                        } else {
                            PF.setSelected(false);
                        }
                        if (b[processor.AF] == 1) {
                            AF.setSelected(true);
                        } else {
                            AF.setSelected(false);
                        }
                        if (b[processor.ZF] == 1) {
                            ZF.setSelected(true);
                        } else {
                            ZF.setSelected(false);
                        }
                        if (b[processor.CF] == 1) {
                            CF.setSelected(true);
                        } else {
                            CF.setSelected(false);
                        }
                        System.out.println("log : " + log);
                        // System.out.println(Arrays.toString(processor.AX.getValue()));
                    } catch (Exception ex) {
                        //Logger.getLogger(ProcessorCore.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                i++;
                if (i >= s.length) {
                    jButton2.setEnabled(true);
                    timer.stop();
                }
            }
        });
        timer.start();
        System.out.println(log);
        Functions.File_Input(log.toString(), Functions.getParent(jTextField6.getText()) + "/out.txt", null);
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                java.awt.Desktop.getDesktop().open(new File(Functions.getParent(jTextField6.getText()) + "/out.txt"));
            } catch (IOException ex) {
                Logger.getLogger(ProcessorCore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser();
        jfc.showDialog(jLabel1, "Select");
        File file = jfc.getSelectedFile();
        if (file.getName().toLowerCase().endsWith("asm")) {
            jTextField6.setText(file.getPath());
        }
        contents = Functions.File_Output(file.getPath());

    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        ProcessorCore pc = new ProcessorCore();
        javax.swing.JFrame jf = new javax.swing.JFrame("Cache");
        Dimension screenSize = jf.getToolkit().getScreenSize();
        jf.setLocation(0, 0);
        pc.setLocation(0, 0);
        pc.setVisible(true);
        jf.add(pc);
        jf.setVisible(true);
        jf.setSize(700, 400);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton AF;
    private javax.swing.JRadioButton CF;
    private javax.swing.JRadioButton CF3;
    private javax.swing.JRadioButton CF5;
    private javax.swing.JRadioButton CF6;
    private javax.swing.JRadioButton PF;
    private javax.swing.JRadioButton ZF;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
