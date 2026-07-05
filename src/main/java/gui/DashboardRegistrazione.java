package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class DashboardRegistrazione extends JFrame {
    private JPanel mainPanel;
    private JLabel labelTitolo;
    private JLabel labelNome;
    private JTextField textNome;
    private JLabel labelCognome;
    private JTextField textCognome;
    private JLabel labelMail;
    private JTextField textMail;
    private JLabel labelPassword;
    private JPasswordField textPassword;
    private JButton buttonConferma;

    private Controller controller;

    /**
     * Crea una nuova istanza della dashboard di registrazione.
     * Inizializza l'interfaccia, configura i listener per il pulsante di conferma
     * e prepara la finestra per l'input dell'utente.
     *
     * @param controller il controller principale di sistema per la gestione dei dati.
     */
    public DashboardRegistrazione(Controller controller) {
        this.controller = controller;

        sistemaGrafica();

        setContentPane(mainPanel);
        setTitle("Registrazione Nuovo Cliente");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(540, 520);
        setMinimumSize(new Dimension(480, 460));
        setLocationRelativeTo(null);
        setVisible(true);

        buttonConferma.addActionListener(e -> registraNuovoCliente());
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(sfondoScuro);

        JPanel pannelloCentrale = new JPanel(new GridBagLayout());
        pannelloCentrale.setBackground(sfondoPannello);
        pannelloCentrale.setBorder(new EmptyBorder(25, 30, 25, 30));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        labelTitolo.setForeground(testoChiaro);
        labelTitolo.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelTitolo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel[] labels = {labelNome, labelCognome, labelMail, labelPassword};
        for (JLabel l : labels) {
            l.setForeground(testoChiaro);
            l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        }

        JTextField[] fields = {textNome, textCognome, textMail, textPassword};
        for (JTextField f : fields) {
            f.setBackground(sfondoCard);
            f.setForeground(testoChiaro);
            f.setCaretColor(testoChiaro);
            f.setFont(new Font("SansSerif", Font.PLAIN, 13));
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(grigioScuro, 1, true),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)
            ));
        }

        buttonConferma.setText("Conferma Registrazione");
        buttonConferma.setBackground(bluAcceso);
        buttonConferma.setForeground(testoChiaro);
        buttonConferma.setFocusPainted(false);
        buttonConferma.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonConferma.setPreferredSize(new Dimension(200, 38));
        buttonConferma.setFont(new Font("SansSerif", Font.BOLD, 12));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 8, 20, 8);
        pannelloCentrale.add(labelTitolo, c);

        c.insets = new Insets(6, 8, 6, 8);
        c.gridwidth = 1;

        c.gridx = 0; c.gridy = 1; c.weightx = 0.0; pannelloCentrale.add(labelNome, c);
        c.gridx = 1; c.weightx = 1.0; pannelloCentrale.add(textNome, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0.0; pannelloCentrale.add(labelCognome, c);
        c.gridx = 1; c.weightx = 1.0; pannelloCentrale.add(textCognome, c);

        c.gridx = 0; c.gridy = 3; c.weightx = 0.0; pannelloCentrale.add(labelMail, c);
        c.gridx = 1; c.weightx = 1.0; pannelloCentrale.add(textMail, c);

        c.gridx = 0; c.gridy = 4; c.weightx = 0.0; pannelloCentrale.add(labelPassword, c);
        c.gridx = 1; c.weightx = 1.0; pannelloCentrale.add(textPassword, c);

        JPanel panelBottone = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBottone.setOpaque(false);
        panelBottone.add(buttonConferma);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(20, 8, 0, 8);
        pannelloCentrale.add(panelBottone, c);

        mainPanel.add(pannelloCentrale, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void registraNuovoCliente() {
        String nome = textNome.getText().trim();
        String cognome = textCognome.getText().trim();
        String email = textMail.getText().trim();
        String password = new String(textPassword.getPassword()).trim();

        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Per favore, compila tutti i campi.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente nuovoCliente = new Cliente(nome, cognome, email, password);

        try {
            controller.aggiungiCliente(nuovoCliente);

            JOptionPane.showMessageDialog(this, "Registrazione completata con successo!\nOra puoi effettuare il login.", "Successo", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio sul Database:\n" + ex.getMessage(), "Errore di Registrazione", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}