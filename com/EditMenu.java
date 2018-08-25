package com;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class EditMenu extends JMenu {
    public EditMenu() {
        super("Edit");

        add(new JMenuItem("Cut"));
        add(new JMenuItem("Copy"));
        add(new JMenuItem("Paste"));
        add(new JMenuItem("Delete"));

        getItem(0).addActionListener(e -> { cut(); });
        getItem(1).addActionListener(e -> { copy(); });
        getItem(2).addActionListener(e -> { paste(); });
        getItem(3).addActionListener(e -> { delete(); });
    }

    private void cut() {
        String copiedText =  ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().getSelectedText();
        ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().replaceSelection("");

        StringSelection stringSelection = new StringSelection(copiedText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void copy() {
        String copiedText =  ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().getSelectedText();
        StringSelection stringSelection = new StringSelection(copiedText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void paste() {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(this);
        if (t == null)
            return;
        try {
            ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().append((String) t.getTransferData(DataFlavor.stringFlavor));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void delete() {
        ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().replaceSelection("");
    }
}
