package co.edu.uptc.presenter;

import co.edu.uptc.model.Calculator;
import co.edu.uptc.net.Connetion;

import java.io.IOException;
import java.net.Socket;

public class Cliente_Thread extends Thread {
    private Connetion connetion;
    private Calculator calculator;

    public Cliente_Thread(Socket socket, Calculator calculator) throws IOException {
        this.connetion = new Connetion(socket);
        this.calculator = calculator;

    }

    @Override
    public void run() {
        super.run();
        try {
            menuPrincipal();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void menuPrincipal() throws IOException {
        String message = new String();
        double result = 0.0;
        establishConnection();
        do {
            message = connetion.receive();
            Double[] parts = dataToDouble(message);
            switch (Integer.toString(parts[0].intValue())) {
                case "1":
                    result = calculator.sum(parts[1], parts[2]);
                    connetion.send("Result: " + result);
                    break;
                case "2":
                    result = calculator.rest(parts[1], parts[2]);
                    connetion.send("Result: " + result);
                    break;
                case "3":
                    result = calculator.multiply(parts[1], parts[2]);
                    connetion.send("Result: " + result);
                    break;
                case "4":
                    result = calculator.divide(parts[1], parts[2]);
                    connetion.send("Result: " + result);
                    break;
                case "5":
                    connetion.send("Memory: " + calculator.getMemory());
                    break;
                case "6":
                    closeConnection();
                    break;
                default:
                    optionNotValid();
                    break;
            }

        } while (message != "6");
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
        throw new IOException("Connection closed");
    }

    private void optionNotValid() throws IOException {
        connetion.send("Invalid option");
    }

}
