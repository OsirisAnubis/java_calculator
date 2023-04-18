import java.io.IOException;
import java.util.Scanner;
public class Calculator {
    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        String calcExpression = scanner.nextLine();
        String[] parts = calcExpression.split(" ");
        if (parts.length != 3) {
            throw new IOException("Wrong number of arguments");
        }
        String firstNumStr = parts[0];
        String secondNumStr = parts[2];
        boolean isRomanNumbers = RomanNumber.isRoman(firstNumStr) && RomanNumber.isRoman(secondNumStr);
        int firstNum;
        int secondNum;
        if (isRomanNumbers) {
            firstNum = RomanNumber.convertRomanToInt(firstNumStr);
            secondNum = RomanNumber.convertRomanToInt(secondNumStr);
        } else {
            try {
                firstNum = Integer.parseInt(firstNumStr);
                secondNum = Integer.parseInt(secondNumStr);
            } catch (NumberFormatException err) {
                throw new IOException("Incorrect number in expression");
            }
        }
        if ((firstNum < 1) | (firstNum > 10) | (secondNum < 1) | (secondNum > 10))
            throw new IOException("Number is out of range");
        String operator = parts[1];
        int resultNum = Operator.use(operator, firstNum, secondNum);
        if (isRomanNumbers && (resultNum < 1))
            throw new IOException("Roman numerals cannot be negative");
        System.out.println(isRomanNumbers ? RomanNumber.convertIntToRoman(resultNum) : resultNum);
    }
}
class RomanNumber {
    public static int value(char r) {
        if (r == 'I') return 1;
        if (r == 'V') return 5;
        if (r == 'X') return 10;
        if (r == 'L') return 50;
        if (r == 'C') return 100;
        if (r == 'D') return 500;
        if (r == 'M') return 1000;
        return -1;
    }
    public static int[] values = { 1000,900,500,400,100,90,50,40,10,9,5,4,1 };
    public static String[] romanLetters = { "M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I" };
    public static int convertRomanToInt(String s) {
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int s1 = value(s.charAt(i));
            if (i + 1 < s.length()) {
                int s2 = value(s.charAt(i + 1));
                if (s1 >= s2) {
                    total = total + s1;
                } else {
                    total = total - s1;
                }
            } else {
                total = total + s1;
            }
        }
        return total;
    }
    public static String convertIntToRoman(int num) {
        StringBuilder roman = new StringBuilder();
        for(int i = 0; i < values.length; i++) {
            while(num >= values[i]) {
                num = num - values[i];
                roman.append(romanLetters[i]);
            }
        }
        return roman.toString();
    }

    public static boolean isRoman(String s) {
        return !s.isEmpty()
                && s.matches("M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})");
    }
}
class Operator {
    public static int use(String operator, int num1, int num2) throws IOException{
        if (operator.equals("+")) return num1 + num2;
        if (operator.equals("-")) return num1 - num2;
        if (operator.equals("*")) return num1 * num2;
        if (operator.equals("/")) return num1 / num2;
        throw new IOException("Bad operator");
    }
}