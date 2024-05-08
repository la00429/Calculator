package co.edu.uptc.net;

public class Request {
    private String operation;
    private String number1;
    private String number2;

    public Request(String operation, String number1, String number2) {
        this.operation = operation;
        this.number1 = number1;
        this.number2 = number2;
    }

    public String getOperation() {
        return operation;
    }

    public String getNumber1() {
        return number1;
    }

    public String getNumber2() {
        return number2;
    }
}
