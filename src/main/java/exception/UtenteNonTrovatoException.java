package exception;

public class UtenteNonTrovatoException extends Exception {

    /**
     * Crea una nuova istanza di {@link UtenteNonTrovatoException} con il messaggio di errore specificato.
     *
     * @param messaggio il messaggio che descrive i dettagli dell'utente non trovato.
     */
    public UtenteNonTrovatoException(String messaggio) {

        super(messaggio);

    }
}