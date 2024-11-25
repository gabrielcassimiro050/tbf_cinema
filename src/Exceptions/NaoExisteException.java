package Exceptions;

public class NaoExisteException extends RuntimeException {
    public NaoExisteException(String message) {
        super(message);
    }
}
