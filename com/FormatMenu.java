package com;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

public class FormatMenu extends JMenu {
    public FormatMenu() {
        super("Format");

        add(new JMenuItem("Font..."));

        getItem(0).addActionListener(e -> { fontSelect(); });
    }
    private void fontSelect() {
        JFontChooser fontChooser = new JFontChooser();
        fontChooser.setSelectedFont(((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().getFont());

        int result = fontChooser.showDialog(null);
        if (result == JFontChooser.OK_OPTION)
        {
            ((Editor)((JScrollPane)Editor.window.getContentPane()).getViewport().getView()).getTextArea().setFont(fontChooser.getSelectedFont());
        }
    }
}
