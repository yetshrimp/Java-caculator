package homework;

import java.util.ArrayList;

public class Cont {
    /* 表达式数组 */
    private ArrayList<String> expression = null;
    private int elen;

    /* 二元操作符数组 */
    private ArrayList<String> toperators = null;

    /* 结果数组 */
    private ArrayList<Double> results = null;
    private int len;

    /* 历史 */
    private String hisexpression;
    private String hisresult;

    /* 结果字符串 */
    private String showNumber;

    /* 数字标记 */
    private boolean isNumber;
    private int multiple;

    /* 小数标记 */
    private boolean isDouble;
    private int scale;

    /* 一元操作符标记，指是否允许一元操作符正常出现 */
    private boolean isOp;

    /* 二元操作符标记，指是否允许二元操作符正常出现 */
    private boolean isTop;

    /* 括号标记，指是否允许括号正常出现。对于右括号而言，还需要判断括号数是否匹配 */
    private boolean islBra;
    private boolean isrBra;
    private int blen;

    /* 错误标记 */
    private boolean isErr;
    private String emess;

    /* 结束标记 */
    private boolean isEnd;

    public Cont() {
        expression = new ArrayList<String>();
        elen = 0;

        toperators = new ArrayList<String>();
        toperators.add("+");

        results = new ArrayList<Double>();
        results.add(0.0);
        len = 1;

        hisexpression = null;
        hisresult = null;

        showNumber = "0";

        isNumber = false;
        multiple = 10;

        isDouble = false;
        scale = 10;

        isOp = true;
        isTop = false;

        islBra = true;
        isrBra = false;
        blen = 0;

        isErr = false;
        isEnd = false;
    }

    public ArrayList<String> getExpression() {
        return expression;
    }

    public ArrayList<Double> getResults() {
        return results;
    }

    public ArrayList<String> getToperators() {
        return toperators;
    }

    public String getHisexpression() {
        return hisexpression;
    }

    public String getHisresult() {
        return hisresult;
    }

    public String getShowNumber() {
        return showNumber;
    }

    public int getElen() {
        return elen;
    }

    public int getLen() {
        return len;
    }

    public int getBlen() {
        return blen;
    }

    public boolean getislBra() {
        return islBra;
    }

    public boolean getisrBra() {
        return isrBra;
    }

    public boolean getisErr() {
        return isErr;
    }

    public boolean getisEnd() {
        return isEnd;
    }

    /* 清空表达式，等式计算后调用 */
    public void expflush() {
        expression.clear();
        expression.add(showNumber);
        elen = 1;
        isNumber = false;
        multiple = 10;
        isDouble = false;
        scale = 10;
        isOp = true;
        isTop = true;
        islBra = false;
        isrBra = false;
        blen = 0;
        isErr = false;
        isEnd = false;
    }

    /* 更新历史数据 */
    public void hisUpdate() {
        /* String是不可变对象，在循环中使用+串接字符串实际上生成了大量新对象,StringBuffer(速度更快)/StringBuilder(线程安全)则是可变对象 */
        StringBuilder tmp = new StringBuilder();

        for (int i = 0; i < elen; i++) {
            tmp.append(expression.get(i));
        }

        hisexpression = tmp.toString();
        hisresult = showNumber;
    }

    /* 历史返回 */
    public void hisback(String hexpression, String hresult) {
        clearAll();
        expression.add(hexpression);
        elen = elen + 1;
        results.add(Double.parseDouble(hresult));
        len = len + 1;
    }

    /* 清空错误 */
    public void clearError() {
        if (isErr)
            clearAll();
    }

    /* 清空面板 */
    public void clearAll() {
        expression.clear();
        elen = 0;
        toperators.clear();
        toperators.add("+");
        results.clear();
        results.add(0.0);
        len = 1;
        showNumber = "0";
        isNumber = false;
        multiple = 10;
        isDouble = false;
        scale = 10;
        isOp = true;
        isTop = false;
        islBra = true;
        isrBra = false;
        blen = 0;
        isErr = false;
        isEnd = false;
    }

    /* 清空上一步的数字输入 */
    public void clear() {
        if (isNumber) {
            if (len > 0) {
                results.remove(len - 1);
                len = len - 1;
                showNumber = "0";
                isDouble = false;
                scale = 10;
            }
        }
    }

    /* 修改结果字符串显示 */
    private String decorate(String Number) {
        String outNumber = Number;

        /* 输出错误信息 */
        if (isErr)
            outNumber = emess;

        /* 去除多余的零 */
        if (Number.indexOf('.') > 0) {
            outNumber = outNumber.replaceAll("0+?$","");
            outNumber = outNumber.replaceAll("[.]$","");
        }

        return outNumber;
    }

