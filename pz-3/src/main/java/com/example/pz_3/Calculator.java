package com.example.pz_3;
import java.util.*;

enum Operation {
    ADD, SUBTRACT,
    MULTIPLY,
    DIVIDE
}

public class Calculator {
    private float result;
    private ArrayList<Operation> operations;
    private ArrayList<Float> values;

    private static Calculator calculator;

    private Calculator() {
        this.result = 0;
        this.operations = new ArrayList<Operation>();
        this.values = new ArrayList<Float>();
    }

    public void addValue(float value) {
        this.values.add(value);
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public void removeLastOperation() {
        int t = this.operations.size();
        this.operations.remove(t - 1);
    }

    public float getResultValue(){
        return this.result;
    }

    public float getResult(){
        for (int i = 0; i < values.size(); i++) {
            if (i==0) {
                this.result = values.get(i);
            } else {
                Operation op = operations.get(i-1);

                switch (op) {
                    case ADD:
                        this.result = this.result + values.get(i);
                        break;
                    case SUBTRACT:
                        this.result = this.result - values.get(i);
                        break;
                    case MULTIPLY:
                        this.result = this.result * values.get(i);
                        break;
                    case DIVIDE:
                        this.result = this.result / values.get(i);
                        break;
                }
            }
        }
        operations.clear();
        values.clear();
        return this.result;
    }

    public void emptyResult(){
        this.result = 0;
    }

    public static Calculator getInstance() {
        if (calculator == null) {
            calculator = new Calculator();
        }
        return calculator;
    }
}
