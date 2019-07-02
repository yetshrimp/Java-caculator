package homework;

public class DividedByZeroException extends Exception{
    public DividedByZeroException() {};

    public DividedByZeroException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return "divided by zero";
    }

}
