/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tortrix;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author LUCI GOMEZ
 */
public class CConexion {
    
    Connection conectar = null;
    
    String usuario = "root";
    String contraseña = "Elcorazon1.";
    String bd = "bdTortixguate";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
 
    public Connection estableceConexion(){
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar  = DriverManager.getConnection(cadena,usuario,contraseña);
            //JOptionPane.showMessageDialog(null,"conección Exitosa");
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"error al conectarese a la base de datos"+ e.toString());
            
        }
        
        return conectar;
    }
}
