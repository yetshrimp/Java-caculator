 package homework;

public class Cal {
    /* 支持的运算 */
    private static final String[] func = new String[] {"+","-","x","/","%","mod","sin","cos","tan","arcsin","arccos","arctan","exp","log","x^y","log10","factorial","dms","deg","toradians","todegrees","+/-","10^x","x^2","1/x","√","y√x","pi","(",")","=","."};
    /* 一元运算符实际不会比较优先级 */
    private static final int[] prio = new int[] {0,0,1,1,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,0,-1,2};
    /* 有部分运算符不直接对应函数 */
    private static final int prioNum = 32;

    /* 优先级比较 */
    public static int compare(String op1, String op2) {
        int index1, index2;

        for (index1 = 0; index1 < prioNum; index1++) {
            if (func[index1].equals(op1))
                break;
        }

        for (index2 = 0; index2 < prioNum; index2++) {
            if (func[index2].equals(op2))
                break;
        }

        return prio[index1] - prio[index2];
    }

    /* 统一处理运算符的函数 */
    public static double deal(String mode,double... X) throws InvalidInputException, DividedByZeroException, NoSupportException{
        if (mode.equals(func[0]))
            return add(X[0],X[1]);

        else if (mode.equals(func[1]))
            return sub(X[0],X[1]);

        else if (mode.equals(func[2]))
            return mul(X[0],X[1]);

        else if (mode.equals(func[3])) {
           if (X[1] == 0)
               throw new DividedByZeroException();

           else
               return div(X[0],X[1]);
        }

        else if (mode.equals(func[4])) {
            if (X[1] == 0)
                throw new DividedByZeroException();

            return com(X[0], X[1]);
        }

        else if (mode.equals(func[5])) {
            if (X[1] == 0)
                throw new DividedByZeroException();

            return mod(X[0], X[1]);
        }

        else if (mode.equals(func[6])) {
            Double tmp = sin(toradians(X[0]));
            return Math.abs(tmp - 1) < 1e-5 ? 1 : Math.abs(tmp - 0) < 1e-5 ? 0 : tmp;

        }

        else if (mode.equals(func[7])) {
            Double tmp = cos(toradians(X[0]));
            return Math.abs(tmp - 1) < 1e-5 ? 1 : Math.abs(tmp - 0) < 1e-5 ? 0 : tmp;
        }

        else if (mode.equals(func[8])) {
            if (X[0] % 360 == 90 || X[0] % 360 == 270)
                throw new InvalidInputException();
            else {
                Double tmp = tan(toradians(X[0]));
                return Math.abs(tmp - 1) < 1e-5 ? 1 : Math.abs(tmp - 0) < 1e-5 ? 0 : tmp;
            }
        }

        else if (mode.equals(func[9])) {
            if (X[0] < - 1 || X[0] > 1)
                throw new InvalidInputException();
            else
                return todegrees(arcsin(X[0]));
        }

        else if (mode.equals(func[10])) {
            if (X[0] < - 1 || X[0] > 1)
                throw new InvalidInputException();
            else
                return todegrees(arccos(X[0]));
        }

        else if (mode.equals(func[11])) {
            if (X[0] < - 1 || X[0] > 1)
                throw new InvalidInputException();
            else
                return todegrees(arctan(X[0]));
        }

        else if (mode.equals(func[12]))
            return exp(X[0]);

        else if (mode.equals(func[13])) {
            if (X[0] <= 0)
                throw new InvalidInputException();

            else
                return log(X[0]);
        }

        else if (mode.equals(func[14])) {
            if (X[0] == 0 && X[1] < 0)
                throw new InvalidInputException();

            /* 事实上应该加上分子是奇数、分母是偶数的条件，暂时未能实现 */
            else if (X[0] < 0 && X[1] < 1 && X[1] > -1)
                throw new InvalidInputException();

            else
                return pow(X[0], X[1]);
        }

        else if (mode.equals(func[15])) {
            if (X[0] <= 0)
                throw new InvalidInputException();

            else
                return log10(X[0]);
        }

        else if (mode.equals(func[16])) {
            if (X[0] < 0)
                throw new InvalidInputException();

            else {
                int n = (int) X[0];
                return factorial(n);
            }
        }

        else if (mode.equals(func[17]))
            return dms(X[0]);

        else if (mode.equals(func[18]))
            return deg(X[0]);

        else if (mode.equals(func[19]))
            return toradians(X[0]);

        else if (mode.equals(func[20]))
            return todegrees(X[0]);

        else if (mode.equals(func[21]))
            return minus(X[0]);

        else if (mode.equals(func[22]))
            return pow(10,X[0]);

        else if (mode.equals(func[23]))
            return pow(X[0],2);

        else if (mode.equals(func[24])) {
            if (X[0] == 0)
                throw new InvalidInputException();

            else
                return pow(X[0],-1);
        }

        else if (mode.equals(func[25])) {
            if (X[0] < 0)
                throw new InvalidInputException();

            else
                return pow(X[0], 0.5);
        }

        else if (mode.equals(func[26])) {
            if (X[0] < 0 && X[1] % 2 == 0)
                throw new InvalidInputException();

            else
                return pow(X[0],1 / X[1]);
        }

        else if (mode.equals(func[27]))
            return 3.14159265;

        else
            throw new NoSupportException();

    }

