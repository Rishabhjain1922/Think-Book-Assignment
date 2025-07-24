package Think41.message.exception.exceptions;

public class ConnectionNotFoundException extends RuntimeException {
    public ConnectionNotFoundException(Long id) {
        super("Connection not found with ID: " + id);
    }
}