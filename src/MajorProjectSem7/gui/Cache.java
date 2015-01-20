package majorprojectsem7.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicBorders;

/**
 *
 * @author Rajat Bothra
 */
public class Cache extends javax.swing.JPanel {

    static int numberOfLines = 100;
    static int defaultHeightViewPort = 55;
    int rows, cols;
    int lineSize = 64;

    /**
     * Creates new form Cache
     */
    private Cache(String name, int numberOfLines, int lineSize) throws Exception {
        if (numberOfLines > 4096) {
            throw new Exception("Cache Size out of Resolution");
        }
        if (!isPowerOf2(lineSize)) {
            throw new Exception("Line Size must be power of 2");
        }
        initComponents();
        setName(name);
        this.lineSize = lineSize;
        Cache.numberOfLines = numberOfLines;
        //defaultHeightViewPort = super.getToolkit().getScreenSize().height / numberOfLines;
        defaultLineSize = new Dimension(300, defaultHeightViewPort);
        int sqrt = (int) Math.sqrt(numberOfLines);
        rows = sqrt;
        cols = sqrt;
        if (rows * cols != numberOfLines) {
            cols++;
        }
        setSize(rows * defaultLineSize.width, defaultLineSize.height * cols);
        LayoutManager lm = new GridLayout(rows, cols, 10, 10);
        this.setLayout(lm);
        for (int i = 0; i < numberOfLines; i++) {
            Line l = new Line(name, defaultLineSize, i, numberOfLines);
            l.setLocation(20 + defaultLineSize.height * i, 20 + i * defaultLineSize.height);
            add(l, lm);
            lines.add(l);
        }
        setBorder(new BasicBorders.MarginBorder());
    }

    ArrayList<Line> lines = new ArrayList<>();
    static final Dimension defaultCacheSize = new Dimension(300, defaultHeightViewPort);

    final Dimension defaultLineSize;

    private boolean isPowerOf2(int lineSize) {
        if (lineSize == 1) {
            return false;
        }
        while (lineSize != 1) {
            if ((lineSize % 2) == 1) {
                return false;
            }
            lineSize >>= 1;
        }
        return true;
    }

    class Line extends javax.swing.JPanel {

        String cacheName = "";
        int lineNumber;
        int size = 100;
        boolean set = false;

        private final javax.swing.JTextField content = new JTextField();

        public Line(String cacheName, Dimension dimension, int lineNumber, int size) {
            super();
            content.setEditable(false);
            setBackground(Color.YELLOW);

            content.setColumns(5);
            content.setLocation(5, 2);

            setSize(defaultLineSize);

            //int w = dimension.width - 40, h = defaultHeightViewPort;
            //w = w <= 0 ? 10 : w;
            //content.setSize(w, h);
            //content.setBounds(5, 2, w, h);
            content.setVisible(true);

            add(content);

            setName("[" + cacheName + "- Line#" + lineNumber + "]");

            setBorder(new TitledBorder("[" + cacheName + "- Line#" + lineNumber + "]"));

            this.size = size;

            fillZeroes();

            this.lineNumber = lineNumber;

            setVisible(true);
        }

        public final void fillZeroes() {
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < lineSize; i++) {
                b.append("0");
            }
            content.setText(b.toString());
        }

        public void fetchLine() {
            setBackground(Color.green);
        }

        public void stopFetchLine() {
            setBackground(Color.YELLOW);
        }
    }

    public static void main(String args[]) throws Exception {
        javax.swing.JFrame jf = new javax.swing.JFrame("Cache");
        JScrollPane js = new JScrollPane();
        Dimension screenSize = jf.getToolkit().getScreenSize();
        js.setBounds(0, 0, screenSize.width, screenSize.height);
        jf.setLocation(0, 0);
        js.setVisible(true);
        int lines = Integer.parseInt(JOptionPane.showInputDialog("Enter number of Lines in the cache"));
        Cache c = new Cache("L2-Cache", lines, 64);
        c.setLocation(0, 0);
        js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        js.setViewportView(c);
        c.setVisible(true);
        jf.setSize(c.getSize().width + 20, c.getHeight() + 20);

        jf.setBackground(Color.white);
        jf.add(c);
        jf.setVisible(true);

        javax.swing.Timer t = new javax.swing.Timer(100, new ActionListener() {
            Random r = new Random();
            int arr[] = new int[c.lines.size() + 1];
            int prevArr[] = new int[c.lines.size() + 1];
            int bound
                    = (c.lines.size() - 1);

            @Override
            public void actionPerformed(ActionEvent e) {

                int howMany = r.nextInt(bound);

                for (int i = 0; i < howMany; i++) {
                    prevArr[i] = arr[i];
                    arr[i]
                            = r.nextInt(bound);
                }
                for (int i = 0; i < howMany; i++) {
                    c.lines.get(prevArr[i]).stopFetchLine();
                    c.lines.get(arr[i]).fetchLine();
                }

            }
        });
        t.start();
         
        //return c;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 102, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
