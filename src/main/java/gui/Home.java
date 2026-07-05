package gui;

import controller.Controller;
import model.Utente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The type Home.
 */
public class Home extends JFrame {
    private JPanel mainPanel;
    private JLabel labelTitolo;
    private JLabel labelEmail;
    private JTextField textEmail;
    private JLabel labelPassword;
    private JPasswordField textPassword;
    private JButton buttonRegistra;
    private JButton buttonAccedi;
    private Controller controller;

    /**
     * Instantiates a new Home.
     *
     * @param controller the controller
     */
    public Home(Controller controller) {
        this.controller = controller;

        setTitle("Enterprise Cinema - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        sistemaGrafica();

        setContentPane(mainPanel);

        setSize(640, 580);
        setMinimumSize(new Dimension(580, 530));

        setLocationRelativeTo(null);
        setVisible(true);

        buttonAccedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eseguiLogin();
            }
        });

        buttonRegistra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DashboardRegistrazione(controller);
            }
        });
    }

    private void sistemaGrafica() {
        Color pannello = new Color(28, 34, 58);
        Color testo = Color.WHITE;
        Color campo = new Color(235, 240, 250);
        Color blu = new Color(70, 120, 255);
        Color viola = new Color(126, 87, 194);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(pannello);

        applicaLogo();

        labelTitolo.setText("Benvenuti in Enterprise Cinema!");
        labelTitolo.setForeground(testo);
        labelTitolo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitolo.setHorizontalTextPosition(SwingConstants.CENTER);
        labelTitolo.setVerticalTextPosition(SwingConstants.BOTTOM);
        labelTitolo.setIconTextGap(15);

        mainPanel.add(labelTitolo, BorderLayout.NORTH);

        JPanel pannelloForm = new JPanel();
        pannelloForm.setBackground(pannello);
        pannelloForm.setLayout(new GridBagLayout());
        pannelloForm.setBorder(new EmptyBorder(15, 35, 25, 35));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        labelEmail.setText("Email :");
        labelEmail.setForeground(testo);

        labelPassword.setText("Password :");
        labelPassword.setForeground(testo);
        textEmail.setBackground(campo);
        textEmail.setForeground(Color.BLACK);
        textEmail.setPreferredSize(new Dimension(320, 38));

        textPassword.setBackground(campo);
        textPassword.setForeground(Color.BLACK);
        textPassword.setPreferredSize(new Dimension(320, 38));

        buttonRegistra.setText("Registrati");
        buttonRegistra.setBackground(viola);
        buttonRegistra.setForeground(Color.WHITE);
        buttonRegistra.setFocusPainted(false);
        buttonRegistra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonRegistra.setPreferredSize(new Dimension(160, 42));

        buttonAccedi.setText("Accedi");
        buttonAccedi.setBackground(blu);
        buttonAccedi.setForeground(Color.WHITE);
        buttonAccedi.setFocusPainted(false);
        buttonAccedi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAccedi.setPreferredSize(new Dimension(160, 42));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        pannelloForm.add(labelEmail, c);

        c.gridx = 1;
        pannelloForm.add(textEmail, c);

        c.gridy = 1;
        c.gridx = 0;
        pannelloForm.add(labelPassword, c);

        c.gridx = 1;
        pannelloForm.add(textPassword, c);

        JPanel pannelloBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pannelloBottoni.setBackground(pannello);
        pannelloBottoni.add(buttonRegistra);
        pannelloBottoni.add(buttonAccedi);

        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(20, 10, 10, 10);
        pannelloForm.add(pannelloBottoni, c);

        mainPanel.add(pannelloForm, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void applicaLogo() {
        String pathLogo = "locandine/logo_enterprise.png";
        File fileLogo = new File(pathLogo);

        if (fileLogo.exists()) {
            ImageIcon logoOriginale = new ImageIcon(pathLogo);
            Image logoRidimensionato = logoOriginale.getImage().getScaledInstance(420, 190, Image.SCALE_SMOOTH);
            labelTitolo.setIcon(new ImageIcon(logoRidimensionato));
        } else {
            labelTitolo.setText("Benvenuti in Enterprise Cinema!");
        }
    }

    private void eseguiLogin() {
        String email = textEmail.getText().trim();
        String password = new String(textPassword.getPassword()).trim();

        try {
            controller.validaLogin(email, password);

            Utente u = controller.recuperaUtente(email);

            if (u instanceof model.Dipendente) {
                new DashboardDipendente(this.controller, (model.Dipendente) u).setVisible(true);
                this.dispose();
            } else if (u instanceof model.Cliente) {
                new DashboardCliente(this.controller, (model.Cliente) u).setVisible(true);
                this.dispose();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Errore di Accesso",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}