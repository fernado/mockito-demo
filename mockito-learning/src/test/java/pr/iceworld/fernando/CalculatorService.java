package pr.iceworld.fernando;

public class CalculatorService {
    private Calculator calculator;

    public CalculatorService(Calculator calculator) {
        this.calculator = calculator;
    }

    public int performAddition(int a, int b) {
        // Some additional logic
        return calculator.add(a, b);
    }
}