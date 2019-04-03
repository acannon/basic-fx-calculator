package core;

public class Model {
    public float calculate(float a, float b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if(b == 0)
                    return 0;
                else
                    return a / b;
            default:
                return 0;
        }
    }
}
