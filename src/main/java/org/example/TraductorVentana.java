package org.example;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class TraductorVentana {

    private JFrame frmTraductorIngls;
    private JTextField sEspanol;
    private JTextField sResultado;
    private boolean modoOscuro = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TraductorVentana window = new TraductorVentana();
                    window.frmTraductorIngls.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public TraductorVentana() {
        initialize();
        setAppIcon();
    }
    private void setAppIcon(){
        ImageIcon icon = new ImageIcon("C:/Users/CFGS/Desktop/traductorv2.png");
        Image image = icon.getImage();
        frmTraductorIngls.setIconImage(image);
    }

    private void toggleDark(){
        modoOscuro=!modoOscuro;

        Color backgroundColor = modoOscuro ? Color.DARK_GRAY : Color.WHITE;
        Color foregroundColor = modoOscuro ? Color.WHITE : Color.DARK_GRAY;
        Color buttonColor = modoOscuro ? Color.LIGHT_GRAY : Color.BLACK;

        frmTraductorIngls.getContentPane().setBackground(backgroundColor);

        for(Component component : frmTraductorIngls.getContentPane().getComponents()) {
            if(component instanceof JLabel || component instanceof JTextField || component instanceof JButton){
                component.setForeground(foregroundColor);
                component.setBackground(backgroundColor);
            }
        }

        JButton btnTranslate = (JButton) frmTraductorIngls.getContentPane().getComponent(5);
        btnTranslate.setBackground(buttonColor);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frmTraductorIngls);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(frmTraductorIngls, "Archivo abierto: " + selectedFile.getName());
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frmTraductorIngls);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(frmTraductorIngls, "Archivo guardado: " + selectedFile.getName());
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmTraductorIngls = new JFrame();
        frmTraductorIngls.setTitle("TRADUCTOR INGLÉS - ESPAÑOL");
        frmTraductorIngls.setBounds(100, 100, 659, 425);
        frmTraductorIngls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmTraductorIngls.getContentPane().setLayout(null);
        frmTraductorIngls.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        frmTraductorIngls.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Archivo");
        menuBar.add(fileMenu);

        JMenuItem openItem = new JMenuItem("Abrir");
        openItem.addActionListener(e->openFile());
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Guardar");
        saveItem.addActionListener(e->saveFile());
        fileMenu.add(saveItem);

        JMenuItem menuOscuro = new JMenuItem("Modo oscuro");
        menuOscuro.addActionListener(e->toggleDark());
        fileMenu.add(menuOscuro);

        JMenuItem exitItem = new JMenuItem("Salir");
        exitItem.addActionListener(e->System.exit(0));
        fileMenu.add(exitItem);

        JLabel lblNewLabel = new JLabel("TRADUCTOR DAM");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(265, 39, 170, 34);
        lblNewLabel.setBackground(Color.black);
        frmTraductorIngls.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("INTRODUCE PALABRA A TRADUCIR");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(79, 142, 218, 14);
        frmTraductorIngls.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("PALABRA TRADUCIDA");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel_2.setForeground(new Color(0, 128, 255));
        lblNewLabel_2.setBackground(new Color(128, 255, 0));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(79, 232, 218, 14);
        frmTraductorIngls.getContentPane().add(lblNewLabel_2);

        sEspanol = new JTextField();
        sEspanol.setBounds(407, 122, 152, 34);
        frmTraductorIngls.getContentPane().add(sEspanol);
        sEspanol.setColumns(10);

        sResultado = new JTextField();
        sResultado.setSelectionColor(Color.RED);
        sResultado.setFont(new Font("Tahoma", Font.BOLD, 18));
        sResultado.setEnabled(false);
        sResultado.setBounds(407, 223, 152, 34);
        frmTraductorIngls.getContentPane().add(sResultado);
        sResultado.setColumns(10);

        JButton btnNewButton = new JButton("TRADUCIR");
        btnNewButton.setForeground(new Color(90,153,216));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {//botón traducir


                if(sEspanol.getText().isBlank()!=true ) {

                    String palabraESP=sEspanol.getText();
                    sResultado.setText(traducirING(palabraESP)); //escribir el resultado en la caja de texto "gris"

                }else {

                    JOptionPane.showMessageDialog(null, "No hay palabra para traducir", "Error", 0);


                }
            }
        });
        btnNewButton.setBackground(Color.black);
        btnNewButton.setBounds(265, 297, 121, 34);
        btnNewButton.setBorder(new MatteBorder(5,5,5,5,Color.red));
        frmTraductorIngls.getContentPane().add(btnNewButton);
    }

    protected String traducirING(String palabraESP) {	//función auxiliar para traducir

        String cadena=null;
        Document document=null;
        String webPage="https://www.ingles.com/traductor/"+palabraESP;
        try {
            document=Jsoup.connect(webPage).get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cadena= document.getElementById("quickdef1-es").getElementsByClass("tCur1iYh").html().toUpperCase();
        return cadena;
    }
}

