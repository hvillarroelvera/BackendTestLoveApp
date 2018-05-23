package com.hector.apptestlove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

public class SolicitudContactoPendienteDAO {

	
	public int insertarSolicitudContacto (UsuarioDTO usuario,UsuarioDTO usuarioContacto,short tipo){
		
		
		Connection con=Conexion.GetConnection();
		int respuesta=0;
		Statement stmt;
		
		try {
			
			UsuariosDAO uDAO=new UsuariosDAO();
			respuesta=uDAO.buscarUsuario(usuario);
			

			
			if(respuesta == Constantes.SUCCESS){
				
				String sql="insert into solicitud(userEmisor,userReceptor,estado,tipo)values(?,?,?,?)";
	             PreparedStatement pstm = con.prepareStatement(sql);
	             pstm.setString(1, usuario.getNom_user());
	             pstm.setString(2, usuarioContacto.getNom_user());
	             pstm.setShort(3, Constantes.ESTADO_SOLICITUD_ENVIADA);
	             pstm.setShort(4, tipo);
	             
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
	
	
	public int eliminarSolicitudContactoPendiente(UsuarioDTO usuario,UsuarioDTO usuarioContacto){
		
		Connection con=Conexion.GetConnection();
		int respuesta=0;
		Statement stmt;
		
		String sql="DELETE FROM solicitud  WHERE userEmisor=? AND userReceptor=?";
	    PreparedStatement pstm;
	    
		try {
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, usuario.getNom_user());
            pstm.setString(2, usuarioContacto.getNom_user());
			
			
			respuesta=pstm.executeUpdate();
			
			System.out.println("eliminarSolicitudContactoPendiente sql="+pstm.toString());
			
			if(respuesta>0){
				return Constantes.SUCCESS;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constantes.NOT_SUCCESS;
		}
	   
	    
	    return Constantes.NOT_SUCCESS;
		
		
	}
	
	
	public boolean existeSolicitudContacto(UsuarioDTO usuario,ContactoDTO contacto){
		
		Connection con=Conexion.GetConnection();
		int respuesta = 0;
		Statement stmt;
		
		
		try {
			
			 ResultSet rs=null;
	       
			 String sql="SELECT count(numero) as numero FROM solicitud WHERE userEmisor=? AND userReceptor=? AND estado=? AND tipo=?";
				

			 
			 PreparedStatement pstm = con.prepareStatement(sql);
	        
			 pstm.setString(1, usuario.getNom_user());
	        pstm.setString(2, contacto.getContacto());
	        pstm.setShort(3, Constantes.ESTADO_SOLICITUD_ENVIADA);
            pstm.setShort(4, Constantes.TIPO_SOLICITUD_SOL_CONTACTO);
	       


	        System.out.println("sql="+pstm.toString());
	         rs=pstm.executeQuery();
	        
		        if( rs.next() ) {
		        	
		        	if(rs.getInt("numero") > 0){
		        		System.out.println("Tiene solicitudContacto");
		        		return true;
		        	}else{
		        		System.out.println("No tiene solicitudContacto");
		        		return false;
		        	}
		        	
		        }else{
		            
		     	   System.out.println("*******************************saliendo de getContactoXUsuario************************************");
		     	  System.out.println("No tiene solicitudContacto");
		        	return false;
		        }
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
			System.out.println("Error");
			return false;
		}
	  

		
		
	}
	
	public int actualizarSolicitud(short numero,short estado){
		
		Connection con=Conexion.GetConnection();
		int respuesta=0;
		Statement stmt;
		
		String sql="UPDATE solicitud SET estado=? WHERE numero=?";
	    PreparedStatement pstm;
	    
		try {
			pstm = con.prepareStatement(sql);
			pstm.setShort(1, estado);
			pstm.setLong(2, numero);
			
			
			respuesta=pstm.executeUpdate();
			
			System.out.println("actualizarSolicitud sql="+pstm.toString());
			
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
}
