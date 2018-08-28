package com;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class FileMenu extends JMenu {
    public FileMenu(Window wnd) {
        super("File");

        add(new JMenuItem("New"));
        add(new JMenuItem("Open"));
        add(new JMenuItem("Save"));
        add(new JMenuItem("Save as"));
        add(new JMenuItem("Exit"));

        getItem(0).addActionListener(e -> { newFile(wnd); });
        getItem(1).addActionListener(e -> { openFile(wnd); });
        getItem(2).addActionListener(e -> { saveFile(wnd); });
        getItem(3).addActionListener(e -> { saveAsFile(wnd); });
        getItem(4).addActionListener(e -> { setCustomCloseOperation(wnd); });

        getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        getItem(1).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        getItem(2).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    }

    private void newFile(Window wnd) {
        ((EditorPanel)wnd.getContentPane()).setTextFile(null);
        ((EditorPanel)wnd.getContentPane()).clearText();

        wnd.setTitle("Text Editor - new file");
    }

    private void openFile(Window wnd) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            ((EditorPanel)(wnd.getContentPane())).clearText();
            ((EditorPanel)(wnd.getContentPane())).setTextFile(fileChooser.getSelectedFile());

            wnd.setTitle("Text Editor - " +  ((EditorPanel)(wnd.getContentPane())).getTextFile().getName());

            try (BufferedReader br = new BufferedReader(new FileReader(((EditorPanel)(wnd.getContentPane())).getTextFile()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    ((EditorPanel)(wnd.getContentPane())).appendText(line);
                    ((EditorPanel)(wnd.getContentPane())).appendText("\n");
                }
            } catch (IOException ignored) {}
        }
    }

    public void saveFile(Window wnd) {
        if(((EditorPanel)(wnd.getContentPane())).getTextFile() != null) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(((EditorPanel)(wnd.getContentPane())).getTextFile()));
                ((EditorPanel)(wnd.getContentPane())).getTextArea().write(bw);
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            saveAsFile(wnd);
        }

        ((EditorPanel)(wnd.getContentPane())).unsavedChanges = false;
    }

    private void saveAsFile(Window wnd) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Save");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if(fileChooser.getSelectedFile().getName().endsWith(".txt"))
                ((EditorPanel)(wnd.getContentPane())).setTextFile(new File(fileChooser.getSelectedFile().getAbsolutePath()));
            else
                ((EditorPanel)(wnd.getContentPane())).setTextFile(new File(fileChooser.getSelectedFile().getAbsolutePath() + ".txt"));

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(((EditorPanel)(wnd.getContentPane())).getTextFile()));
                ((EditorPanel)(wnd.getContentPane())).getTextArea().write(bw);
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            ((EditorPanel)(wnd.getContentPane())).unsavedChanges = false;
            wnd.setTitle("Text Editor - " + (((EditorPanel)(wnd.getContentPane())).getTextFile().getName()));
        }
    }

    private void setCustomCloseOperation(Window wnd) {
        Object[] options = {"Yes",
                "No",
                "Cancel"};

        int n = ((EditorPanel)wnd.getContentPane()).unsavedChanges ? JOptionPane.showOptionDialog(wnd,
                "Do you want to save changes?",
                "Text Editor",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]) : 1;

        if(n == 0)  {
            saveFile(wnd);
            wnd.dispose();
            System.exit(0);
        } else if(n == 1) {
            wnd.dispose();
            System.exit(0);
        }
    }
}
