package org.example;

import java.awt.*;

import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class TraductorVentana {

    private JFrame frmTraductorIngls;
    private JTextField sEspanol;
    private JTextField sResultado;

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


        JLabel lblNewLabel = new JLabel("TRADUCTOR DAM");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(208, 39, 170, 34);
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
        btnNewButton.setBackground(Color.YELLOW);
        btnNewButton.setBounds(113, 297, 121, 34);
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

