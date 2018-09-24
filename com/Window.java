package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;

public class Window extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;


    public Window(String[] args) {
        super();

        setTitle("Text Editor - new file");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2);

        setWindowIcon();
        setLookAndFeel();
        setCustomCloseOperation(this);
        setContentPane(new EditorPanel());
        createMenu();
        createAppdataFolder();

        loadFileFromArgs(args);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Window(args);
    }

    private void createMenu() {
        this.setJMenuBar(new JMenuBar());
        this.getJMenuBar().add(new FileMenu(this));
        this.getJMenuBar().add(new EditMenu(this));
        this.getJMenuBar().add(new FormatMenu(this));
    }

    private void setWindowIcon() {
        String imagePath = "/res/icon.png";
        InputStream imgStream = Window.class.getResourceAsStream(imagePath);
        try {
            BufferedImage myImg = ImageIO.read(imgStream);
            this.setIconImage(myImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFileFromArgs(String [] args) {
        if(args.length > 0 && args[0].endsWith(".txt")) {
            ((EditorPanel)(getContentPane())).setTextFile(new File(args[0]));

            setTitle("Text Editor - " +  ((EditorPanel)(getContentPane())).getTextFile().getName());
            try (BufferedReader br = new BufferedReader(new FileReader(((EditorPanel)(getContentPane())).getTextFile()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    ((EditorPanel)(getContentPane())).appendText(line);
                    ((EditorPanel)(getContentPane())).appendText("\n");
                }
            } catch (IOException ignored) {}
        }
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setCustomCloseOperation(Window parent) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                Object[] options = {"Yes",
                        "No",
                        "Cancel"};

                int n = ((EditorPanel)parent.getContentPane()).unsavedChanges ? JOptionPane.showOptionDialog(parent,
                        "Do you want to save changes?",
                        "Text Editor",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]) : 1;

                Properties prop = new Properties();
                OutputStream output = null;

                try {
                    output = new FileOutputStream(System.getenv("APPDATA") + File.separator + "text-editor" + File.separator + "config.properties");

                    // set the properties value
                    prop.setProperty("font_size", EditorPanel.globalFont.getSize()+"");
                    prop.setProperty("font_name", EditorPanel.globalFont.getFontName()+"");
                    prop.setProperty("font_style", EditorPanel.globalFont.getStyle()+"");

                    // save properties to project root folder
                    prop.store(output, null);

                } catch (IOException io) {
                    io.printStackTrace();
                } finally {
                    if (output != null) {
                        try {
                            output.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                if(n == 0)  {
                    getJMenuBar().getMenu(0).getItem(2).doClick();
                    parent.dispose();
                    System.exit(0);
                } else if(n == 1) {
                    parent.dispose();
                    System.exit(0);
                }
            }
        });
    }

    private void createAppdataFolder() {
        new File(System.getenv("APPDATA") + File.separator + "text-editor").mkdirs();
    }
}
