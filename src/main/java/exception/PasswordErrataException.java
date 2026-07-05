package exception;


public class PasswordErrataException extends RuntimeException {

    /**
     * Crea una nuova istanza di {@link PasswordErrataException} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'errore di autenticazione o validazione.
     */
    public PasswordErrataException(String message) {

        super(message);
    }
}
