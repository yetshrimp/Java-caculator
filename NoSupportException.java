package homework;

public class NoSupportException extends Exception{
    public NoSupportException() {};

    public NoSupportException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return "no support";
    }
}
