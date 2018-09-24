package com;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FormatMenu extends JMenu {
    public FormatMenu(Window wnd) {
        super("Format");

        add(new JMenuItem("Font..."));
        getItem(0).addActionListener(e -> { fontSelect(wnd); });
    }

    private void fontSelect(Window wnd) {
        JFontChooser fontChooser = new JFontChooser();
        fontChooser.setSelectedFont(((EditorPanel)(wnd.getContentPane())).getTextArea().getFont());

        int result = fontChooser.showDialog(null);
        if (result == JFontChooser.OK_OPTION)
        {
            ((EditorPanel)(wnd.getContentPane())).getTextArea().setFont(fontChooser.getSelectedFont());
            EditorPanel.globalFont = fontChooser.getSelectedFont();
        }
    }
}
