package exceptions;

public class CropNotFoundException extends Exception {
    public CropNotFoundException(String message) {
        super(message);
    }
}