    /* 括号表达式浓缩 */
    private void bracketCom() {
        int i = elen - 1;
        StringBuilder tmp = new StringBuilder(100);

        /* 越界或遇到第一个左括号结束查找 */
        while (i >= 0 && !expression.get(i).equals("("))
            i = i - 1;

        /* 遇到左括号可以进行括号表达式浓缩 */
        if (i >= 0) {
            int k;
            /* 将括号即其内部的表达式按顺序放入StringBuilder */
            for (k = i; k < elen; k++) {
                tmp.append(expression.get(i));
                expression.remove(i);
            }

            /* k - i：括号表达式长度 */
            elen = elen - (k - i);

            /* 将括号表达式浓缩方便后续操作 */
            expression.add(tmp.toString());
            elen = elen + 1;
        }
    }

    /* 计算器输入数字 */
    public void obtainNumber(String snumber) {
        /* 按照数学表达式规则开放/禁止相应操作符出现 */

        if (!isOp)
            isOp = true;

        if (!isTop)
            isTop = true;

        if (islBra)
            islBra = false;

        /* pi视为常量 */
        if (snumber.equals("pi")) {
            try {
                double tmp = Cal.deal(snumber);
                results.set(len - 1, tmp);
                showNumber = String.valueOf(tmp);
                isNumber = true;
            }

            catch (Exception e) {
                isErr = true;
                emess = e.toString();
            }

        }

        else {
            Double number = Double.parseDouble(snumber);

            /* 处理小数 */
            if (isDouble) {
                /* 计算最新值 */
                double tmp = results.get(len - 1) + number / scale;
                results.set(len - 1, tmp);
                scale = scale * 10;

                /* 不允许溢出 */
                if (scale < 0) {
                    clearAll();
                    showNumber = "overflow";
                    return;
                }

                try {
                    /* 位数其实多加了1，方便比较 */
                    int digit = (int) Cal.deal("log10", scale);

                    showNumber = String.valueOf(tmp);
                    int index = showNumber.indexOf('.');
                    int slen = showNumber.length() - index;

                    /* 截断处理 */
                    if (slen > digit)
                        showNumber = showNumber.substring(0, index + digit + 1);
                }

                catch (Exception e) {
                    isErr = true;
                    emess = e.toString();
                }
            }

            /* 处理两位数及以上整数 */
            else if (isNumber) {
                /* 计算最新值 */
                double tmp = results.get(len - 1) * multiple + number;
                results.set(len - 1, tmp);
                showNumber = String.valueOf(tmp);
            }

            /* 处理个位数 */
            else {
                results.add(number);
                len = len + 1;
                showNumber = snumber;
                isNumber = true;
            }

        }

        /* 修整输出 */
        showNumber = decorate(showNumber);

    }

    /* 计算器输入一元运算符 */
    public void obtainOperator(String operator) {
        /* 按照数学表达式规则开放/禁止相应操作符出现 */

        /* 清除错误的二元操作符和左括号 */
        if (!isOp) {
            int tlen = toperators.size();
            toperators.remove(tlen - 1);
            isOp = true;
        }

        if (!isTop)
            isTop = true;

        if (islBra)
            islBra = false;

        /* 计算结果 */
        double last = results.get(len - 1);
        double tmp = 0;

        try {
            tmp = Cal.deal(operator, results.get(len - 1));

            results.set(len - 1, tmp);
        }

        catch (Exception e) {
            isErr = true;
            emess = e.toString();
        }

        String nowexpression;

        /* 修改表达式数组为更合理的形式 */
        if (operator.equals("+/-")) {

            /* 数字不送表达式 */
            if (!isNumber && !isDouble) {
                operator = "negative";

                nowexpression = expression.get(elen - 1);

                nowexpression = operator + "(" + nowexpression + ")";

                /* 修改表达式数组和结果数组 */
                expression.set(elen - 1,nowexpression);
            }
        }

        else {
            /* 上一步的输入数字可以送入表达式数组，上一步如果是计算结果则不能 */
            if (isNumber || isDouble) {
                String tnum = String.valueOf(last);
                tnum = decorate(tnum);
                expression.add(tnum);
                elen = elen + 1;

                isNumber = false;
                multiple = 10;
                isDouble = false;
                scale = 10;
            }

            nowexpression = expression.get(elen - 1);

            nowexpression = operator + '(' + nowexpression + ')';

            /* 修改表达式数组和结果数组 */
            expression.set(elen - 1,nowexpression);
        }

        showNumber = String.valueOf(tmp);

        /* 修整输出 */
        showNumber = decorate(showNumber);

    }

