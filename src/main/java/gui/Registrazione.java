package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;

public class Registrazione extends JFrame {
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

    public Registrazione(Controller controller) {
        this.controller = controller;

        setContentPane(mainPanel);
        setTitle("Registrazione Nuovo Cliente");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        buttonConferma.addActionListener(e -> registraNuovoCliente());
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
            // Tenta il salvataggio immediato sul Database
            controller.aggiungiCliente(nuovoCliente);

            JOptionPane.showMessageDialog(this, "Registrazione completata con successo!\nOra puoi effettuare il login.", "Successo", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Chiude la schermata di registrazione

        } catch (Exception ex) {
            // Se il DB restituisce un errore (es. chiave primaria violata o errore SQL), viene intercettato qui
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio sul Database:\n" + ex.getMessage(), "Errore di Registrazione", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}