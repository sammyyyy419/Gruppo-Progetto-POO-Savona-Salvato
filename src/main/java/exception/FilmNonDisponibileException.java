package exception;

public class FilmNonDisponibileException extends RuntimeException {
    /**
     * Crea una nuova istanza di {@link FilmNonDisponibileException} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la causa dell'eccezione.
     */
    public FilmNonDisponibileException(String message) {
        super(message);
    }
}
