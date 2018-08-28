package com;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditMenu extends JMenu {
    public EditMenu(Window wnd) {
        super("Edit");

        add(new JMenuItem("Cut"));
        add(new JMenuItem("Copy"));
        add(new JMenuItem("Paste"));
        add(new JMenuItem("Delete"));

        getItem(0).addActionListener(e -> { cut(wnd); });
        getItem(1).addActionListener(e -> { copy(wnd); });
        getItem(2).addActionListener(e -> { paste(wnd); });
        getItem(3).addActionListener(e -> { delete(wnd); });

        getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        getItem(1).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        getItem(2).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    }

    private void cut(Window wnd) {
        String copiedText =   ((EditorPanel)(wnd.getContentPane())).getTextArea().getSelectedText();
        ((EditorPanel)(wnd.getContentPane())).getTextArea().replaceSelection("");

        StringSelection stringSelection = new StringSelection(copiedText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void copy(Window wnd) {
        String copiedText =   ((EditorPanel)(wnd.getContentPane())).getTextArea().getSelectedText();
        StringSelection stringSelection = new StringSelection(copiedText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void paste(Window wnd) {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(this);
        if (t == null)
            return;
        try {
            ((EditorPanel)(wnd.getContentPane())).getTextArea().append((String) t.getTransferData(DataFlavor.stringFlavor));
        } catch (Exception ignored) {}
    }

    private void delete(Window wnd) {
        ((EditorPanel)(wnd.getContentPane())).getTextArea().replaceSelection("");
    }
}
