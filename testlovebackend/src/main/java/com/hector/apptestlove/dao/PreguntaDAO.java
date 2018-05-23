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
import com.hector.apptestlove.controler.dto.PuntuacionResultadoDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

public class PreguntaDAO extends Conexion {

	public String getCountPreguntasXUsuario(UsuarioDTO usuario){
		
		Connection con=Conexion.GetConnection();
		int respuesta = 0;
		String cantidadPreguntas="0";
		Statement stmt;
		
		
		try {
			
			 ResultSet rs=null;
	       
			 String sql="SELECT COUNT(numero) AS totalPreguntas FROM pregunta WHERE nom_user=?";
				

			 
			 PreparedStatement pstm = con.prepareStatement(sql);
	        
	        pstm.setString(1, usuario.getNom_user());
	       


	        System.out.println("sql="+pstm.toString());
	         rs=pstm.executeQuery();
	        
		        if( rs.next() ) {
		        	
		        cantidadPreguntas=rs.getString("totalPreguntas");
		           
		        	
		        }
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
			return "";
		}
	  
	   System.out.println("*******************************saliendo de getCountPreguntasXUsuario************************************");

		
		return cantidadPreguntas;
	}
	
	
public int insertarPregunta (UsuarioDTO usuario,PreguntaDTO pregunta){
		
		
		Connection con=Conexion.GetConnection();
		int respuesta=0;
		Statement stmt;
		long numeroVal=0;
		
		try {
			
			UsuariosDAO uDAO=new UsuariosDAO();
			respuesta=uDAO.buscarUsuario(usuario);
		/*Nextval*/
			ResultSet rs=null;
			String sql1="SELECT numero FROM pregunta ORDER BY numero DESC LIMIT 1";
            PreparedStatement pstm1 = con.prepareStatement(sql1);
            rs=pstm1.executeQuery();
	        
	        if( rs.next() ) {
	        	
	        	numeroVal=rs.getLong("numero");
	        	numeroVal=numeroVal+1;
	        	
	        }
         /*Nextval*/
			
			if(respuesta == Constantes.SUCCESS){
				String sql="insert into pregunta(numero,nom_user,pregunta)values(?,?,?)";
	             PreparedStatement pstm = con.prepareStatement(sql);
	             pstm.setLong(1,numeroVal );
	             pstm.setString(2, usuario.getNom_user());
	             pstm.setString(3, pregunta.getPregunta());
	             
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
	
public PreguntasDTO getPreguntasXUsuario(UsuarioDTO usuario){
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	Statement stmt;
	PreguntasDTO preguntas=new PreguntasDTO();
	ArrayList<PreguntaDTO>listap=new ArrayList<PreguntaDTO>();
	
	try {
		
		 ResultSet rs=null;
       
		 String sql="SELECT numero,pregunta FROM pregunta WHERE nom_user=?";
			

		 
		 PreparedStatement pstm = con.prepareStatement(sql);
        
        pstm.setString(1, usuario.getNom_user());
       


        System.out.println("sql="+pstm.toString());
         rs=pstm.executeQuery();
        
	        while( rs.next() ) {
	        	
	        	PreguntaDTO pregunta=new PreguntaDTO();
	        	
	        	pregunta.setPregunta(rs.getString("pregunta"));
	        	pregunta.setNumero(rs.getLong("numero"));
	        	listap.add(pregunta);
	        	
	        }
	        
		preguntas.setPreguntas(listap);
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
	}
  
   System.out.println("*******************************saliendo de getPreguntasXUsuario************************************");

	return preguntas;
	
}


public PreguntasDTO getPreguntasXUsuarioXNumero(UsuarioDTO usuario,PreguntasDTO preguntasPendientes){
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	Statement stmt;
	PreguntasDTO preguntas=new PreguntasDTO();
	ArrayList<PreguntaDTO>listapp=new ArrayList<PreguntaDTO>();
	ArrayList<PreguntaDTO>nuevalistapp=new ArrayList<PreguntaDTO>();
	
	listapp=preguntasPendientes.getPreguntas();
	
	try {
		
		for(int i=0;i<listapp.size();i++){
			PreguntaDTO pregunta=new PreguntaDTO();
			pregunta=listapp.get(i);
			
			 ResultSet rs=null;
		       
			 String sql="SELECT pregunta FROM pregunta WHERE nom_user=? AND numero=?";
			 PreparedStatement pstm = con.prepareStatement(sql);

			 pstm.setString(1, usuario.getNom_user());
		     pstm.setLong(2,pregunta.getNumero());
		     
		     System.out.println("sql="+pstm.toString());
	         rs=pstm.executeQuery();
	         
	         if(rs.next()){
	        	 
	        	 pregunta.setPregunta(rs.getString("pregunta"));
	        	 
	         }
	         
	         nuevalistapp.add(pregunta);
			
		}
		
		preguntasPendientes.cleanPreguntas();
		preguntasPendientes.setPreguntas(nuevalistapp);
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
	}
  
   System.out.println("*******************************saliendo de getPreguntasXUsuarioXNumero************************************");

	return preguntasPendientes;
	
}

public int actualizarPregunta(UsuarioDTO usuario,PreguntaDTO pregunta){
	
	Connection con=Conexion.GetConnection();
	int respuesta=0;
	Statement stmt;
	
	String sql="UPDATE pregunta SET pregunta=?  WHERE nom_user=? AND numero=?";
    PreparedStatement pstm;
    
	try {
		pstm = con.prepareStatement(sql);
		pstm.setString(1, pregunta.getPregunta());
		pstm.setString(2, usuario.getNom_user());
		pstm.setLong(3, pregunta.getNumero());
		
		respuesta=pstm.executeUpdate();
		
		System.out.println("actualizarPregunta sql="+pstm.toString());
		
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


public int eliminarPregunta(UsuarioDTO usuario,PreguntaDTO pregunta){
	
	Connection con=Conexion.GetConnection();
	int respuesta=0;
	Statement stmt;
	
	String sql="DELETE FROM pregunta  WHERE nom_user=? AND numero=?";
    PreparedStatement pstm;
    
	try {
		pstm = con.prepareStatement(sql);
		
		pstm.setString(1, usuario.getNom_user());
		pstm.setLong(2, pregunta.getNumero());
		
		respuesta=pstm.executeUpdate();
		
		System.out.println("eliminarPregunta sql="+pstm.toString());
		
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


public int actualizarPuntuacion (int id_contacto,PuntuacionResultadoDTO puntuacion){
	
	
	Connection con=Conexion.GetConnection();
	int respuesta=0;
	Statement stmt;
	
	try {
        
			String sql="update puntuacion set puntos=? where id_contacto=?";
             PreparedStatement pstm = con.prepareStatement(sql);
             pstm.setInt(1,puntuacion.getPuntos() );
             pstm.setLong(2, id_contacto);
             
             respuesta=pstm.executeUpdate();
             
            
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
	

public int buscarPuntuacion(int id_contacto){
	
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	Statement stmt;
	
	try {
		
		 ResultSet rs=null;
        
		 String sql="SELECT p.id_contacto FROM puntuacion p WHERE p.id_contacto=?";
			
		 PreparedStatement pstm = con.prepareStatement(sql);
         
         pstm.setInt(1, id_contacto);


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
   
    System.out.println("*******************************saliendo de buscarPuntuacion************************************");

	return respuesta;
}

public int insertarPuntuacion(int id_contacto,PuntuacionResultadoDTO puntuacion){
	
	
	Connection con=Conexion.GetConnection();
	int respuesta=0;
	Statement stmt;
	
	try {
		
			String sql="insert into puntuacion(id_contacto,puntos)values(?,?)";
             PreparedStatement pstm = con.prepareStatement(sql);
             pstm.setLong(1,id_contacto );
             pstm.setInt(2, puntuacion.getPuntos());
             
             respuesta=pstm.executeUpdate();
             
            
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

public int getPuntos(int id_contacto){
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	int puntosAnterior=0;
	Statement stmt;
	
	
	try {
		
		 ResultSet rs=null;
       
		 String sql="SELECT puntos FROM puntuacion WHERE id_contacto=?";
			

		 
		 PreparedStatement pstm = con.prepareStatement(sql);
        
        pstm.setInt(1, id_contacto);
       


        System.out.println("sql="+pstm.toString());
         rs=pstm.executeQuery();
        
	        if( rs.next() ) {
	        	
	        	puntosAnterior=rs.getInt("puntos");
	           
	        	
	        }
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
		return 0;
	}
  
   System.out.println("*******************************saliendo de getPuntos************************************");

	
	return puntosAnterior;
}
	
}