    private static double add(double x, double y) {
        return x + y;
    }

    private static double sub(double x, double y) {
        return x - y;
    }

    private static double mul(double x, double y) {
        return x * y;
    }

    private static double div(double x, double y) {
        return x / y;
    }

    /* 注意取模和取余的区别，-3%5=-3，-3mod5=2 */
    private static double com(double x, double y) {
        int tx, ty, tquotient;
        double tproduct;

        tx = (int) x;
        ty = (int) y;

        tquotient = tx / ty;
        tproduct = y * tquotient;

        return x - tproduct;
    }

    private static double mod(double x, double y) {
        int tx, ty, tquotient;
        double tproduct;

        tx = (int) x;
        ty = (int) y;

        tquotient = tx / ty;
        tproduct = y * tquotient;

        if (tproduct <= 0 && tproduct > tx)
            tproduct = tproduct - y;

        return x - tproduct;
    }

    private static double sin(double x) {
        return Math.sin(x);
    }

    private static double cos(double x) {
        return Math.cos(x);
    }

	private static double tan(double x) {
        return Math.tan(x);
    }

    private static double arcsin(double x) {
        return Math.asin(x);
    }

    private static double arccos(double x) {
        return Math.acos(x);
    }

    private static double arctan(double x) {
        return Math.atan(x);
    }

    private static double exp(double x) {
        return Math.exp(x);
    }

    private static double log(double x) {
        return Math.log(x);
    }

    private static double pow(double x, double y) {
        return Math.pow(x, y);
    }

    private static double log10(double x) {
        return Math.log10(x);
    }

    /* 非广义阶乘 */
    private static int factorial(int x) {
    	int y = 1;
    	while (x > 0) {
    	    y = y * x;
    	    x = x - 1;
        }

        return y;
    }

    /* 度-分-秒 */
    private static double dms(double x) {
        int degree = (int) x;
        int minute = (int) ((x - degree) * 60);
        double second = (x - degree - minute / 60.0) * 3600;

        return degree + minute / 100.0 + second / 10000;
    }

    /* 度-分-秒 */
    private static double deg(double x) {
        int degree = (int) x;
        int minute = (int) ((x - degree) * 100);
        double second = (x - degree - minute / 100.0) * 10000;

        return degree + minute / 60.0 + second / 3600;
    }

    /* 弧度-角度 */
    private static double toradians(double x) {
        return Math.toRadians(x);
    }

    /* 弧度-角度 */
    private static double todegrees(double x) {
        return Math.toDegrees(x);
    }
    
    private static double minus(double x) { return -1 * x; }
}

