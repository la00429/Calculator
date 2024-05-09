package co.edu.uptc.presenter;

import co.edu.uptc.model.Calculator;
import co.edu.uptc.net.Connetion;
import co.edu.uptc.net.Request;
import co.edu.uptc.net.Responsive;
import com.google.gson.Gson;

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
        Gson gson = new Gson();
        double result = 0.0;
        establishConnection();
        Request request;
        do {
            request = gson.fromJson(connetion.receive(), Request.class);
            switch (request.getOperation()) {
                case "1":
                    result = calculator.sum(Double.parseDouble(request.getNumber1()), Double.parseDouble(request.getNumber2()));
                    connetion.send(new Gson().toJson(new Responsive(String.valueOf(result), "Operation performed successfully")));
                    break;
                case "2":
                    result = calculator.rest(Double.parseDouble(request.getNumber1()), Double.parseDouble(request.getNumber2()));
                    connetion.send(new Gson().toJson(new Responsive(String.valueOf(result), "Operation performed successfully")));
                    break;
                case "3":
                    result = calculator.multiply(Double.parseDouble(request.getNumber1()), Double.parseDouble(request.getNumber2()));
                    connetion.send(new Gson().toJson(new Responsive(String.valueOf(result), "Operation performed successfully")));
                    break;
                case "4":
                    result = calculator.divide(Double.parseDouble(request.getNumber1()), Double.parseDouble(request.getNumber2()));
                    connetion.send(new Gson().toJson(new Responsive(String.valueOf(result), "Operation performed successfully")));
                    break;
                case "5":
                    result = calculator.getMemory();
                    connetion.send(new Gson().toJson(new Responsive(String.valueOf(result), "Operation performed successfully")));
                    break;
                case "6":
                    closeConnection();
                    break;
                default:
                    optionNotValid();
                    break;
            }
        } while (!request.getOperation().equals("6"));
    }

    private void establishConnection() throws IOException {
        connetion.connect();
        Gson gson = new Gson();
        connetion.send(gson.toJson(new Responsive("0", "Connection established")));
        Responsive message = gson.fromJson(connetion.receive(), Responsive.class);
        System.err.println(message.getMessage());
    }

    private void closeConnection() throws IOException {
        Gson gson = new Gson();
        connetion.send(gson.toJson(new Responsive("6", "Close connection")));
        connetion.disconnect();
        throw new IOException("Connection closed");
    }

    private void optionNotValid() throws IOException {
        Gson gson = new Gson();
        connetion.send(gson.toJson(new Responsive("0", "Invalid option")));
    }

}
