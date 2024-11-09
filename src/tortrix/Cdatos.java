/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tortrix;

import javax.swing.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author LUCI GOMEZ
 */
public class Cdatos {
int codigo;
    String nombreProducto;
    double precioProducto;
    int cantidadProducto;
    LocalDate fechaProducto;
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public LocalDate getFechaProducto() {
        return fechaProducto;
    }

    public void setFechaProducto(LocalDate fechaProducto) {
        this.fechaProducto = fechaProducto;
    }
    
    public void InsertarDatos(JTextField paramNombreProducto, JTextField paramPrecioProducto, JTextField paramCantidadProducto, JTextField paramFechaProducto){
        
        setNombreProducto(paramNombreProducto.getText());
         setPrecioProducto((Double.parseDouble(paramPrecioProducto.getText())));
         setCantidadProducto(Integer.parseInt(paramCantidadProducto.getText()));
         setFechaProducto(LocalDate.parse(paramFechaProducto.getText()));
         
         CConexion objetoConexion = new CConexion();
         
         String consulta = "insert into datos (nombresProducto,precioUnitario,cantidadProducto,fechaVencimmiento) values (?,?,?,?);";
       
         try { 
             CallableStatement  cs = objetoConexion.estableceConexion().prepareCall(consulta);
             
             
             cs.setString(1, getNombreProducto());
             cs.setDouble(2, getPrecioProducto());        
             cs.setInt(3, getCantidadProducto());
             cs.setDate(4, java.sql.Date.valueOf(getFechaProducto()));
             
             cs.execute();
             
             JOptionPane.showMessageDialog(null,"Se inseto correctamente los datos");
         }catch (Exception e){
             
             JOptionPane.showMessageDialog(null,"No se inseto correctamente los datos: "+e.toString());
         }
         
    }
    
   public void MostrarDatos(JTable paramTablaTotaldatos){
       
       CConexion objetoConexion = new CConexion();
       
       DefaultTableModel modelo = new  DefaultTableModel();
       
       TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);//hordenar correctamente 
       paramTablaTotaldatos.setRowSorter(OrdenarTabla);
       
       String sql = "";
       
       modelo.addColumn("id:");
       modelo.addColumn("Nombre: ");
       modelo.addColumn("Precio Unitario:");
       modelo.addColumn("Cantidas:");
       modelo.addColumn("Fecha Vencimiento:");
       
       paramTablaTotaldatos.setModel(modelo);
       
       
       sql ="select * from datos;";
       
       String[] datos = new String [5];
       Statement st;
       
