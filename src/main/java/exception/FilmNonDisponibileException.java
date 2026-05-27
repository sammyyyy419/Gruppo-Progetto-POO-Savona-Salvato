package exception;

public class FilmNonDisponibileException extends RuntimeException {
    public FilmNonDisponibileException(String message) {
        super(message);
    }
}
