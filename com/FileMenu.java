package com;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class FileMenu extends JMenu {
    public FileMenu() {
        super("File");

        add(new JMenuItem("New"));
        add(new JMenuItem("Open"));
        add(new JMenuItem("Save"));
        add(new JMenuItem("Save as"));
        add(new JMenuItem("Exit"));

        getItem(0).addActionListener(e -> { newFile(); });
        getItem(1).addActionListener(e -> { openFile(); });
        getItem(2).addActionListener(e -> { saveFile(); });
        getItem(3).addActionListener(e -> { saveAsFile(); });
        getItem(4).addActionListener(e -> { System.exit(0); });
    }

    private void newFile() {
        ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).setTextFile(null);
        Editor.window.setTitle("Text Editor - nowy plik");
        ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).clearText();
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).clearText();
            ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).setTextFile(fileChooser.getSelectedFile());

            Editor.window.setTitle("Text Editor - " + ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextFile().getName());

            try (BufferedReader br = new BufferedReader(new FileReader(((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextFile()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).appendText(line);
                    ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).appendText("\n");
                }
            } catch (IOException ignored) {}
        }
    }

    private void saveFile() {
        if(((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextFile() != null) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextFile()));
                ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().write(bw);
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setApproveButtonText("Save");
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName().endsWith(".txt")) {
                ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).setTextFile(new File(fileChooser.getSelectedFile().getAbsolutePath()));
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextFile()));
                    ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().write(bw);
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                Editor.window.setTitle("Text Editor - " + ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextFile().getName());
            }
        }
    }

    private void saveAsFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Save");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName().endsWith(".txt")) {
            ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).setTextFile(new File(fileChooser.getSelectedFile().getAbsolutePath()));
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextFile()));
                ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().write(bw);
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Editor.window.setTitle("Text Editor - " + ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextFile().getName());
        }
    }
}
