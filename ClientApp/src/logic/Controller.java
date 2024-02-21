/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import communication.Operation;
import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import domain.Message;
import domain.User;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JComboBox;

/**
 *
 * @author Maja
 */
public class Controller {
    
    private static Controller instance;
    public Socket socket;
    Sender sender;
    Receiver receiver;
    
    

    private Controller() throws IOException {
        socket = new Socket("localhost", 9000);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }
    
    
    
    public static Controller getInstance() throws IOException {
        if(instance==null) 
            instance = new Controller();
        return instance;
    }    

    public User loginUser(User user) throws IOException, Exception {
        Request request = new Request(user, Operation.LOGIN);
        sender.send(request);
        Object responseObject =  receiver.receive();
        System.out.println("Rez: "+responseObject.getClass());
        Response response = (Response) responseObject;
        user = (User) response.getResult();
        return user;
        
    }

    public void logoutUser(User user) throws IOException {
        Request request = new Request(user, Operation.LOGOUT);
        sender.send(request);
    }

    public void sendMessage(Message m) throws IOException {
        Request request = new Request(m, Operation.SEND_MESSAGE);
        sender.send(request);
    }

    public void updateMe(User user) throws IOException {
        Request request = new Request(user, Operation.UPDATE);
        sender.send(request);        
    }
    
}