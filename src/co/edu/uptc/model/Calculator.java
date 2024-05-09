package co.edu.uptc.model;

public class Calculator {
    private double memory;

    public Calculator( ) {
        this.memory = 0;
    }

    public double sum(double a, double b){
       updateMemory(a+b);
        return a+b;
    }

    public double rest(double a, double b){

        return a-b;
    }

    public double multiply(double a, double b){

        return a*b;
    }

    public double divide(double a, double b){

        return a/b;
    }

    public  synchronized double getMemory() {
        return memory;
    }

    public  synchronized void updateMemory(double memory) {
        this.memory += memory;
    }


}
