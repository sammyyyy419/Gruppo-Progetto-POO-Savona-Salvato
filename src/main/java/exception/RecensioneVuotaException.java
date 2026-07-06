package exception;


public class RecensioneVuotaException extends Exception {/**
 * Crea una nuova istanza di {@link RecensioneVuotaException} con il messaggio di errore specificato.
 *
 * @param message il messaggio che descrive la natura del problema riscontrato con la recensione.
 */
    public RecensioneVuotaException(String message) {
        super(message);
    }
}
