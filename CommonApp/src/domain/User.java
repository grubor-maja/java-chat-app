/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Maja
 */
public class User implements Serializable{
    String username;
    List<User> onlineUsers;
    List<Message> oldMessages;

    public User() {
        onlineUsers = new ArrayList<>();
    }

    public User(String username) {
        this.username = username;
        onlineUsers = new ArrayList<>();
    }

    public String getUserrname() {
        return username;
    }

    public void setUserrname(String userrname) {
        this.username = userrname;
    }

    @Override
    public String toString() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

    public List<User> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<User> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public List<Message> getOldMessages() {
        return oldMessages;
    }

    public void setOldMessages(List<Message> oldMessages) {
        this.oldMessages = oldMessages;
    }

    


    @Override
    public boolean equals(Object obj) {
        
        User u  =(User)obj;
        if(u.getUsername().equals(username))
            return true;
        return false;

    }
    
    
    
    
}
