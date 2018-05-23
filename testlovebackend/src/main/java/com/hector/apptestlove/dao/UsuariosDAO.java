package com.hector.apptestlove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;


public class UsuariosDAO extends Conexion {

	
	public int registrarUser (UsuarioDTO usuario){
		
		String res="";
		Connection con=Conexion.GetConnection();
		int respuesta=0;
		Statement stmt;
		
		try {
			
			
			respuesta=this.buscarUsuario(usuario);
			

			
			if(respuesta == Constantes.NOT_SUCCESS){
				String sql="insert into usuarios(nom_user,password)values(?,?)";
	             PreparedStatement pstm = con.prepareStatement(sql);
	             pstm.setString(1, usuario.getNom_user());
	             pstm.setString(2, usuario.getPassword());
	             
	             return pstm.executeUpdate();
	             
	             
	             //
			}else{
				return Constantes.NOT_SUCCESS; 
			}
             
             
		        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		
		return Constantes.SUCCESS;
	}
	
	


public int existeGcmXUser(String codGcm,String nom_user){
	
	System.out.println("***************gcm="+codGcm+" ,usuario"+nom_user+"****************************");
	
	Connection con=Conexion.GetConnection();
	int respuesta=0;
	Statement stmt;
	String valor1=nom_user;
	try {
		
		 ResultSet rs=null;
        
		 String sql="SELECT g.codGcm,g.nom_user FROM usuarios u,gcm g WHERE g.nom_user=? AND u.nom_user=g.nom_user";
			

		 
		 PreparedStatement pstm = con.prepareStatement(sql);
         
         pstm.setString(1, valor1);
         System.out.println("**************************usuario="+valor1+"*********************************");

         System.out.println("sql="+pstm.toString());
          rs=pstm.executeQuery();
         
	        if( rs.next() ) {
	        	respuesta=1;
	            System.out.println("*******************************saliendo de EXISTEGCMXUSER************************************");

	           return respuesta;
	        	
	        }else{
	            System.out.println("*******************************saliendo de EXISTEGCMXUSER************************************");

	        	return respuesta;
	        }
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
	}
   
    System.out.println("*******************************saliendo de EXISTEGCMXUSER************************************");

	return respuesta;
}

public int loguearUsuario(UsuarioDTO usuario){
	
	
	Connection con=Conexion.GetConnection();
	int respuesta=0;
	Statement stmt;
	
	try {
		
		 ResultSet rs=null;
        
		 String sql="SELECT u.nom_user,u.password FROM usuarios u WHERE u.nom_user=? AND u.password=?";
			

		 
		 PreparedStatement pstm = con.prepareStatement(sql);
         
         pstm.setString(1, usuario.getNom_user());
         pstm.setString(2, usuario.getPassword());


         System.out.println("sql="+pstm.toString());
          rs=pstm.executeQuery();
         
	        if( rs.next() ) {
	        	respuesta=Constantes.SUCCESS;

	           return respuesta;
	        	
	        }else{
	            respuesta=Constantes.NOT_SUCCESS;

	        	return respuesta;
	        }
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
	}
   
    System.out.println("*******************************saliendo de EXISTEGCMXUSER************************************");

	return respuesta;
}

public int buscarUsuario(UsuarioDTO usuario){
	
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	Statement stmt;
	
	try {
		
		 ResultSet rs=null;
        
		 String sql="SELECT u.nom_user FROM usuarios u WHERE u.nom_user=?";
			

		 
		 PreparedStatement pstm = con.prepareStatement(sql);
         
         pstm.setString(1, usuario.getNom_user());
        


         System.out.println("sql="+pstm.toString());
          rs=pstm.executeQuery();
         
	        if( rs.next() ) {
	        	respuesta = Constantes.SUCCESS;

	           
	        	
	        }else{
	            respuesta=Constantes.NOT_SUCCESS;

	        	
	        }
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
	}
   
    System.out.println("*******************************saliendo de EXISTEGCMXUSER************************************");

	return respuesta;
}


/*Obtener el nombre del usuario a partir de un contacto,se utiliza en el flujo de responder pregunta*/
public ContactoDTO getContactoXUsuario(UsuarioDTO usuario){
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	Statement stmt;
	ContactoDTO contacto=new ContactoDTO();
	
	try {
		
		 ResultSet rs=null;
       
		 String sql="SELECT nom_user FROM contacto WHERE contacto=?";
			
		 PreparedStatement pstm = con.prepareStatement(sql);
        
        pstm.setString(1, usuario.getNom_user());
       

        System.out.println("sql="+pstm.toString());
         rs=pstm.executeQuery();
        
	        if( rs.next() ) {
	        	
	        	contacto.setContacto(rs.getString("nom_user"));

	           return contacto;
	        	
	        }else{
	            

	        	return contacto;
	        }
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
	}
  
   System.out.println("*******************************saliendo de getContactoXUsuario************************************");

	return contacto;
	
}
}
