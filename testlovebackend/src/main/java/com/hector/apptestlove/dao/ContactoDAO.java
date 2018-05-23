package com.hector.apptestlove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

public class ContactoDAO extends Conexion{

public int registrarContacto (UsuarioDTO usuario,ContactoDTO contacto){
		
		
		Connection con=Conexion.GetConnection();
		int respuesta=0;
		Statement stmt;
		
		try {
			
			UsuariosDAO uDAO=new UsuariosDAO();
			respuesta=uDAO.buscarUsuario(usuario);
			

			
			if(respuesta == Constantes.SUCCESS){
				String sql="insert into contacto(nom_user,contacto)values(?,?)";
	             PreparedStatement pstm = con.prepareStatement(sql);
	             pstm.setString(1, usuario.getNom_user());
	             pstm.setString(2, contacto.getContacto());
	             
	             respuesta=pstm.executeUpdate();
	             
	            
			}
             
             
		        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constantes.NOT_SUCCESS;
		}
       
	if(respuesta==Constantes.SUCCESS){
		return Constantes.SUCCESS;
	}else{
		return Constantes.NOT_SUCCESS;
	}
	
	}
	
public ContactoDTO getContactoXUsuario(UsuarioDTO usuario){
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	Statement stmt;
	ContactoDTO contacto=new ContactoDTO();
	
	try {
		
		 ResultSet rs=null;
       
		 String sql="SELECT contacto FROM contacto WHERE nom_user=?";
			

		 
		 PreparedStatement pstm = con.prepareStatement(sql);
        
        pstm.setString(1, usuario.getNom_user());
       

        System.out.println("sql="+pstm.toString());
         rs=pstm.executeQuery();
        
	        if( rs.next() ) {
	        	
	        	contacto.setContacto(rs.getString("contacto"));

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

public int actualizarContacto(ContactoDTO contacto,String nom_user){
	
	Connection con=Conexion.GetConnection();
	int respuesta=0;
	Statement stmt;
	
	String sql="UPDATE contacto SET contacto=?  WHERE nom_user=?";
    PreparedStatement pstm;
    
	try {
		pstm = con.prepareStatement(sql);
		pstm.setString(1, contacto.getContacto());
		pstm.setString(2, nom_user);
		
		respuesta=pstm.executeUpdate();
		
		System.out.println("actualizarContacto sql="+pstm.toString());
		
		if(respuesta>0){
			return Constantes.SUCCESS;
		}else{
			return Constantes.NOT_SUCCESS;
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
    
    return Constantes.NOT_SUCCESS;
	
}


public ContactoDTO getIdContactoXUsuario(UsuarioDTO usuario,ContactoDTO usuarioContacto){
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	Statement stmt;
	ContactoDTO contacto=new ContactoDTO();
	
	try {
		
		 ResultSet rs=null;
       
		 String sql="SELECT id_contacto  FROM contacto WHERE nom_user=? and contacto=?";
			

		 
		 PreparedStatement pstm = con.prepareStatement(sql);
        
        pstm.setString(1, usuario.getNom_user());
        pstm.setString(2, usuarioContacto.getContacto());
       

        System.out.println("sql="+pstm.toString());
         rs=pstm.executeQuery();
        
	        if( rs.next() ) {
	        	
	        	contacto.setId_contacto(rs.getInt("id_contacto"));

	           return contacto;
	        	
	        }else{
	            

	        	return contacto;
	        }
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
	}
  
   System.out.println("*******************************saliendo de getIdContactoXUsuario************************************");

	return contacto;
	
}

	
}
