/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import communication.Operation;
import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import domain.Message;
import domain.User;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author Maja
 */
public class ClientThread extends Thread {
    
    Server server;
    public Socket socket;
    Sender sender;
    Receiver receiver;
    public User u;
    List<Message> oldMessages;

    public ClientThread(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        oldMessages = new ArrayList<>();
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                Request request = (Request) receiver.receive();
                Response response = new Response();
                
                switch (request.getOperation()) {
                    case LOGIN:
                        User user = (User) request.getArgument();
                        user = server.loginUser(user);
                        response.setResult(user);                         
                        this.u =user;
                        break;
                    case LOGOUT:
                        user = (User) request.getArgument();
                        server.logoutUser(user);
                        
                        continue;
                    case SEND_MESSAGE:
                        Message m = (Message) request.getArgument();
                        server.sendMessage(m);
                        continue;
                    case UPDATE:
                        user = (User) request.getArgument();
                        server.updateMe(user);
             
                        continue;
                    case GET_ONLINE_USERS:
                        
                        continue;
                     
                    default:
                        break;
                }
                
                sender.send(response);
            } catch (Exception ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public void update(List<User> onlineUsers) throws IOException {
        Request request = new Request(onlineUsers, Operation.UPDATE);
        sender.send(request);
    }

    public void sendMessage(Message m) throws IOException {
        Request request = new Request(m, Operation.SEND_MESSAGE);
        sender.send(request);
    }

    public void updateMessages(Message m) throws IOException {
        Request request = new Request(oldMessages, Operation.UPDATE_MESSAGES);
        sender.send(request);    
        oldMessages.add(m);
    }
    
    
    
    
    
}
