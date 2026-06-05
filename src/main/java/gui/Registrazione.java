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
    private JTextField textCognome; // Nome aggiornato!
    private JLabel labelMail;
    private JTextField textMail;
    private JLabel labelPassword;
    private JPasswordField textPassword; // Nome aggiornato!
    private JButton buttonConferma;

    private Controller controller;

    public Registrazione(Controller controller) {
        this.controller = controller;

        setContentPane(mainPanel);
        setTitle("Registrazione Nuovo Cliente");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Chiude SOLO questa finestra, lasciando aperta la Home
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Azione del bottone conferma
        buttonConferma.addActionListener(e -> registraNuovoCliente());
    }

    private void registraNuovoCliente() {
        // 1. Prendi i dati inseriti dall'utente usando i nuovi nomi
        String nome = textNome.getText().trim();
        String cognome = textCognome.getText().trim();
        String email = textMail.getText().trim();
        String password = new String(textPassword.getPassword()).trim();

        // 2. Controllo di sicurezza: verifica che i campi non siano vuoti
        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Per favore, compila tutti i campi.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return; // Interrompe il metodo se manca qualcosa
        }

        // 3. Crea il nuovo cliente
        Cliente nuovoCliente = new Cliente(nome, cognome, email, password);

        // 4. Passa il cliente al controller per salvarlo nella lista
        controller.aggiungiCliente(nuovoCliente);

        // 5. Messaggio di successo e chiusura finestra
        JOptionPane.showMessageDialog(this, "Registrazione completata con successo!\nOra puoi effettuare il login.", "Successo", JOptionPane.INFORMATION_MESSAGE);

        // Chiude la finestra di registrazione.
        this.dispose();
    }
}