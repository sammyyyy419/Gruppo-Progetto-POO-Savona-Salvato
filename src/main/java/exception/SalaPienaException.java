package exception;


public class SalaPienaException extends Exception {
    /**
     * Crea una nuova istanza di {@link SalaPienaException} con il messaggio di errore specificato.
     *
     * @param message il messaggio che descrive la condizione di sala piena.
     */
    public SalaPienaException(String message) {
        super(message);
    }
}
