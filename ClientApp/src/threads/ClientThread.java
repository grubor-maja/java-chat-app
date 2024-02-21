/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import static communication.Operation.GET_ONLINE_USERS;
import static communication.Operation.LOGIN;
import static communication.Operation.LOGOUT;
import static communication.Operation.SEND_MESSAGE;
import static communication.Operation.UPDATE;
import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import domain.Message;
import domain.User;
import form.FrmMain;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Maja
 */
public class ClientThread extends Thread {
    
    public Socket socket;
    Sender sender;
    Receiver receiver;
    JComboBox<User> cbOnlineUsers;
    JTextField txtPoslednjaPoruka;
    FrmMain form;
    List<Message> allMessages;
    List<Message> oldMessages;

    public ClientThread(Socket socket,JComboBox<User> cbOnlineUsers, JTextField txtPoslednjaPoruka, FrmMain form) {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        this.cbOnlineUsers = cbOnlineUsers;
        this.txtPoslednjaPoruka = txtPoslednjaPoruka;
        this.form = form;
        allMessages = new ArrayList<>();
        oldMessages = new ArrayList<>();
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            try {
                Request request = (Request) receiver.receive();
                Response response = new Response();
                
                switch (request.getOperation()) {

                    case LOGOUT:
                        continue;
                    case SEND_MESSAGE:
                        Message m = (Message) request.getArgument();
                        
                        if(m.getReceiver().equals(form.user)) {
                            txtPoslednjaPoruka.setText(m.getContent());
                            
                            allMessages.add(m); 
                            
                        }
                        for(Message m2 : allMessages) {
                            oldMessages.add(m2);
                        }
                        
                        continue;
                    case UPDATE:
                        List<User> onlineUsers = (List<User>) request.getArgument();
                        onlineUsers.remove(form.user);
                        System.out.println("Updated online users");
                        for(User u2 : onlineUsers) {
                            System.out.println("Korisnik: "+u2);
                        }
                        cbOnlineUsers.setModel(new DefaultComboBoxModel(onlineUsers.toArray()));                           
                        continue;
                    case GET_ONLINE_USERS:                         
                        continue;
                    case UPDATE_MESSAGES:
                        allMessages = (List<Message>) request.getArgument();
                        form.updateTable(allMessages);
                        continue;
                     
                    default:
                        break;
                }
                sender.send(response);
            } catch (Exception ex) {
                System.out.println("");
            }
            
        }
    }    
}