    /* 计算器输入二元运算符 */
    public void obtainToperator(String toperator) {
        /* 按照数学表达式规则开放/禁止相应操作符出现 */

        /* 二元运算符后输入二元运算符视为更新 */
        if (!isTop) {
            expression.set(elen - 1,toperator);
            int tlen = toperators.size();
            toperators.set(tlen - 1,toperator);
            return;
        }

        if (!islBra)
            islBra = true;

        /* 小数，逻辑上不视为运算符 */
        if (toperator.equals(".")) {
            /* 不允许多个小数点的出现 */
            if (isDouble)
                return;
            isDouble = true;
            isNumber = false;
            showNumber = showNumber + ".";
            return;
        }

        /* 结束小数输入 */
        if (isDouble) {
            isDouble = false;
            isNumber = true;
            scale = 10;
        }

        /* 结束整数输入 */
        if (isNumber) {
            /* 把上一步的输入送入表达式数组 */
            String tnum = String.valueOf(results.get(len - 1));
            tnum = decorate(tnum);
            expression.add(tnum);
            elen = elen + 1;
            isNumber = false;
            multiple = 10;
        }

        expression.add(toperator);
        elen = elen + 1;

        int tlen = toperators.size();
        String toperator1 = toperators.get(tlen - 1);

        /* 优先级比较 */
        if (Cal.compare(toperator1,toperator) >= 0) {
            try {
                double tmp = Cal.deal(toperator1, results.get(len - 2), results.get(len - 1));

                /* 普通运算 */
                results.remove(len - 1);
                len = len - 1;
                results.set(len - 1, tmp);

                /* 结束本轮运算 */
                if (toperator.equals("="))
                    isEnd = true;

                /* 更新操作符数组 */
                toperators.set(tlen - 1,toperator);

                /* 二元操作符后不允许直接扩展一/二元操作符 */
                isOp = false;
                isTop = false;
            }

            catch (Exception e) {
                isErr = true;
                emess = e.toString();
            }

        }

        else
            toperators.add(toperator);

        /* 新一轮计算准备工作，结果可以直接参与下一轮计算 */
        if (isEnd) {
            if (len > 1) {
                tlen = toperators.size();
                toperator1 = toperators.get(tlen - 2);

                try {
                    double tmp = Cal.deal(toperator1, results.get(len - 2), results.get(len - 1));

                    /* 普通运算 */
                    results.remove(len - 1);
                    len = len - 1;
                    results.set(len - 1, tmp);
                }

                catch (Exception e) {
                    isErr = true;
                    emess = e.toString();
                }

            }

            isNumber = true;
        }

        showNumber = String.valueOf(results.get(len - 1));

        /* 修整输出 */
        showNumber = decorate(showNumber);

    }

    /* 计算器输入括号 */
    public void obtainBracket (String bracket) {
        if (bracket.equals("(")) {
            /* 左括号错误出现相当于重新开始 */
            if (!islBra)
                clearAll();

            expression.add(bracket);
            elen = elen + 1;

            toperators.add(bracket);

            /* 局部的全新运算 */
            toperators.add("+");
            results.add(0.0);
            len = len + 1;

            /* 左括号允许接右括号 */
            blen = blen + 1;
            isNumber = false;
            multiple = 10;
            isrBra = true;
            isOp = true;
            isTop = true;
        }

        else if (bracket.equals(")")) {
            /* 右括号错误出现不接受该输入 */
            if (!isrBra || blen <= 0)
                return;

            /* 如果二元运算符直接跟右括号，默认操作数为0 */
            if (!isTop) {
                results.add(0.0);
                len = len + 1;
                expression.add("0");
                elen = elen + 1;
            }

            else if (isNumber) {
                /* 把上一步的输入送入表达式数组 */
                String tnum = String.valueOf(results.get(len - 1));
                tnum = decorate(tnum);
                expression.add(tnum);
                elen = elen + 1;
                isNumber = false;
                multiple = 10;
            }

            expression.add(bracket);
            elen = elen + 1;

            int tlen = toperators.size();
            String toperator;

            double tmp;

            /* 由于二元运算符优先级缘故，括号表达式内存在最多三个未计算的部分 */
            while (tlen >= 0 && !toperators.get(tlen - 1).equals("(")) {

                toperator = toperators.get(tlen - 1);

                try {
                    tmp = Cal.deal(toperator, results.get(len - 2), results.get(len - 1));

                    /* 普通运算 */
                    results.remove(len - 1);
                    len = len - 1;
                    results.set(len - 1, tmp);

                    /* 结束局部运算 */
                    toperators.remove(tlen - 1);
                    tlen = tlen - 1;
                }

                catch (Exception e) {
                    isErr = true;
                    emess = e.toString();
                    break;
                }

            }

            /* 将括号表达式浓缩，在expression数组只保留一个位置 */
            toperators.remove(tlen - 1);
            tlen = tlen - 1;
            bracketCom();

            /* 右括号后不应该接左括号 */
            blen = blen - 1;
            islBra = false;
            isOp = true;
            isTop = true;

            showNumber = String.valueOf(results.get(len - 1));

            /* 修整输出 */
            showNumber = decorate(showNumber);
        }
    }

    /* 特殊处理 */
    public void obtainSpecialOrder (String order) {
         if (order.equals("CE"))
            clearError();
        else if (order.equals("CA"))
            clearAll();
        else if (order.equals("C"))
            clear();
    }

}
