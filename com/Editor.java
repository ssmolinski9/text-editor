package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Editor extends JPanel implements Scrollable {

    public static JFrame window;

    private JTextArea textArea;
    private static File textFile = null;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    public Editor() {
        super();

        setLayout(new BorderLayout());
        setBorder(new EtchedBorder());

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        add(textArea);
    }

    public void clearText() {
        textArea.setText("");
    }

    public void appendText(String text) {
        textArea.append(text);
    }

    public JTextArea getTextArea() { return textArea; }

    public File getTextFile() { return textFile; }

    public void setTextFile(File file) { textFile = file; }

    public static void main(String[] args) {
        window = new JFrame();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        window.setTitle("Text Editor - new file");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(WIDTH, HEIGHT));
        window.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - window.getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - window.getHeight() / 2);

        window.setContentPane(new JScrollPane(new Editor()));

        window.setJMenuBar(new JMenuBar());
        window.getJMenuBar().add(new FileMenu());
        window.getJMenuBar().add(new EditMenu());
        window.getJMenuBar().add(new FormatMenu());

        String imagePath = "/res/icon.png";
        InputStream imgStream = Editor.class.getResourceAsStream(imagePath);
        try {
            BufferedImage myImg = ImageIO.read(imgStream);
            window.setIconImage(myImg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        window.setVisible(true);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return null;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 0;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 0;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return true;
    }
}
