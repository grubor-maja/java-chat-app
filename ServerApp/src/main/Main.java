/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


import form.FrmServer2;
import javax.swing.JFrame;
import logic.ServerController;

/**
 *
 * @author Maja
 */
public class Main {
    public static void main(String[] args) {
        ServerController.getInstance().setConfigProperties(5);
        int max = ServerController.getInstance().readConfigProperties();
        JFrame frmServer2 = new FrmServer2(max);
        frmServer2.setVisible(true);
    }
}
