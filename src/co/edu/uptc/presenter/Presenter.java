package co.edu.uptc.presenter;

import co.edu.uptc.model.Calculator;
import co.edu.uptc.net.Connetion;

import java.io.IOException;

public class Presenter {
    private Connetion connetion;
    private Calculator calculator;

    public Presenter() throws IOException {
        this.connetion = new Connetion();
        this.calculator = new Calculator();
    }

    public void start() throws IOException {
        String message = new String();
        Double result = 0.0;
        try {
            establishConnection();
            do {
                message = connetion.receive();
                Double[] parts = dataToDouble(message);
                switch (Integer.toString(parts[0].intValue())) {
                    case "1":
                        result = calculator.sum(parts[1], parts[2]);
                        break;
                    case "2":
                        result = calculator.rest(parts[1], parts[2]);
                        break;
                    case "3":
                        result = calculator.multiply(parts[1], parts[2]);
                        break;
                    case "4":
                        result = calculator.divide(parts[1], parts[2]);
                        break;
                    case "5":
                        closeConnection();
                        break;
                    default:
                        optionNotValid();
                        break;
                }
                connetion.send("Result: " + result);
            } while (!message.equals("5"));
        } catch (IOException e) {
//            System.err.println(e.getMessage());
        }
    }

    private void establishConnection() throws IOException {
        connetion.connect();
        connetion.send("Conexión establecida con el servidor");
        System.err.println(connetion.receive());
    }

    private Double[] dataToDouble(String data) {
        String[] parts = data.split(" ");
        Double[] numbers = new Double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Double.parseDouble(parts[i]);
        }
        return numbers;
    }
    private void closeConnection() throws IOException {
        connetion.send("Close connection");
        connetion.disconnect();
    }

    private void optionNotValid() throws IOException {
        connetion.send("Invalid option");
    }
}
