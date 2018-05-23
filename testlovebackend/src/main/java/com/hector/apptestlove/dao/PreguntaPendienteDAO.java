package com.hector.apptestlove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

public class PreguntaPendienteDAO extends Conexion {

public int insertarPreguntaPendiente (UsuarioDTO usuario,PreguntaDTO pregunta){
		
		
		Connection con=Conexion.GetConnection();
		int respuesta=0;
		Statement stmt;
		PreguntasDTO preguntasUsuario=new PreguntasDTO();
		ArrayList<PreguntaDTO>listap=new ArrayList();
		PreguntaDTO preguntaUsuario=new PreguntaDTO();
		PreguntaDAO pDAO=new PreguntaDAO();
		
		try {
			
			preguntasUsuario=pDAO.getPreguntasXUsuario(usuario);
			listap=preguntasUsuario.getPreguntas();
			System.out.println("cantidadPreguntasRecuperadas="+listap.size());
			for(int i=0;i<listap.size();i++){
				preguntaUsuario=listap.get(i);
				
				System.out.println("PreguntaRealizada="+preguntaUsuario.getPregunta());
				System.out.println("PreguntaRcuperada="+pregunta.getPregunta());

				
				if(preguntaUsuario.getPregunta().equals(pregunta.getPregunta())){
					
					String sql="insert into preguntaPendiente(numero,contacto)values(?,?)";
		             PreparedStatement pstm = con.prepareStatement(sql);
		             pstm.setLong(1,preguntaUsuario.getNumero() );
		             pstm.setString(2, pregunta.getPregunta());
		             
		             System.out.println("sql="+pstm.toString());
		             respuesta=pstm.executeUpdate();
					
				}
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



public PreguntasDTO getNumerosFromPPXContacto(ContactoDTO contacto){
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	Statement stmt;
	PreguntasDTO preguntas=new PreguntasDTO();
	ArrayList<PreguntaDTO>listap=new ArrayList<PreguntaDTO>();
	
	
	try {
		
		 ResultSet rs=null;
       
		 String sql="SELECT numero FROM preguntaPendiente WHERE contacto=?";
			

		 
		 PreparedStatement pstm = con.prepareStatement(sql);
        
        pstm.setString(1, contacto.getContacto());
       


        System.out.println("sql="+pstm.toString());
         rs=pstm.executeQuery();
        
	        while( rs.next() ) {
	        	
	        	PreguntaDTO pregunta=new PreguntaDTO();
	        	
	        	pregunta.setNumero(rs.getLong("numero"));
	        	listap.add(pregunta);
	        	
	        }
	        
		preguntas.setPreguntas(listap);
		
		//preguntasFinal=pDAO.getPreguntasXUsuarioXNumero(usuario, preguntas);
		
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
	}
  
   System.out.println("*******************************saliendo de getNumerosFromPPXContacto************************************");

	return preguntas;
	
}


public int eliminarPreguntaPendiente(PreguntaDTO pregunta,ContactoDTO contacto){
	
	Connection con=Conexion.GetConnection();
	int respuesta=0;
	Statement stmt;
	
	String sql="DELETE FROM preguntaPendiente  WHERE numero=? AND contacto=?";
    PreparedStatement pstm;
    
	try {
		pstm = con.prepareStatement(sql);
		
		pstm.setLong(2, pregunta.getNumero());
		pstm.setString(1, contacto.getContacto());
		
		
		respuesta=pstm.executeUpdate();
		
		System.out.println("eliminarPreguntaPendiente sql="+pstm.toString());
		
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
	
}
