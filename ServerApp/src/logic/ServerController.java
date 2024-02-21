/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.AppConfigurationEntry;
import server.Server;

/**
 *
 * @author Maja
 */
public class ServerController {
    private static ServerController instance;
    Server server;

    private ServerController() {

    }
    
    
    
    public static ServerController getInstance() {
        if(instance==null) 
            instance = new ServerController();
        return instance;
    }

    public void pokreniServer(int maxbroj) {
        server = new Server(maxbroj);
        server.pokreniServer();
    }

    public void zaustaviServer() throws IOException {
        server.zaustaviServer();
    }
    
    public void setConfigProperties(int max) {
        String maxString = String.valueOf(max);
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("app.config");
            prop.setProperty("max_broj_klijenata", maxString);
            prop.store(output, null);
            
        } catch (Exception e) {
        } finally {
            if(output!=null) {
                try {
                    output.close();
                } catch (Exception e) {
                }
            }
        }        
    }

    
    public int readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("app.config");
            prop.load(input);
            String maxString = prop.getProperty("max_broj_klijenata");
            int max = Integer.parseInt(maxString);
            return max;
        } catch (Exception e) {
            
        } finally {
            if(input!=null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return 0;
    }  
}
