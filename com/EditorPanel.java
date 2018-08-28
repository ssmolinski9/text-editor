package com;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class EditorPanel extends JPanel {

    public static boolean unsavedChanges = true;

    private JTextArea textArea;
    private JScrollPane scrollPane;
    private File textFile;

    public EditorPanel() {
        super();

        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        textFile = null;

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));

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
