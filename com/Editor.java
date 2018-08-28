package com;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

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

        setPreferredSize(window.getSize());
        add(textArea);

        textArea.addPropertyChangeListener("text", evt -> {
            if(textArea.getPreferredSize().height > window.getSize().height)
                setSize(textArea.getPreferredSize());

            System.out.println("Test");
        });

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                if(textArea.getPreferredSize().height >= window.getSize().height - textArea.getFontMetrics(textArea.getFont()).getHeight()) {
                    setPreferredSize(textArea.getPreferredSize());
                } else {
                    setPreferredSize(window.getSize());
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                if(textArea.getPreferredSize().height >= window.getSize().height - textArea.getFontMetrics(textArea.getFont()).getHeight()) {
                    setPreferredSize(textArea.getPreferredSize());
                } else {
                    setPreferredSize(window.getSize());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                if(textArea.getPreferredSize().height >= window.getSize().height - textArea.getFontMetrics(textArea.getFont()).getHeight()) {
                    setPreferredSize(textArea.getPreferredSize());
                } else {
                    setPreferredSize(window.getSize());
                }
            }
        });

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(textArea.getPreferredSize().height >= window.getSize().height - textArea.getFontMetrics(textArea.getFont()).getHeight()) {
                        try {
                            textArea.setCaretPosition(textArea.getLineStartOffset(textArea.getLineCount() - 1) - 1);
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
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

        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {

                if(((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().getPreferredSize().height >= window.getSize().height - ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().getFontMetrics(((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().getFont()).getHeight()) {
                    ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).setPreferredSize(((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().getPreferredSize());
                } else {
                    ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).setPreferredSize(window.getSize());
                }
            }
        });

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
        switch (orientation) {
            case SwingConstants.VERTICAL:
                return visibleRect.height / 10;
            case SwingConstants.HORIZONTAL:
                return visibleRect.width / 10;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch (orientation) {
            case SwingConstants.VERTICAL:
                return visibleRect.height;
            case SwingConstants.HORIZONTAL:
                return visibleRect.width;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
