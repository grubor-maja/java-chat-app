/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domain.Message;
import java.util.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Maja
 */
public class MessagesTableModel extends AbstractTableModel {

    List<Message> messages;
    
    public MessagesTableModel(List<Message> messages) {
        this.messages = messages;
    }
    
    @Override
    public int getRowCount() {
        return messages.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Message m = messages.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return m.getSender();
                
            case 1:
                return m.getContent();
            case 2: 
                return m.getTime();
            default:
                return "";
        }
        
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                
                return "Sender";
            case 1:
                return "Content";
            case 2:
                return "Time sent";
            default:
                return "";
        }
    }
    
    
    
}
