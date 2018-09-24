package com;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EditorPanel extends JPanel {

    public static boolean unsavedChanges = false;
    public static Font globalFont;

    private JTextArea textArea;
    private File textFile;

    public EditorPanel() {
        super();

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textFile = null;

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(System.getenv("APPDATA") + File.separator + "text-editor" + File.separator + "config.properties");

            // load a properties file
            prop.load(input);

            globalFont = new Font(prop.getProperty("font_name"), Integer.parseInt(prop.getProperty("font_style")), Integer.parseInt(prop.getProperty("font_size")));
        } catch (IOException ex) {
            globalFont = new Font("Times New Roman", Font.PLAIN, 14);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(globalFont);

        setLayout(new BorderLayout());
        setActionListener();

        add(scrollPane, BorderLayout.CENTER);
    }

    public void clearText() { textArea.setText(""); }
    public void appendText(String text) { textArea.append(text); }

    public File getTextFile() { return textFile; }
    public void setTextFile(File textFile) { this.textFile = textFile; }

    public JTextArea getTextArea() {
        return textArea;
    }

    private void setActionListener() {
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                unsavedChanges = true;
            }
        });
    }
}
