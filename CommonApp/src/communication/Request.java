/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import java.io.Serializable;

/**
 *
 * @author Maja
 */
public class Request implements Serializable{
    Object argument;
    Operation operation;

    public Request() {
    }

    public Request(Object argument, Operation operation) {
        this.argument = argument;
        this.operation = operation;
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    
    @Override
    public String toString() {
        return "Request{" + "argument=" + argument + ", operation=" + operation + '}';
    }
    
    
}