       try {
           st=objetoConexion.estableceConexion().createStatement();
           
           ResultSet rs = st.executeQuery(sql);
           
           while (rs.next()){
               datos[0]= rs.getString(1);
               datos[1]= rs.getString(2);
               datos[2]= rs.getString(3);
               datos[3]= rs.getString(4);
               datos[4]= rs.getString(5);
               
               modelo.addRow(datos);
           }
           paramTablaTotaldatos.setModel(modelo);
       }catch (Exception e){
           JOptionPane.showMessageDialog(null,"nose puede mostrar los registros,error" +e.toString());
       }
          
   }
   public void SeleccionarDatos(JTable paramTablaDatos, JTextField paramId, JTextField paramNombres,JTextField paramPrecio,JTextField paramCantidad,JTextField paramFecha){

try {
    int fila = paramTablaDatos.getSelectedRow();
    
    if (fila >=0 ){
        
        paramId.setText((paramTablaDatos.getValueAt(fila, 0).toString()));
         paramNombres.setText((paramTablaDatos.getValueAt(fila, 1).toString()));
          paramPrecio.setText((paramTablaDatos.getValueAt(fila, 2).toString()));
           paramCantidad.setText((paramTablaDatos.getValueAt(fila, 3).toString()));
            paramFecha.setText((paramTablaDatos.getValueAt(fila, 4).toString()));
        
    }
    {
        JOptionPane.showMessageDialog(null, "la fila no esta seleccionada");
    }
}   catch (Exception e)  {
    
       JOptionPane.showMessageDialog(null, "Error de selección error: "+e.toString());
}  
       
   } 
  
    public void ModificarDatos( JTextField paramCodigo, JTextField paramNombreproducto, JTextField paramPrecioUnitario){
        
       setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombreProducto(paramNombreproducto.getText());
        setPrecioProducto(Double.parseDouble(paramPrecioUnitario.getText()));
         
         
         CConexion objetoConexion = new CConexion();
         
         String consulta = "UPDATE datos SET datos.nombresProducto = ?, datos.precioUnitario = ? WHERE datos.id=?;";
         
         try {
                
             CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
         
            cs.setString(1, getNombreProducto());
             cs.setDouble(2, getPrecioProducto());        
             cs.setInt(3,getCodigo());
             
            
             
             
             cs.execute();
             JOptionPane.showMessageDialog(null,"Modificación con exito");
           
            
             } catch (SQLException e){
                 JOptionPane.showMessageDialog(null,"Error no se modifico: "+e.toString());  
                 
         }
    }
    public void EliminarDatos(JTextField paramCodigo){
        
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "delete FROM datos WHERE datos.id=?;";
        
        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, getCodigo());
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"Sea eliminado correctamente el dato");  
                 
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error no se elimino el dato: "+e.toString());
        }
                 
    }
    
    public void ConsultarDatos(JTable paramTablaTotaldatos){
        
       CConexion objetoConexion = new CConexion();
       
     DefaultTableModel modelo = new  DefaultTableModel();
       
       TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);//hordenar correctamente 
       paramTablaTotaldatos.setRowSorter(OrdenarTabla);
       String sql = "";
       
       
       modelo.addColumn("id:");
       modelo.addColumn("Nombre: ");
     
       
       paramTablaTotaldatos.setModel(modelo);
       
       
       sql ="select id, nombresProducto from datos;";
       
       String[] datos = new String [2];
       Statement st;
       
       try {
           st=objetoConexion.estableceConexion().createStatement();
           
           ResultSet rs = st.executeQuery(sql);
           
           while (rs.next()){
               datos[0]= rs.getString(1);
               datos[1]= rs.getString(2);
               
               modelo.addRow(datos);
           }
           paramTablaTotaldatos.setModel(modelo);
       }catch (Exception e){
           JOptionPane.showMessageDialog(null,"nose puede mostrar los registros,error" +e.toString());
       }
          
   }
        public void RegresarDatos(JTable paramTablaTotaldatos){
       
       CConexion objetoConexion = new CConexion();
       
       DefaultTableModel modelo = new  DefaultTableModel();
       
       TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);//hordenar correctamente 
       paramTablaTotaldatos.setRowSorter(OrdenarTabla);
       
       String sql = "";
       
       modelo.addColumn("id:");
       modelo.addColumn("Nombre: ");
       modelo.addColumn("Precio Unitario:");
       modelo.addColumn("Cantidas:");
       modelo.addColumn("Fecha Vencimiento:");
       
       paramTablaTotaldatos.setModel(modelo);
       
       
       sql ="select * from datos;";
       
       String[] datos = new String [5];
       Statement st;
       
       try {
           st=objetoConexion.estableceConexion().createStatement();
           
           ResultSet rs = st.executeQuery(sql);
           
           while (rs.next()){
               datos[0]= rs.getString(1);
               datos[1]= rs.getString(2);
               datos[2]= rs.getString(3);
               datos[3]= rs.getString(4);
               datos[4]= rs.getString(5);
               
               modelo.addRow(datos);
           }
           paramTablaTotaldatos.setModel(modelo);
       }catch (Exception e){
           JOptionPane.showMessageDialog(null,"nose puede mostrar los registros,error" +e.toString());
       }
          
   }
    public void BuscarporID(JTextField idBusqueda,JTextField idRes,JTextField NombreRes,JTextField PrecioRes,JTextField CantidadRes,JTextField FechaRes ){
           String consulta = "select id,nombresProducto,precioUnitario,cantidadProducto,fechaVencimmiento from datos where datos.id=(?);";
           
          CConexion objetoConexion = new CConexion();
       
       try {
           CallableStatement cs =objetoConexion.estableceConexion().prepareCall(consulta);
           cs.setString(1,idBusqueda.getText());
           cs.execute();
  
           ResultSet rs = cs.executeQuery();
           
           if(rs.next()){
               JOptionPane.showMessageDialog(null,"Registro encontrado correctamente");
               idRes.setText(rs.getString("id"));
              NombreRes.setText(rs.getString("nombresProducto"));
               PrecioRes.setText(rs.getString("precioUnitario"));
               CantidadRes.setText(rs.getString("cantidadProducto"));
               FechaRes .setText(rs.getString("fechaVencimiento"));
           }
           else
           {
              JOptionPane.showMessageDialog(null,"Registros no encontrado"); 
              idRes.setText("");
              NombreRes.setText("");
               PrecioRes.setText("");
               CantidadRes.setText("");
               FechaRes .setText("");
           }
       }catch (SQLException ex){
           JOptionPane.showMessageDialog(null,"nose puede mostrar los registros,error" +ex.toString());
       }         
   }
  }
    

