package co.edu.uptc.presenter;

import co.edu.uptc.model.Calculator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Presenter {
    private final int PUERTO = 1234;
    private ServerSocket serverSocket;
    private Calculator calculator;

    public Presenter() throws IOException {
        serverSocket = new ServerSocket(PUERTO);
        this.calculator = new Calculator();

    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            Cliente_Thread clienteThread = new Cliente_Thread(socket, calculator);
            clienteThread.start();
        }

    }
}
