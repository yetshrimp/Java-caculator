package homework;

public class InvalidInputException extends Exception{
    public InvalidInputException() {};

    public InvalidInputException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return "invalid input";
    }
}
