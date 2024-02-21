/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import domain.Message;
import domain.User;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import threads.ClientThread;

/**
 *
 * @author Maja
 */
public class Server extends Thread {
    
    ServerSocket serverSocket;
    Socket clientSocket;
    List<ClientThread> clientThreads;
    List<User> onlineUsers;
    // Copy added because of concurrent modification error
    List<User> onlineUsersCopy;
    int maxBrojKlijenata;
    int trenutniBrojKlijenata=0;

    public Server(int maxbroj) {
        maxBrojKlijenata = maxbroj;
        clientThreads = new ArrayList<>();
        onlineUsers = new ArrayList<>();
        for(User u2 : onlineUsers) {
            onlineUsersCopy.add(u2);
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9000);
            System.out.println("Waiting for connection...");
            while(!isInterrupted()) {
                if(trenutniBrojKlijenata<=maxBrojKlijenata) {
                    clientSocket = serverSocket.accept();
                    System.out.println("Connected");
                    ClientThread clientThread = new ClientThread(this, clientSocket);
                    trenutniBrojKlijenata++;
                    System.out.println("Trenutni broj: "+trenutniBrojKlijenata);
                    clientThread.start();
                    clientThreads.add(clientThread);                    
                } else {
                    System.out.println("Dostignut je maksimalan broj klijenata za ovaj server ("+maxBrojKlijenata+")");
                    clientSocket.close();
                    
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
    

    public void pokreniServer() {
        start();
    }

    public void zaustaviServer() throws IOException {
        interrupt();
        for(ClientThread ct : clientThreads) {
            if(ct.isAlive() && ct!=null) {
                ct.interrupt();
                ct.socket.close();
            }
        }
        serverSocket.close();
    }

    public User loginUser(User user) throws IOException {
//        for(ClientThread ct : clientThreads) {
//            ct.update(onlineUsers);
//        }           
        if(!onlineUsers.contains(user))
            onlineUsers.add(user);
     
        return user;
    }

    public void logoutUser(User user) throws IOException {
        onlineUsers.remove(user);

        for(ClientThread ct : clientThreads) {
            if(!ct.u.equals(user))
                ct.update(onlineUsers);
        }
    }

    public void sendMessage(Message m) throws IOException {

        for(ClientThread ct : clientThreads) {
            if(ct.u.equals(m.getReceiver())) {
                ct.sendMessage(m);
                ct.updateMessages(m);                
            }

        }
    }

    public void updateMe(User user) throws IOException {
            for(ClientThread ct : clientThreads) {
                if(ct.u!=user) {
                    ct.update(onlineUsers);    
                } else {
                    ct.update(sviSemOvog(user));
                }
                
            }  
    }

    private List<User> sviSemOvog(User user) {
        List<User> sviSemMene = new ArrayList<>();
        for(User u : onlineUsers) {
            if(u!=user)
                sviSemMene.add(u);
        }
        return sviSemMene;
    }


    
}
