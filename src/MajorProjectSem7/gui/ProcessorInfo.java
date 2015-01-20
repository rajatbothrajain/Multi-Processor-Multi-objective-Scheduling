package majorprojectsem7.gui;

import Utility.support.kit.utilities.MessagePane;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import majorprojectsem7.core.Graph;
import majorprojectsem7.core.Implementor;

/**
 *
 * @author Rajat Bothra
 */
public class ProcessorInfo extends javax.swing.JPanel {
    
    private Graphics graphics = null;
    private LayoutManager l = new GridBagLayout();
    private ArrayList<Graph> tasks = new ArrayList();
    int startIndex = 0;
    int stopIndex = 0;

    /**
     * Creates new form ProcessorInfo
     */
    public ProcessorInfo(int n) {
        initComponents();
        jPanel1.setLayout(l);
        graphics = jPanel1.getGraphics();
        jTextField1.setText("" + n);
    }
    private int id = 0;
    private static int state = 0;
    Point startLocation = new Point(0, 0);
    Point stopLocation = new Point(0, 0);
    ArrayList<JButton> buttons = new ArrayList<>();
    
    void addTask(Graph g) {
        JButton jb1 = new JButton();
        
        jb1.setSize(jb1.getPreferredSize());
        
        this.graphics = jPanel1.getGraphics();
        
        jb1.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isControlDown()) {
                    if (state == 0) {
                        state = 1; // state = 1 => get the location
                        startLocation = e.getLocationOnScreen();
                        startIndex = buttons.indexOf((JButton) e.getSource());
                    } else if (state == 1) {
                        state = 0;
                        stopIndex = buttons.indexOf((JButton) e.getSource());
                        stopLocation = e.getLocationOnScreen();
                        if (startIndex != stopIndex) {
                            //if (tasks.isEmpty() || !tasks.contains(tasks.get(stopIndex))) {
                            tasks.get(startIndex).addChildren(tasks.get(stopIndex));
                            GUIMain.x.sendRequest("ADDED " + tasks.get(startIndex) + " AS PARENT OF " + tasks.get(stopIndex));
                            //}
                            if (!tasks.contains(tasks.get(startIndex))) {
                                tasks.get(stopIndex).addParent(tasks.get(startIndex));
                                
                                graphics.drawLine(startLocation.x, startLocation.y, stopLocation.x, stopLocation.y);
                                validate();
                            }
                        }
                    }
                } else if (e.getClickCount() >= 2) {
                    MessagePane mp = new MessagePane();
                    mp.setTitle("INFO !!");
                    mp.setMessage("Task Name : " + g.getName()
                            + "\nExecution time = " + g.getExecutionTime()
                            + "\nChildren : " + g.getChildren()
                            + "\nParent : " + g.getParent());
                    mp.setVisible(true);
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        jb1.addMouseMotionListener(new MouseMotionListener() {
            
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                if (state == 1 && e.isControlDown()) {
                    graphics.drawLine(startLocation.x, startLocation.y, e.getLocationOnScreen().x, e.getLocationOnScreen().y);
                    validate();
                }
            }
        });
        jb1.setText(g.getName());
        jb1.setVisible(true);
        jPanel1.add(jb1);
        buttons.add(jb1);
        tasks.add(g);
        validate();
    }
    
    public ArrayList<Graph> getTasks() {
        return tasks;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(102, 102, 102), new java.awt.Color(204, 204, 204), new java.awt.Color(51, 51, 51)));

        jLabel1.setText("Number of processors");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Display"));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 77, Short.MAX_VALUE)
        );

        jButton1.setText("Compute allocation");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(8, 8, 8)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        System.out.println("add task");
        Graph g1 = new Graph(id++, (int) (Math.random() * 100));
        addTask(g1);
        validate();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            FileWriter fw = new FileWriter(new File("in.txt"));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(jTextField1.getText() + "\n");
            bw.write("" + tasks.size() + "\n");
            for (Graph t : tasks) {
                bw.write(t.getExecutionTime() + "\n");
            }
            for (Graph t : tasks) {
                bw.write(t.getChildren().size() + "\n");
                bw.write(t.getChildren().toString().replace("[", "").replace("]", "").replace(",", "").replace("T", "") + "\n");
            }
            for (Graph t : tasks) {
                for (int i = 0; i < tasks.size(); i++) {
                    bw.write("0");
                    if (i != tasks.size() - 1) {
                        bw.write(" ");
                    }
                }
                bw.write("\n");
            }
            bw.close();
            fw.close();
        } catch (Exception ex) {
        }
        try {
            Implementor.main(null);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " | " + ex.getCause());
            MessagePane mp = new MessagePane();
            
            mp.setTitle("ERROR!");
            mp.setErrorStyle();
            mp.setMessage(Arrays.deepToString(ex.getStackTrace()));
            mp.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
