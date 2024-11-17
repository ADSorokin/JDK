/**
 * 1.Написать класс Калькулятор (необобщенный), который содержит обобщенные статические методы: sum(), multiply(), divide(),
 * subtract(). Параметры этих методов – два числа разного типа, над которыми должна быть произведена операция.
 * Методы должны возвращать результат своей работы.
 */
public class Calc {

    public static <T extends Number, S extends Number> Double sum(T a, S b) {
        return a.doubleValue() + b.doubleValue();
    }

    public static <T extends Number, S extends Number> Double multiply(T a, S b) {
        return a.doubleValue() * b.doubleValue();
    }

    public static <T extends Number, S extends Number> Double divide(T a, S b) {
        if (b.doubleValue() == 0) {
            throw new ArithmeticException("Деление на ноль ");
        }
        return a.doubleValue() / b.doubleValue();
    }

    public static <T extends Number, S extends Number> Double subtract(T a, S b) {
        return a.doubleValue() - b.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println("Sum: " + Calc.sum(7, 5.1));
        System.out.println("Multiply: " + Calc.multiply(3.5, 2));
        System.out.println("Divide: " + Calc.divide(8, 2));
        System.out.println("Subtract: " + Calc.subtract(14, 6.1));
    }
}







